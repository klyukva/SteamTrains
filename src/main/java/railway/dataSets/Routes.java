package railway.dataSets;

import railway.data.Route;

import java.util.*;

/**
 * Класс описывает набор уникальных путей
 */
public class Routes {

  private Map<Route, Integer> routes; //набор уникальных путей (по номерам станций) и их длин

  /**
   * Добавление нового пути в набор
   * @param route номера станций на пути
   * @param length длина пути
   */
  public void addRoute (Route route, Integer length) {
    routes.putIfAbsent(route, length);
  }

  /**
   * Контруктор, иницилиализирующий значение с пустым набором путей
   */
  public Routes () {
    this.routes = new HashMap<>();
  }

  /**
   * Контруктор принимающий строку с маршрутами
   * @param inputStr строка с путями
   * @param outputSep разделитель между станциями и длиной пути
   * @param inputSep разделитель между путями
   */
  public Routes(String inputStr, String outputSep, String inputSep) {
    this();
    this.setRoutes(inputStr, outputSep, inputSep);
  }

  /**
   * Установка набора путей из карты путей
   * @param routes карта путей
   */
  public void setRoutes (Map<Route, Integer> routes) {
    this.routes = routes;
  }

  /**
   * Установка набора путей из перечислимого типа
   * @param routes набор путей в перечислимом типе
   */
  public void setRoutes (Iterable<Route> routes) {
    this.routes.clear();

    for (Route route : routes) {
      this.routes.putIfAbsent(route, route.getLength());
    }
  }

  /**
   * Установка набора путей из строки
   * Формат строки: "{№станции_а}{inputStep}{№станции_б}{inputStep}{длина_пути}{outputSep}"
   * @param inputStr строка с путями
   * @param outputSep разделитель между станциями и длиной пути
   * @param inputSep разделитель между путями
   * @throws IllegalArgumentException исключение выбрасывается при некорректных строке с путями или разделителях
   */
  public void setRoutes (String inputStr, String outputSep, String inputSep) throws IllegalArgumentException {
    routes.clear(); //очищаем карту маршрутов, если есть

    if (inputStr == null || inputStr.equals(""))
      return; //если строка с путями пустая или имеет значение null - ничего не делаем

    try {
      Arrays.stream(inputStr.split(outputSep)) //получаем массив строк с отдельными путями
              .map(s -> Arrays.stream(s.split(inputSep)).mapToInt(Integer::parseInt).toArray()) //преобразование пути в массив чисел
              .filter(s -> s.length == 3) //отбрасываем некорректные значения
              .forEach(s -> this.routes.putIfAbsent(new Route(s[0], s[1], s[2]), s[2])); //добавляем новый путь к набору
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Не целочисленный формат записи путей");
    }
  }

  /**
   * Получение набора путей
   * @return набор путей
   */
  public Map<Route, Integer> getRoutes() {
    return routes;
  }

  /**
   * Метод возвращает длину пути, если он есть в текущем наборе путей
   * @param route путь, длину которого необходимо узнать
   * @return длина пути
   * @throws IllegalArgumentException исключение выбрасывается если запрашиваемого пути не существует в данном наборе
   */
  public int getRouteLengthIfExist (Route route) throws IllegalArgumentException {
    Integer len = getRoutes().get(route);
    if (len == null)
      throw new IllegalArgumentException("Проход по несуществующему пути");
    return len;
  }
}
