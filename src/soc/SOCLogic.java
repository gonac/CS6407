package soc;


public class SOCLogic {

    private int[] cellSoc = new int[5];

    private int batterySoc;

    private float[] cellPower;
    private float batteryPower;

    private float cellCapacity;
    private float batteryCapacity;


    //key battery capacity --  capacity

    public SOCLogic() {

    }

    public void setUp(int batterySoc, float[] cellPower, float batteryPower, float cellCapacity, float batteryCapacity) {
        this.batterySoc = batterySoc;
        this.cellPower = cellPower;
        this.batteryPower = batteryPower;
        this.cellCapacity = cellCapacity;
        this.batteryCapacity = batteryCapacity;
    }


    public float[] calcCellPower(float[] loadPerCell) {

        for (int i = 0; i < cellPower.length; i++) {

            cellPower[i] = cellPower[i] - loadPerCell[i];
        }
        return cellPower;
    }

    public float calcBatteryPower() {

        float batteryPower = 0;
        for (int i = 0; i < cellPower.length; i++) {

            batteryPower += cellPower[i];
        }
        return batteryPower;
    }


    public int[] cellSOC() throws ValueOutOfBoundException {


        for (int i = 0; i < cellPower.length; i++) {
            this.cellSoc[i] = (int) ((cellPower[i] / cellCapacity) * 100);
            if (cellSoc[i] > 100)
                throw new ValueOutOfBoundException("Value out of bounds");
        }
        return cellSoc;
    }

    public int batterySOC() throws ValueOutOfBoundException {

        int batterySOC = (int) ((batteryPower / batteryCapacity) * 100);
        if (batterySOC > 100)
            throw new ValueOutOfBoundException("Value out of bounds");
        return batterySOC;
    }

    public int getBatterySoc() {
        return batterySoc;
    }

    public int[] getCellSoc() {
        return cellSoc;
    }
}
