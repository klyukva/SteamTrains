package railway.dataSets;

import org.junit.Test;

public class TrainRidesTest {

  @Test(expected = IllegalArgumentException.class)
  public void incorrectOutputSep () {
    new TrainRides("1 2 3,3 4 5", "", " ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void incorrectInputSep () {
    new TrainRides("1 2 3,3 4 5", ",", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void incorrectInputStr () {
    new TrainRides("Hello", ",", " ");
  }
}