import railroad.support.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Train implements Runnable{
    private int id;
    private String name;
    private Locomotive locomotive;
    private ArrayList<Carriage> carriages = new ArrayList<>();
    private List<Integer> route = new LinkedList<>();
    private Iterator<Integer> routeIterator = route.listIterator();

    private int weightOfTrain;
    private Station currentStation;
    private DispatcherCenter dispatcherCenter;

    @Override
    public void run() {
        while (routeIterator.hasNext()){
            try {
                move(routeIterator.next());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Train(Config.RawTrain rawTrain, DispatcherCenter dispatcherCenter) {
        this.dispatcherCenter = dispatcherCenter;
        locomotive = new Locomotive(rawTrain.getPower());
        this.id = rawTrain.getId();
        this.name = rawTrain.getName();
        route.addAll(rawTrain.getRoute());
        prepareCarriages(rawTrain);
        calculateWeight();
    }

    private void prepareCarriages(Config.RawTrain rawTrain){
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
    public int calculateTravelTime(){
        return dispatcherCenter.getDistance(currentStation.getID(), routeIterator.next())
                / (weightOfTrain
                / locomotive.getIndexOfPower());
    }

    public void uploadAllCarriages(){
        for (Carriage carriage : carriages ) {
            if (carriage instanceof CarriageImplCargo && currentStation instanceof ICargo){
                ((StationImplCargo) currentStation).unloadCargos(carriage.getAvailableSpace());
                ((CarriageImplCargo)carriage).uploadCargos(carriage.getAvailableSpace());
            }

            if (carriage instanceof CarriageImplPassenger && currentStation instanceof IPassenger){
                ((ICargo) currentStation).unloadCargos(carriage.getAvailableSpace());
                ((CarriageImplPassenger)carriage).uploadPassengers(carriage.getAvailableSpace());
            }
        }
    }
    public void unloadAllCarriages(){
        int movingContent;
        for (Carriage carriage : carriages ) {
            movingContent = carriage.getRandomAmountOfContentToGo();
            if (carriage instanceof CarriageImplCargo && currentStation instanceof ICargo){
                ((CarriageImplCargo)carriage).unloadCargos(movingContent);
                ((StationImplCargo) currentStation).uploadCargos(movingContent);
            }

            if (carriage instanceof CarriageImplPassenger && currentStation instanceof IPassenger){
                ((CarriageImplPassenger)carriage).unloadPassengers(movingContent);
                ((ICargo) currentStation).uploadCargos(movingContent);
            }
        }
    }

    public void calculateWeight(){
        for (Carriage carriage : carriages) {
            weightOfTrain+=carriage.getTotalWeight();
        }
    }

    public void move(int nextStation) throws InterruptedException {
        if(dispatcherCenter.isWayValid(currentStation.getID(), nextStation)){
            try{
                //можно сделать класс для вывода по типу паттерна команда
                System.out.println(this.name + "moves from " + currentStation.getName() + " to" + dispatcherCenter.stations.get(nextStation));
                TimeUnit.SECONDS.sleep(calculateTravelTime());
                work();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            currentStation = dispatcherCenter.getStation(nextStation);

        }
    }

    public void work(){
        unloadAllCarriages();
        uploadAllCarriages();
    }

    public void skipStation(){
        System.out.println("just chilling\n");
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
            super.work();
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
            super.work();
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