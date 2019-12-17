import railroad.support.Config;
import railroad.support.ConfigReader;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RailRoadModel {
    private DispatcherCenter dispatcherCenter;
    private ConcurrentLinkedQueue<Train> trains = new ConcurrentLinkedQueue<>();

    public void start(String path) throws IOException {
        initDispatcherCenter(path);
        Config config = ConfigReader.read(DispatcherCenter.CONFIG_PATH);
        applyConfigurationOfTrains(config);
        for (Train train: trains) {
            ExecutorService exec = Executors.newFixedThreadPool(1);
            exec.execute(train);
        }
    }

    public void initDispatcherCenter(String path) throws IOException {
        dispatcherCenter = DispatcherCenter.getRailRoad(path);
    }

    public void applyConfigurationOfTrains(Config config) {
        for (Config.RawTrain rawTrain : config.getTrains()) {
            switch (rawTrain.getType()) {
                case "Cargo and Passenger": {
                    this.trains.add(new Train(rawTrain, dispatcherCenter));
                    break;
                }
                case "Cargo": {
                    this.trains.add(new CargoTrain(rawTrain, dispatcherCenter));
                    break;
                }
                case "Passenger": {
                    this.trains.add(new PassengerTrain(rawTrain, dispatcherCenter));
                    break;
                }
            }
        }
    }
}
