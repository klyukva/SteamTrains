package railway.executor;

import railway.data.Route;
import railway.data.TrainRide;
import railway.dataSets.Routes;
import railway.dataSets.TrainRides;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс содержит функции по определению возможных столкновений с заданными путями на заданных маршрутах
 */
public class TripExecutor {
  /**
   * Функция проверяющая мересечения маршрутов поездов на заданных путях.
   * Проверяются все маршруты поездов, чтобы убедиться в их корректности
   * @param routes набор пути
   * @param trainRides набор маршрутов поездов
   * @return строку с информацией о возможных столкновенях или их отсутствии
   * @throws IllegalArgumentException исключени выбрасывается, если аргрументы не инициализированы
   */
  public static String accidentCheck(Routes routes, TrainRides trainRides) throws IllegalArgumentException {
    if (routes == null || trainRides == null)
      throw new IllegalArgumentException("Аргументы функции не инициализированы");
    Map<Route, Set<PairStationDirection>> routesVisited = new HashMap<>(); //таблица с временами пребывания поездов на пути
    Set<StationVisitTime> stationsVisited = new HashSet<>(); //набор с временами пребывания поездов на станциях
    boolean collision = false; //флаг встречи поездов

    for (TrainRide train : trainRides.getRides()) { //проверяем каждый поезд
      int currentTime = 0;//поезд начинает свое движения с времени 0
      int[] trainPath = train.getStations();

      /*
        Проверяем, не было ли отправления от одной станции нескольких поездов
        Добавлям текущие время и станцию в набор или останавливаем выполнение функции и сообщаем о столкновении
       */
      if (meetOnStation(stationsVisited, currentTime, trainPath[0]))
        collision = true;

      /*
        Обходим все последовательные пары станций (пути) поезда
       */
      for (int i = 0; i < trainPath.length - 1; i++) {
        PairStationDirection pairStationDirection;
        Route key = new Route(trainPath[i], trainPath[i+1]);

        /*
          Проверяем номер станции для установки направления в конструкторе, равными станции быть не могут,
          проверялось в конструкторе TrainRides
         */
        if(trainPath[i] < trainPath[i+1]) {
          pairStationDirection = new PairStationDirection(currentTime, true);
        } else {
          pairStationDirection = new PairStationDirection(currentTime, false);
        }

        int routeLength = routes.getRouteLengthIfExist(key);

        /*
          Добавляем текущее время и станцию в набор
         */
        if (meetOnStation(stationsVisited, currentTime + routeLength, trainPath[i+1]))
          collision = true;

        /*
          Проверяем встречу поездов на текущем пути
         */
        if(!routesVisited.containsKey(key)) {
          routesVisited.put(key, new HashSet<>());
        } else if (pairStationDirection.crossingTrains(routesVisited.get(key), routeLength)){
            collision = true;
        }


        routesVisited.get(key).add(pairStationDirection); //обновляем таблицу времени пребывания на пути
        currentTime += routeLength; //обновляем текущее время
      }
    }
    if (collision)
      return "Произошло столкновение";
    return "Столкновений не произошло";
  }

  private static boolean meetOnStation (Set<StationVisitTime> alreadyCheckedStations,
                                        int currentTime, int stationNumber) {

    StationVisitTime checkingStation = new StationVisitTime(currentTime, stationNumber);
    if (alreadyCheckedStations.contains(checkingStation))
      return true;

    alreadyCheckedStations.add(checkingStation);
    return false;
  }

  /**
   * Класс описывает время посещения станции поездом
   */
  static class StationVisitTime {
    private final int visitTime;
    private final int numberStation;

    /**
     * Контруктор принимающий время и номер станции
     * @param visitTime время посещения
     * @param numberStation номер станции
     */
    public StationVisitTime(int visitTime, int numberStation) {

      this.visitTime = visitTime;
      this.numberStation = numberStation;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof StationVisitTime)) return false;

      StationVisitTime that = (StationVisitTime) o;

      if (visitTime != that.visitTime) return false;
      return numberStation == that.numberStation;
    }

    @Override
    public int hashCode() {
      int result = visitTime;
      result = 31 * result + numberStation;
      return result;
    }
  }

  /**
   * Класс описывает время и направление отправления поезда от станции
   */
  static class PairStationDirection {

    private final int timePassingStation;
    private final boolean direction;//true - от меньшего номера станции к большему(timePassingStation<b)

    /**
     *
     * @param timePassingStation время начала движения поезда по пути
     * @param direction направление движения поезда, true - от станции с меньшим номером к станции с большим номером
     */
    public PairStationDirection(int timePassingStation, boolean direction){

      this.timePassingStation = timePassingStation;
      this.direction = direction;
    }

    /**
     * Проверка пересечений маршрутов поездов на заданном пути с текущим поездом
     * @param pairStationDirections набор времен и направлений проездов по текущему пути
     * @param travelTime время проезда поезда по текущему пути
     * @return true - поезда встретились или едут в одну сторону в одно время
     */
    public boolean crossingTrains (Set<PairStationDirection> pairStationDirections, int travelTime){

      for (PairStationDirection pairStationDirection : pairStationDirections){
        if (this.timePassingStation == pairStationDirection.timePassingStation)
          return true;

        if (this.direction != pairStationDirection.direction
                && Math.abs(this.timePassingStation - pairStationDirection.timePassingStation) <= travelTime)
          return true;
      }
      return false;
    }
  }
}
