
package soc;

import main.BMS;
import main.BMSState;

public class BatteryMonitor extends Thread {


    private boolean on = true;
    private SOCLogic soc;
    private CellBalanceMonitor cellBalanceMonitor;
    private ChargeMonitor chargeMonitor;

    private ThermalMonitor thermalMonitor;

    public BatteryMonitor(BatteryReport batteryReport) {
        this.soc = new SOCLogic();
        this.cellBalanceMonitor = new CellBalanceMonitor(batteryReport);
        this.chargeMonitor = new ChargeMonitor(batteryReport);
        this.thermalMonitor = new ThermalMonitor(batteryReport);

        float[] cellPower = getCellData();


        soc.setCellCapacity((Float) BMS.centralStorage.get(BMS.TOTAL_CAPACITY_EACH_CELLS));
        soc.setBatteryCapacity((Float) BMS.centralStorage.get(BMS.TOTAL_BATTERY_CAPACITY));
        soc.setCellPower(cellPower);
        soc.setCellSoc();
        soc.setBatteryPower();
        soc.setBatterySoc();
        thermalMonitor.setTemperature((Float) BMS.centralStorage.get(BMS.CURRENT_BATTERY_TEMPERATURE));


    }
    /*
    public static final String PRESENTCAPACITY = "currentBatteryCapacity";
    public static final String BATTERY_LEVEL = "batteryLevel";
    public static final String BATTERY_CHARGE_AMOUNT = "batteryChargeLevel";                    //Total charge amount in the battery, stored by Charge Group
     public static final String CURRENT_BATTERY_TEMPERATURE = "currentBatteryTemperature";

    Anoumt of power in cells
    *public static final String CHARGE_AMOUNT_CELL1 = "chargeCell1";                            //Amount of charge in Cell 1, provided by user
    public static final String CHARGE_AMOUNT_CELL2 = "chargeCell2";                            //Amount of charge in Cell 2, provided by user
    public static final String CHARGE_AMOUNT_CELL3 = "chargeCell3";                            //Amount of charge in Cell 3, provided by user
    public static final String CHARGE_AMOUNT_CELL4 = "chargeCell4";                            //Amount of charge in Cell 4, provided by user
    public static final String CHARGE_AMOUNT_CELL5 = "chargeCell5";
    *public static final String CURRENT_BATTERY_TEMPERATURE = "currentBatteryTemperature"
    *
    *
    * */


    private float[] getCellData() {
        int numberOfBadCells = 0;
        float[] cellData;
        cellData = new float[]{(Float) BMS.centralStorage.get(BMS.CHARGE_AMOUNT_CELL1),
                (Float) BMS.centralStorage.get(BMS.CHARGE_AMOUNT_CELL1),
                (Float) BMS.centralStorage.get(BMS.CHARGE_AMOUNT_CELL1),
                (Float) BMS.centralStorage.get(BMS.CHARGE_AMOUNT_CELL1),
                (Float) BMS.centralStorage.get(BMS.CHARGE_AMOUNT_CELL1)};

        for (int i = 0; i < cellData.length; i++) {
            if (cellData[i] < 0) {
                cellData[i] = 0;
                numberOfBadCells++;

            }

        }

        on = !(numberOfBadCells == cellData.length);

        return cellData;
    }

    public void run() {
        while (on) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (BMS.getBMSStatus().equals(BMSState.CHARGING.toString())) {
                on = true;

                try {
                    soc.setCellPower(getCellData());
                    soc.setCellSoc();
                    soc.setBatteryPower();
                    soc.setBatterySoc();
                    cellBalanceMonitor.checkCellBalance(soc.getCellSoc());
                    chargeMonitor.checkChargeLevel(soc.getBatterySoc());
                    thermalMonitor.checkTemperature();
                } catch (ValueOutOfBoundException e) {
                    e.printStackTrace();
                }
            } else if (BMS.getBMSStatus().equals(BMSState.ONMOVE.toString())) {

                on = true;
                try {

                    soc.setCellPower(getCellData());
                    soc.setCellSoc();
                    soc.setBatteryPower();
                    soc.setBatterySoc();
                    cellBalanceMonitor.checkCellBalance(soc.getCellSoc());
                    chargeMonitor.checkChargeLevel(soc.getBatterySoc());
                    thermalMonitor.checkTemperature();

                } catch (ValueOutOfBoundException e) {
                    e.printStackTrace();
                }

            } else {
                on = false;
            }
        }
    }

}
