import java.util.ArrayList;

public class Train<T extends Carriage> {
    private Locomotive locomotive;
    private ArrayList<T> carriages;
    private int weightOfTrain;
    private Station currentStation;
    private RailRoad dispatcherCenter;

    public void uploadAllCarriages(){
        for (Carriage carriage : carriages ) {
            if (carriage instanceof ICargo && currentStation instanceof ICargo){
                ((CarriageImplCargo)carriage).uploadCargos(carriage.getAvailableSpace());
                ((ICargo) currentStation).unloadCargos(carriage.getAvailableSpace());
            }

            if (carriage instanceof IPassenger && currentStation instanceof IPassenger){
                carriage.uploadCargos(carriage.getAvailableSpace());
                ((ICargo) currentStation).unloadCargos(carriage.getAvailableSpace());
            }
        }
    }
    public void unloadAllCarriages(){

    }

    public void calculateWeight(){
        for (Carriage carriage : carriages) {
            weightOfTrain+=carriage.getTotalWeight();
        }
    }

    public void move(int nextStation){
        if(dispatcherCenter.isWayValid(currentStation.getID(), nextStation)){
            currentStation = (StationImplCargosPassengers) dispatcherCenter.getStation(nextStation);

        }
    }

    public void work(String task){
        if(currentStation instanceof ICargo && currentStation instanceof  IPassenger){

        } else
            if(currentStation instanceof ICargo){

            } else
                if(currentStation instanceof IPassenger){

                }

    }
}


//простерировать так как могут быть проблемы с восходящим преобразованием
class CargoTrain extends Train{
    private Locomotive locomotive;
    private ArrayList<CarriageImplCargo> carriages;
    private int weightOfTrain;
    private Station currentStation;

    @Override
    public void work(String task) {
        for (CarriageImplCargo carriage : carriages ) {
            if (carriage instanceof ICargo && currentStation instanceof ICargo){
                carriage.uploadCargos(carriage.getAvailableSpace());
                ((ICargo) currentStation).unloadCargos(carriage.getAvailableSpace());
            }
        }
    }

    @Override
    public void uploadAllCarriages() {

    }

    @Override
    public void unloadAllCarriages() {
        super.unloadAllCarriages();
    }
}

class PassengerTrain extends  Train{
    private Locomotive locomotive;
    private  ArrayList<CarriageImplPassenger> carriages;
    private int weightOfTrain;
    private Station currentStation;

    @Override
    public void work(String task) {
        for (CarriageImplPassenger carriage : carriages ) {
            if (carriage instanceof IPassenger && currentStation instanceof IPassenger){
                carriage.uploadPassengers(carriage.getAvailableSpace());
                 ((IPassenger) currentStation).unloadPassengers(carriage.getAvailableSpace());
            }
        }
    }

    @Override
    public void uploadAllCarriages() {
        super.uploadAllCarriages();
    }

    @Override
    public void unloadAllCarriages() {
        super.unloadAllCarriages();
    }
}