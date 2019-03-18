package railway.data;

import org.junit.Test;

public class RouteTest {

  @Test(expected = IllegalArgumentException.class)
  public void setLoops () {
    new Route(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumberStation () {
    new Route(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonPositiveLengthRoute () {
    new Route(1, 2, 0);
  }
}