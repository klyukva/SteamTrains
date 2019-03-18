package railway.dataSets;

import railway.data.TrainRide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс описывает маршруты нескольких поездов
 */
public class TrainRides {

  private final List<TrainRide> rides; //набор маршрутов

  /**
   * Контруктор, создающий пустой набор маршрутов
   */
  public TrainRides () {
    this.rides = new ArrayList<>();
  }

  public TrainRides (String inputStr, String outputSep, String inputSep) {
    this();
    this.setRides(inputStr, outputSep, inputSep);
  }

  /**
   * Установка набора маршрутов из строки
   * Формат строки: "{№станции_а}{inputStep}{№станции_б}{inputStep}{№станции_в}{outputSep}
   * @param inputStr строка с маршрутами
   * @param outputSep разделитель между станциями
   * @param inputSep разделитель между маршрутами
   * @throws IllegalArgumentException исключение выбрасывается при некорректных строке с маршрутами или разделителях
   */
  public void setRides (String inputStr, String outputSep, String inputSep) throws IllegalArgumentException {
    rides.clear(); //очищаем набор маршрутов

    if (inputStr == null || inputStr.equals(""))
      return;

    try {
      Arrays.stream(inputStr.split(outputSep)) //получаем массив строк с отдельными маршрутами
              .map(s -> Arrays.stream(s.split(inputSep)).mapToInt(Integer::parseInt).toArray()) //преобразовываем маршруты в массив чисел
              .forEach(s -> this.rides.add(new TrainRide(s))); //добавляем новый маршрут к набору
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Не целочисленный формат записи маршрутов");
    }

  }

  /**
   * Выдача списков всех текущих маршрутов поездов
   * @return маршруты в текущем наборе
   */
  public List<TrainRide> getRides() {
    return rides;
  }
}
