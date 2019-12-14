import railroad.support.Config;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RailRoadModel {
    private DispatcherCenter dispatcherCenter;
    private ConcurrentLinkedQueue<Train> trains;







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
