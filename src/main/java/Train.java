import railroad.support.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Train implements Runnable{
    private int id;
    private String name;
    private Locomotive locomotive;
    private ArrayList<Carriage> carriages;
    private List<Integer> route;
    private Integer currentPosInRoute;
    private Integer nextPosInRoute;

    private int weightOfTrain;
    private Station currentStation;
    private DispatcherCenter dispatcherCenter;

    @Override
    public void run() {
        while(currentPosInRoute < route.size()-1){
            try {
                move(nextPosInRoute);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("You arrived!");
    }

    public Train(Config.RawTrain rawTrain, DispatcherCenter dispatcherCenter) {
        this.dispatcherCenter = dispatcherCenter;
        this.locomotive = new Locomotive(rawTrain.getPower());
        this.id = rawTrain.getId();
        this.name = rawTrain.getName();
        this.route = new ArrayList<>();
        this.carriages = new ArrayList<>();
        this. currentPosInRoute = 0;
        this.nextPosInRoute = 1;
        route.addAll(rawTrain.getRoute());
        prepareCarriages(rawTrain);
        calculateWeight();
        currentStation = dispatcherCenter.stations.get(route.get(currentPosInRoute));
    }

    protected void prepareCarriages(Config.RawTrain rawTrain){
        for (Config.RawTrain.RawCarriage rawCarriage : rawTrain.getCarriages()) {
            if(rawCarriage.getType().equals("Cargo")){
                carriages.add(new CarriageImplCargo(rawCarriage.getAmountOfContent()));
            }
            if(rawCarriage.getType().equals("Passenger")){
                carriages.add(new CarriageImplPassenger(rawCarriage.getAmountOfContent()));
            }
        }
    }

    // in seconds
    protected int calculateTravelTime(){
        return dispatcherCenter.getDistance(currentStation.getID(), route.get(nextPosInRoute))
                / (weightOfTrain
                / locomotive.getIndexOfPower());
    }

    protected void uploadAllCarriages() throws InterruptedException {
        for (Carriage carriage : carriages ) {
            //убрать голый параметр
            TimeUnit.SECONDS.sleep(1);
            if (carriage instanceof CarriageImplCargo && currentStation instanceof ICargo){
                ((ICargo) currentStation).unloadCargos(carriage.getAvailableSpace());
                ((CarriageImplCargo)carriage).uploadCargos(carriage.getAvailableSpace());
            }

            if (carriage instanceof CarriageImplPassenger && currentStation instanceof IPassenger){
                ((IPassenger) currentStation).unloadPassengers(carriage.getAvailableSpace());
                ((CarriageImplPassenger)carriage).uploadPassengers(carriage.getAvailableSpace());
            }
        }
    }
    protected void unloadAllCarriages(){
        int movingContent;
        int counterOfCarriages = 0;
        for (Carriage carriage : carriages ) {
            System.out.println("Carriage #" + ++counterOfCarriages + " works");
            movingContent = carriage.getRandomAmountOfContentToGo();
            if (carriage instanceof CarriageImplCargo && currentStation instanceof ICargo){
                ((CarriageImplCargo)carriage).unloadCargos(movingContent);
                ((ICargo) currentStation).uploadCargos(movingContent);
            }

            if (carriage instanceof CarriageImplPassenger && currentStation instanceof IPassenger){
                ((CarriageImplPassenger)carriage).unloadPassengers(movingContent);
                ((IPassenger) currentStation).uploadPassengers(movingContent);
            }
        }
    }

    public void calculateWeight(){
        for (Carriage carriage : carriages) {
            weightOfTrain+=carriage.getTotalWeight();
        }
    }

    protected void move(int nextStation) throws InterruptedException {
        if(dispatcherCenter.isWayValid(currentStation.getID(), route.get(nextStation))){
            try{
                //можно сделать класс для вывода по типу паттерна команда
                System.out.println("---------------------------------------------------------------");
                System.out.println(this.name + " moves from " + currentStation.getName()
                        + " to " + dispatcherCenter.stations.get(route.get(nextStation)).getName());
                work();
                System.out.println("Trip will take an " + calculateTravelTime() + " seconds");
                TimeUnit.SECONDS.sleep(calculateTravelTime());
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            currentStation = dispatcherCenter.getStation(route.get(nextStation));
            increaseCurrentPosInRoute();
        }
    }

    protected void increaseCurrentPosInRoute(){
        currentPosInRoute++;
        nextPosInRoute++;
    }

    protected void work() throws InterruptedException {
        printInfoAboutCarriages("Before work: ");
        unloadAllCarriages();
        printInfoAboutCarriages("Before uploading: ");
        uploadAllCarriages();
        printInfoAboutCarriages("After Work: ");
    }

    protected void skipStation(){
        System.out.println("just chilling\n");
    }

    public void printInfoAboutCarriages(String marker){
        for (Carriage carriage : carriages) {
            if (carriage instanceof ICargo){
                System.out.println(marker + " " + ((ICargo) carriage).getAmountOfCargos() + " cargos");
            }
            if (carriage instanceof  IPassenger){
                System.out.println(marker + " " + ((IPassenger) carriage).getAmountOfPassengers() + " passengers");
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}


//простерировать так как могут быть проблемы с восходящим преобразованием
class CargoTrain extends Train{
    private Locomotive locomotive;
    private ArrayList<CarriageImplCargo> carriages;
    private int weightOfTrain;
    private Station currentStation;

    public CargoTrain(Config.RawTrain rawTrain, DispatcherCenter dispatcherCenter) {
        super(rawTrain, dispatcherCenter);
    }


    @Override
    public void work() {
        if(currentStation instanceof ICargo){
         //   super.work();
        }
        else skipStation();
    }

    @Override
    public void uploadAllCarriages() {
        if (currentStation instanceof ICargo){
            for (CarriageImplCargo carriage : carriages ) {
                ((StationImplCargo) currentStation).unloadCargos(carriage.getAvailableSpace());
                carriage.uploadCargos(carriage.getAvailableSpace());
            }
        }
    }

    @Override
    public void unloadAllCarriages() {
        if (currentStation instanceof ICargo){
            int movingCargo;
            for (CarriageImplCargo carriage : carriages ) {
                movingCargo = carriage.getRandomAmountOfContentToGo();
                carriage.uploadCargos(movingCargo);
                ((StationImplCargo) currentStation).unloadCargos(movingCargo);
            }
        }
    }
}

class PassengerTrain extends  Train{
    private Locomotive locomotive;
    private  ArrayList<CarriageImplPassenger> carriages;
    private int weightOfTrain;
    private Station currentStation;

    public PassengerTrain(Config.RawTrain rawTrain, DispatcherCenter dispatcherCenter) {
        super(rawTrain, dispatcherCenter);
    }

    @Override
    public void work() {
        if(currentStation instanceof IPassenger){
          //  super.work();
        }
    }

    @Override
    public void uploadAllCarriages() {
        if (currentStation instanceof IPassenger){
            for (CarriageImplPassenger carriage : carriages ) {
                ((StationImplPassengers) currentStation).unloadPassengers(carriage.getAvailableSpace());
                carriage.uploadPassengers(carriage.getAvailableSpace());
            }
        }
    }

    @Override
    public void unloadAllCarriages() {
        if (currentStation instanceof ICargo){
            int movingCargo;
            for (CarriageImplPassenger carriage : carriages ) {
                movingCargo = carriage.getRandomAmountOfContentToGo();
                carriage.uploadPassengers(movingCargo);
                ((StationImplPassengers) currentStation).unloadPassengers(movingCargo);
            }
        }
    }
}