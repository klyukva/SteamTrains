package railway.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TrainRideTest {

  private TrainRide trainRide;

  @Test
  public void skippingStationRepeats () {
    trainRide = new TrainRide(new int[] {1, 2, 2, 3, 3, 3, 2});
    Assert.assertTrue(Arrays.equals(trainRide.getStations(), new int[] {1, 2, 3, 2}));
  }

}