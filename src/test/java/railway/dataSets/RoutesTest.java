package railway.dataSets;

import org.junit.Assert;
import org.junit.Test;
import railway.data.Route;

import static org.junit.Assert.*;

public class RoutesTest {
  private Routes routes;

  @Test(expected = IllegalArgumentException.class)
  public void incorrectOutputSep () {
    new TrainRides("1 2 1,2 3 5", "", " ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void incorrectInputSep () {
    new TrainRides("1 2 1,2 3 5", ",", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void incorrectInputStr () {
    new TrainRides("Hello", ",", " ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void lengthNotExistingRoute () {
    new Routes("1 2 3", ",", " ").getRouteLengthIfExist(new Route(2, 3));
  }

  @Test
  public void skippingNotThreeValues () {
    routes = new Routes("1,1 2,1 2 3 4", ",", " ");
    Assert.assertTrue(routes.getRoutes().isEmpty());
  }

  @Test
  public void selectionFirstOfRepeatingStations () {
    routes = new Routes("1 2 3,2 1 5", ",", " ");
    assertEquals(routes.getRouteLengthIfExist(new Route(1, 2)), 3);
  }
}