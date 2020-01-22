import railroad.support.Config;
import railroad.support.ConfigReader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RailRoadModel {
    private DispatcherCenter dispatcherCenter;
    private List<Train> trains = new LinkedList<>();

    public void start(String args) throws IOException {
        initDispatcherCenter();
        Config config = ConfigReader.read(DispatcherCenter.CONFIG_PATH);
        applyConfigurationOfTrains(config, args);
        if("log".equals(args)){
            ExecutorService exec = Executors.newFixedThreadPool(1);
                exec.execute(trains.get(0));
                exec.shutdown();
        }
        else{
        for (Train train: trains) {
            ExecutorService exec = Executors.newFixedThreadPool(1);
            exec.execute(train);
            exec.shutdown();
        }
        }
    }

    public void initDispatcherCenter() throws IOException {
        dispatcherCenter = DispatcherCenter.getRailRoad();
    }

    public void applyConfigurationOfTrains(Config config, String arg) {
        for (Config.RawTrain rawTrain : config.getTrains()) {
            switch (rawTrain.getType()) {
                case "Cargo and Passenger": {
                    this.trains.add(new Train(rawTrain, dispatcherCenter, arg));
                    break;
                }
                case "Cargo": {
                    this.trains.add(new CargoTrain(rawTrain, dispatcherCenter, arg));
                    break;
                }
                case "Passenger": {
                    this.trains.add(new PassengerTrain(rawTrain, dispatcherCenter, arg));
                    break;
                }
            }
        }
    }
}
