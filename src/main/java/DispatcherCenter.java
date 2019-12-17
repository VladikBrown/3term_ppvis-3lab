import railroad.support.Config;
import railroad.support.ConfigReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


public final class DispatcherCenter extends Thread{
    public static final String CONFIG_PATH = "./src/main/resources/config.yaml";
    public static final String MATRIX_PATH = "./src/main/resources/matrix.txt";
    public static final int STATION_PASSENGER_RESOURCE = 1000;
    public static final int STATION_CARGO_RESOURCE = 1000;


    private static int[][] adjencyMatrix = new int[0][0];
    public Map<Integer, Station> stations = new HashMap<>();

    private static DispatcherCenter dispatcherCenter;

    private DispatcherCenter(String path) throws IOException {
        Config config = ConfigReader.read(CONFIG_PATH);
        applyConfigurationOfStations(config);
        int size = stations.size();
        adjencyMatrix = new int[size][size];
        readMatrix(MATRIX_PATH);
    }

    public static DispatcherCenter getRailRoad(String configPath) throws IOException {
        // possible i have to use threads
        if (dispatcherCenter == null) {
            dispatcherCenter = new DispatcherCenter(configPath);
        }
        return dispatcherCenter;
    }

    //обновление "населения" в потоке
    private synchronized void refreshStationsContent(int amountOfArrivingConent){
        for (Station station : stations.values()) {
            if(station instanceof IPassenger){
                ((StationImplPassengers) station).uploadPassengers(amountOfArrivingConent);
            }
            if (station instanceof ICargo){
                ((StationImplCargo) station).uploadCargos(amountOfArrivingConent);
            }
        }
    }

    public int getDistance(int currentStation, int nextStation){
        return adjencyMatrix[currentStation-1][nextStation-1];
    }

    public boolean isWayValid(int currentStation, int nextStation) {
        if(adjencyMatrix[currentStation-1][nextStation-1] != 0) {
            return true;
        }
        else return false;
    }

    public Station getStation(int stationID) {
        return stations.get(stationID);
    }

    public void applyConfigurationOfStations(Config config) {

        for (Config.RawStation rawStation : config.getRawStations()) {
            switch (rawStation.getType()) {
                case "Cargo and Passenger": {
                    this.stations.put(rawStation.getId(), new StationImplCargosPassengers(STATION_CARGO_RESOURCE, STATION_PASSENGER_RESOURCE, rawStation.getId(), rawStation.getName()));
                    break;
                }
                case "Cargo": {
                    this.stations.put(rawStation.getId(), new StationImplCargo(STATION_CARGO_RESOURCE, rawStation.getId(), rawStation.getName()));
                    break;
                }
                case "Passenger": {
                    this.stations.put(rawStation.getId(), new StationImplPassengers(STATION_PASSENGER_RESOURCE, rawStation.getId(), rawStation.getName()));
                    break;
                }
            }
        }
    }

    public void readMatrix(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(MATRIX_PATH));

        List<String> lines = new ArrayList<>();
        while (br.ready()) {
            lines.add(br.readLine());
        }
        int matrixLength = lines.get(0).split(" ").length;

        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                String[] line = lines.get(i).split(" ");
                adjencyMatrix[i][j] = Integer.parseInt(line[j]);
            }
        }
    }
}


