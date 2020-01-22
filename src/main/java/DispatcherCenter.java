import railroad.support.Config;
import railroad.support.ConfigReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class DispatcherCenter extends Thread{
    public static final String CONFIG_PATH = "./src/main/resources/config.yaml";
    public static final String MATRIX_PATH = "./src/main/resources/matrix.txt";
    public static final int STATION_PASSENGER_RESOURCE = 1000;
    public static final int STATION_CARGO_RESOURCE = 1000;

    //пофикстить объявление
    private static int[][] adjacencyMatrix = new int[0][0];
    public Map<Integer, Station> stations;

    private static DispatcherCenter dispatcherCenter;

    private DispatcherCenter() throws IOException {
        stations = new HashMap<>();
        Config config = ConfigReader.read(CONFIG_PATH);
        applyConfigurationOfStations(config);
        int size = stations.size();
        adjacencyMatrix = new int[size][size];
        readMatrix(MATRIX_PATH);
    }

    public static DispatcherCenter getRailRoad() throws IOException {
        // possible i have to use threads
        if (dispatcherCenter == null) {
            dispatcherCenter = new DispatcherCenter();
        }
        return dispatcherCenter;
    }

    //обновление "населения" в потоке
    private synchronized void refreshStationsContent(int amountOfArrivingContent){
        for (Station station : stations.values()) {
            if(station instanceof IPassenger){
                ((StationImplPassengers) station).uploadPassengers(amountOfArrivingContent);
            }
            if (station instanceof ICargo){
                ((StationImplCargo) station).uploadCargos(amountOfArrivingContent);
            }
        }
    }

    public int getDistance(int currentStation, int nextStation){
        return adjacencyMatrix[currentStation-1][nextStation-1];
    }

    public boolean isWayValid(int currentStation, int nextStation) {
        if (nextStation < stations.size()){
            if(adjacencyMatrix[currentStation-1][nextStation-1] != 0) {
                return true;
            }
            else return false;
        }
        else{
            System.out.println("Such station doesn't exist");
            return false;
        }
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

        for (int i = 0; i < matrixLength-1; i++) {
            for (int j = 0; j < matrixLength-1; j++) {
                String[] line = lines.get(i).split(" ");
                adjacencyMatrix[i][j] = Integer.parseInt(line[j]);
            }
        }
    }
}


