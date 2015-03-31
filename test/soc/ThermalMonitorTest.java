package soc;

import main.BMS;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ThermalMonitorTest {

    private BatteryReport report;
    private ThermalMonitor monitor;

    @Before
    public void setUp() {
        this.report = new BatteryReport();
        this.monitor = new ThermalMonitor(report);
        try {
            BMS bms = new BMS();
        } catch (main.ValueOutOfBoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCheckTemperatureForNormalTemp() throws ValueOutOfBoundException {

        monitor.setTemperature(50.0f);
        monitor.checkTemperature();
        assertNull("CheckTemperature is not working for normal temp", report.getAlert());

    }


    @Test(expected = ValueOutOfBoundException.class)
    public void testCheckTemperatureForLowTemp() throws ValueOutOfBoundException {

        monitor.setTemperature(-10.0f);
        monitor.checkTemperature();
    }

    @Test
    public void testCheckTemperatureForOverHeating() throws ValueOutOfBoundException {

        monitor.setTemperature(80);
        monitor.checkTemperature();
        assertEquals("Overheating alert is not working", report.getAlert(), Alert.OVERHEATING);
    }


//    @Test
//    public void testGetTemperature() {
//        monitor.getTemperatureFromSensor();
//    }
}