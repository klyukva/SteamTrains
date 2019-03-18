package railway.data;

import java.util.Arrays;

/**
 * Класс описывает маршрут поезда
 */
public class TrainRide {

  private final int[] stations;

  /**
   * Конструктор, принимающий маршрут в виде массива номеров станций
   * @param stations номера станций по порядку, по которым едет поезд
   */
  public TrainRide(int[] stations) throws IllegalArgumentException {
    if (stations == null || stations.length == 0)
      throw new IllegalArgumentException("Маршрут не должен быть пустым");

    for (int elem : stations)
      if (elem < 0)
        throw new IllegalArgumentException("Номера станций должны быть неотрицательными целыми числами");

          int n = 1;//счетчик количества неповторяющихся станций

    for (int i = 0; i < stations.length - 1; i++) //считаем колличество значений без повторений подряд
      if (stations[i] != stations[i+1])
        n++;

    int[] res = new int[n]; //создаем вспомогательный массив с колличеством значений без повторений подряд
    n = 1;
    res[0] = stations[0];

    for (int i = 0; i < stations.length - 1; i++) //записывем значения в впомогательный массив
      if (stations[i] != stations[i+1])
        res[n++] = stations[i+1];

      if (res.length <= 1)
        throw new IllegalArgumentException("Маршрут должен содержать не менее двух различных станций");

    this.stations = res;
  }

  /**
   * Получение маршрута поезда
   * @return маршрут поезда
   */
  public int[] getStations() {
    return stations;
  }

  @Override
  public String toString() {
    return "TrainRide{" +
            "stations=" + Arrays.toString(stations) +
            '}';
  }
}
