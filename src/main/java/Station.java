abstract public class Station{
    abstract public int getID();
    abstract public String getName();

    @Override
    public String toString() {
        return getName();
    }
}

class StationImplCargo extends Station implements ICargo{
    private volatile int amountOfCargos;
    private Integer ID;
    private String name;


    public StationImplCargo(int amountOfCargos, Integer id, String name){
        this.amountOfCargos = amountOfCargos;
        ID = id;
        this.name = name;
        String n = "gf";
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setAmountOfCargos(int amountOfCargos) {
        this.amountOfCargos = amountOfCargos;
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
}

class StationImplPassengers extends Station implements IPassenger{
    private volatile int amountOfPassengers;
    private Integer ID;
    private String name;

    public StationImplPassengers(int amountOfPassengers, Integer id, String name){
        this.amountOfPassengers = amountOfPassengers;
        ID = id;
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setAmountOfPassengers(int amountOfPassengers) {
        this.amountOfPassengers = amountOfPassengers;
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

}

class StationImplCargosPassengers extends Station implements ICargo, IPassenger{
    private volatile int amountOfCargos;
    private volatile int amountOfPassengers;
    private Integer ID;
    private String name;

    public StationImplCargosPassengers(int amountOfCargos, int amountOfPassengers, Integer id, String name){
        this.amountOfCargos = amountOfCargos;
        this.amountOfPassengers = amountOfPassengers;
        this.ID = id;
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setAmountOfCargos(int amountOfCargos) {
        this.amountOfCargos = amountOfCargos;
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


    public void setAmountOfPassengers(int amountOfPassengers) {
        this.amountOfPassengers = amountOfPassengers;
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


}