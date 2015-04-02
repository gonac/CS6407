package soc;

import main.BMS;

import java.util.Calendar;
import java.util.Random;



public class ThermalMonitor {


    public Float temp = 0f; //remember to make private

    private BatteryReport report = null;
    Random rand;


    public ThermalMonitor(BatteryReport report) {
        this.report = report;
        rand = new Random(Calendar.getInstance().getTime().getTime());
    }


    public void checkTemperature() throws ValueOutOfBoundException {

    	//System.err.println("Checking Temperature function");
       if (this.temp >= 70.0) {
            report.setAlert(Alert.OVERHEATING);
        } else if (this.temp < 0) {
            throw new ValueOutOfBoundException("Temperature is lower than 0");
        }

        this.getTemperatureFromSensor();
    }

    private void getTemperatureFromSensor() {
        /**
         * Get temperature from sensor
         */
    	//System.err.println("Called function");
       // Random rand = new Random(Calendar.getInstance().getTime().getTime());
        // get temp from [-100.0, 900.0]
        setTemperature(temp + rand.nextFloat());
    }

    protected void setTemperature(float temp)
    {
        this.temp = temp;
        BMS.centralStorage.put(BMS.CURRENT_BATTERY_TEMPERATURE, temp);
    }
}