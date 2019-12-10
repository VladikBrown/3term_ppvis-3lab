abstract public class Station{
    abstract public int getID();
}

class StationImplCargo extends Station implements ICargo{
    private int amountOfCargos;
    private int ID;

    public StationImplCargo(int id){
        ID = id;
    }

    public StationImplCargo(int amountOfCargos, int id){
        this.amountOfCargos = amountOfCargos;
        ID = id;
    }

    public int getAmountOfCargos() {
        return amountOfCargos;
    }

    public void uploadCargos(int uploadingCargos) {
        amountOfCargos+= uploadingCargos;
    }

    public int unloadCargos(int unloadingCargos) {
        if(unloadingCargos <= amountOfCargos){
            amountOfCargos-=unloadingCargos;
            return unloadingCargos;
        }
        return 0;
    }

    public int getID() {
        return ID;
    }
}

class StationImplPassengers extends Station implements IPassenger{
    private int amountOfPassengers;
    private int ID;

    public StationImplPassengers(int id){
        amountOfPassengers = 0;
        ID = id;
    }

    public StationImplPassengers(int amountOfPassengers, int id){
        this.amountOfPassengers = amountOfPassengers;
        ID = id;
    }

    public int getAmountOfPassengers() {
        return amountOfPassengers;
    }

    public void uploadPassengers(int uploadingPassengers) {
        amountOfPassengers += uploadingPassengers;
    }

    public int unloadPassengers(int unloadingPassengers) {
        if(unloadingPassengers <= amountOfPassengers){
            amountOfPassengers-=unloadingPassengers;
            return unloadingPassengers;
        }
        return 0;
    }

    public int getID() {
        return ID;
    }
}

class StationImplCargosPassengers extends Station implements ICargo, IPassenger{
    private int amountOfCargos;
    private int amountOfPassengers;
    private int ID;

    public StationImplCargosPassengers(int ID){
        this.ID = ID;
    }

    public StationImplCargosPassengers(int amountOfCargos, int amountOfPassengers, int id){
        this.amountOfCargos = amountOfCargos;
        this.amountOfPassengers = amountOfPassengers;
        this.ID = id;
    }

    public int getAmountOfCargos() {
        return amountOfCargos;
    }

    public void uploadCargos(int uploadingCargos) {
        amountOfCargos+= uploadingCargos;
    }

    public int unloadCargos(int unloadingCargos) {
        if(unloadingCargos <= amountOfCargos){
            amountOfCargos-=unloadingCargos;
            return unloadingCargos;
        }
        return 0;
    }

    public int getAmountOfPassengers() {
        return amountOfPassengers;
    }

    public void uploadPassengers(int uploadingPassengers) {
        amountOfPassengers += uploadingPassengers;
    }

    public int unloadPassengers(int unloadingPassengers) {
        if(unloadingPassengers <= amountOfPassengers){
            amountOfPassengers-=unloadingPassengers;
            return unloadingPassengers;
        }
        return 0;
    }

    public int getID() {
        return ID;
    }
}