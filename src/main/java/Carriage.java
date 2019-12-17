abstract public class Carriage {
    public static final int OWN_WEIGHT = 26000;

    abstract int getAvailableSpace();
    abstract int getTotalWeight();
    abstract int getRandomAmountOfContentToGo();
    abstract void calculateTotalWeight();
    abstract boolean isNotFilled();
}

interface ICargo{
    int getAmountOfCargos();
    void uploadCargos(int uploadingCargos);
    int unloadCargos(int unloadingCargos);
}

interface IPassenger{
    int getAmountOfPassengers();
    void uploadPassengers(int uploadingPassengers);
    int unloadPassengers(int unloadingPassengers);
}

class CarriageImplCargo extends Carriage implements ICargo{

    private static final int WEIGHT_OF_CARGO = 10;
    public static final int MAX_CARGOS = 138;
    private int totalWeight;
    private int amountOfCargos;

    public CarriageImplCargo(int amountOfCargos){
        if (amountOfCargos >= 0){
            this.amountOfCargos = amountOfCargos;
        }
    }

    public int getAvailableSpace(){
        if(isNotFilled()) {
            return MAX_CARGOS - amountOfCargos;
        }
        else return 0;
    }

    public int getAmountOfCargos(){
        return amountOfCargos;
    }

    public void uploadCargos(int uploadingCargos) {
        if(isNotFilled()){

            amountOfCargos+=uploadingCargos;
        }
    }

    public int unloadCargos(int unloadingCargos) {
        if(unloadingCargos <= amountOfCargos){
            amountOfCargos-=unloadingCargos;
            return unloadingCargos;
        }
        return 0;
    }

    public void calculateTotalWeight() {
        this.totalWeight = amountOfCargos * WEIGHT_OF_CARGO + Carriage.OWN_WEIGHT;
    }

    public int getTotalWeight() {
        calculateTotalWeight();
        return this.totalWeight;
    }

    public boolean isNotFilled() {
        if(amountOfCargos < MAX_CARGOS){
            return true;
        }
        else {return false;
        }
    }

    @Override
    public int getRandomAmountOfContentToGo() {
        return (int) (Math.random() * getAmountOfCargos());
    }
}

class CarriageImplPassenger extends Carriage implements IPassenger{

    private static final int WEIGHT_OF_PASSENGER = 70;
    public static final int MAX_PASSENGERS = 54;
    private int amountOfPassengers;
    private int totalWeight;

    public CarriageImplPassenger(int amountOfPassengers){
        this.amountOfPassengers = amountOfPassengers;
    }

    public int getAmountOfPassengers() {

        return amountOfPassengers;
    }

    public void uploadPassengers(int uploadingPassengers) {
        if(isNotFilled()){
            amountOfPassengers+=uploadingPassengers;
        }
    }

    public int unloadPassengers(int unloadingCargos) {
        if(unloadingCargos <= amountOfPassengers){
            amountOfPassengers-=unloadingCargos;
            return unloadingCargos;
        }
        return 0;
    }

    public void calculateTotalWeight() {

        this.totalWeight = amountOfPassengers * WEIGHT_OF_PASSENGER + Carriage.OWN_WEIGHT;
    }

    public int getTotalWeight() {
        calculateTotalWeight();
        return this.totalWeight;
    }

    public boolean isNotFilled() {
        if(amountOfPassengers < MAX_PASSENGERS){
            return true;
        }
        else {return false;
        }
    }

    public int getAvailableSpace() {
        if(isNotFilled()) {
            return MAX_PASSENGERS - amountOfPassengers;
        }
        else return 0;
    }

    @Override
    public int getRandomAmountOfContentToGo() {
        return (int) (Math.random() * getAmountOfPassengers());
    }
}