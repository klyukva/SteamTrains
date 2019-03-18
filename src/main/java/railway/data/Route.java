package railway.data;
/**
 * Класс описывает путь между двумя станциями
 */
public class Route {

  private  static final int DEFAULT_LENGTH = 1;
  private final int firstStation;
  private final int secondStation;
  private final int length;

  /**
   * Конструктор принимает номера двух станций и длину пути
   * @param firstStation первая станция на пути
   * @param secondStation втроая станция на пути
   */
  public Route(int firstStation, int secondStation, int length){
    if (firstStation < 0 || secondStation < 0)
      throw new IllegalArgumentException("Номера станций должны быть неотрицательными целыми числами");
    if (length <= 0)
      throw new IllegalArgumentException("Длина должна быть полижительным целым числом");
    if(firstStation == secondStation)
      throw new IllegalArgumentException("Петли не поддерживаются");

    this.firstStation = firstStation;
    this.secondStation = secondStation;
    this.length = length;
  }

  /**
   * Конструктор принимающий номера станций, длина пути приравнивается к длине по умолчанию
   * @param firstStation первая станция на пути
   * @param secondStation вторая станция на пути
   */
  public Route(int firstStation, int secondStation) {
    this(firstStation, secondStation, DEFAULT_LENGTH);
  }

  /**
   * Возвращение длины пути
   * @return длина пути
   */
  public int getLength() {
    return length;
  }
/*
  Считаем что пути со станциями x и y эквивалетны пути со станциями y и x, длины не учитываются
 */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Route)) return false;

    Route route = (Route) o;

    int thisMinorSt = firstStation < secondStation ? firstStation : secondStation;
    int thisMajorSt = firstStation > secondStation ? firstStation : secondStation;
    int routeMinorSt = route.firstStation < route.secondStation ?
            route.firstStation : route.secondStation;
    int routeMajorSt = route.firstStation > route.secondStation ?
            route.firstStation : route.secondStation;

    if (thisMinorSt != routeMinorSt) return false;
    return thisMajorSt == routeMajorSt;
  }
  /*
    Считаем что пути со станциями x и y и пути со станциями y и x имеют одинаковый хэш код, длины не учитываются
   */
  @Override
  public int hashCode() {
    int resultMinor = firstStation < secondStation ? firstStation : secondStation;
    int resultMajor = firstStation > secondStation ? firstStation : secondStation;
    resultMinor = 31 * resultMinor + resultMajor;
    return resultMinor;
  }
}
