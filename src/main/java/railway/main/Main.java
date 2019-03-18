package railway.main;

import railway.dataSets.Routes;
import railway.dataSets.TrainRides;
import railway.executor.TripExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void  main(String... args) {
    String trainStr = null;
    String routeStr = null;
    try {
      trainStr = fileToString("src/main/resources/trains.txt");
      routeStr = fileToString("src/main/resources/paths.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }

    TrainRides trainRides = new TrainRides(trainStr, ",", " ");
    Routes routes = new Routes(routeStr, ",", " ");

    System.out.println(TripExecutor.accidentCheck(routes, trainRides));
  }

  /**
   * Чтение файла и возвращение содержи мого в виде строки
   * @param filePath путь файла для чтения
   * @return содержимое файла, преобразованное в строку
   * @throws IOException
   */
  static public String fileToString (String filePath) throws IOException {

    return new String(Files.readAllBytes(Paths.get(filePath)));
  }
}
