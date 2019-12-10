public class Locomotive {
    private int indexOfPower;
    private int operationPeriod;

    public Locomotive(int indexOfPower, int operationPeriod){
        this.indexOfPower = indexOfPower;
        this.operationPeriod = operationPeriod;
    }

    public int getIndexOfPower() {
        return indexOfPower;
    }

    public void setIndexOfPower(int indexOfPower) {
        this.indexOfPower = indexOfPower;
    }

    public int getOperationPeriod() {
        return operationPeriod;
    }

    public void setOperationPeriod(int operationPeriod) {
        this.operationPeriod = operationPeriod;
    }


}
