package soc;


import main.BMS;

public class SOCLogic {

    private int[] cellSoc = new int[5]; //charge level of cells

    private int batterySoc; // charge level of batteries

    private float[] cellPower; // power in cells
    private float batteryPower; // power in battery

    private float cellCapacity; //cell capacity
    private float batteryCapacity; // battery capacity



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


    public void setCellCapacity(float cellCapacity) {
        this.cellCapacity = cellCapacity;
    }

    public void setBatteryCapacity(float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }



    public void setCellPower(float[] cellPower) {
        this.cellPower = cellPower;
    }

    public void setBatterySoc( ) {
        try {
            this.batterySoc = batterySOC();
            BMS.centralStorage.put(BMS.BATTERY_LEVEL, batterySoc);
        } catch (ValueOutOfBoundException e) {
            e.printStackTrace();
        }
    }

    public void setCellSoc( ) {
        try {
            this.cellSoc = cellSOC();
        } catch (ValueOutOfBoundException e) {
            e.printStackTrace();
        }
    }



    public int getBatterySoc() {
        return batterySoc;
    }

    public int[] getCellSoc() {
        return cellSoc;
    }


    public float getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower() {
        this.batteryPower = calcBatteryPower();
        BMS.centralStorage.put(BMS.BATTERY_CHARGE_AMOUNT, batteryPower);
    }

    public float[] getCellPower() {
        return cellPower;
    }
}

