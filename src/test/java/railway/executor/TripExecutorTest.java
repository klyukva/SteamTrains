package railway.executor;

import org.junit.Test;
import railway.dataSets.Routes;
import railway.dataSets.TrainRides;
import static junit.framework.TestCase.assertEquals;

public class TripExecutorTest {

  private TrainRides trainRides;
  private Routes routes;

  @Test(expected = IllegalArgumentException.class)
  public void nullTrainRidesArgument () throws IllegalArgumentException{
    trainRides = null;
    routes = new Routes("1 2 3", ",", " ");
    TripExecutor.accidentCheck(routes, trainRides);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullRoutesArgument () throws IllegalArgumentException{
    trainRides = new TrainRides("1 2 3", ",", " ");
    routes = null;
    TripExecutor.accidentCheck(routes, trainRides);
  }

  @Test
  public void noCrashCircle () {
    trainRides = new TrainRides("1 2 3 4,2 3 4 1", ",", " ");
    routes = new Routes("1 2 2,2 3 3,3 4 4,4 1 1", ",", " ");
    assertEquals(TripExecutor.accidentCheck(routes, trainRides), "Столкновений не произошло");
  }

  @Test
  public void crushOneStationStart () {
    trainRides = new TrainRides("1 2 3 4,1 2 3 4 1", ",", " ");
    routes = new Routes("1 2 2,2 3 3,3 4 4,4 1 1", ",", " ");
    assertEquals(TripExecutor.accidentCheck(routes, trainRides), "Произошло столкновение");
  }

  @Test
  public void crushMovementTowards () {
    trainRides = new TrainRides("1 2 3,3 2 1", ",", " ");
    routes = new Routes("1 2 2,2 3 3,3 4 4,4 1 1", ",", " ");
    assertEquals(TripExecutor.accidentCheck(routes, trainRides), "Произошло столкновение");
  }

  @Test
  public void crushMeetOnStation () {
    trainRides = new TrainRides("1 2 3,4 2 5", ",", " ");
    routes = new Routes("1 2 2,2 3 3,2 4 2,2 5 5", ",", " ");
    assertEquals(TripExecutor.accidentCheck(routes, trainRides), "Произошло столкновение");
  }

  @Test
  public void crushSameStationFinishSameTime () {
    trainRides = new TrainRides("1 2 3,5 4 3", ",", " ");
    routes = new Routes("1 2 1,2 3 5,3 4 3,4 5 3", ",", " ");
    assertEquals(TripExecutor.accidentCheck(routes, trainRides), "Произошло столкновение");
  }

  @Test
  public void crushWithIncorrectStationNumber () {
    trainRides = new TrainRides("1 2 3,3 2 1 10000", ",", " ");
    routes = new Routes("1 2 1,2 3 2", ",", " ");
  }
}