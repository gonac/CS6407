package soc;

import main.BMS;

import java.util.Calendar;
import java.util.Random;



public class ThermalMonitor {


    public Float temp = 0f; //remember to make private

    private BatteryReport report = null;


    public ThermalMonitor(BatteryReport report) {
        this.report = report;
    }


    public void checkTemperature() throws ValueOutOfBoundException {

        if (this.temp > 100.0) {
            throw new ValueOutOfBoundException("Temperature is higher than 100");
        } else if (this.temp >= 70.0) {
            report.setAlert(Alert.OVERHEATING);
        } else if (this.temp < 0) {
            throw new ValueOutOfBoundException("Temperature is lower than 0");
        }
        temp += 0.5f;
        BMS.centralStorage.put(BMS.CURRENT_BATTERY_TEMPERATURE, temp);
        getTemperatureFromSensor();
    }

    private void getTemperatureFromSensor() {
        /**
         * Get temperature from sensor
         */
        //Random rand = new Random(Calendar.getInstance().getTime().getTime());
        // get temp from [-100.0, 900.0]
        //this.temp += rand.nextFloat();
    }

    protected void setTemperature(float temp)
    {
        this.temp = temp; //(Double)  BMS.centralStorage.get(BMS.CURRENT_BATTERY_TEMPERATURE);
    }
}