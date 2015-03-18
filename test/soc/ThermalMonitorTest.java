package soc;

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
    }

    @Test
    public void testCheckTemperatureForNormalTemp() throws ValueOutOfBoundException {

        monitor.setTemperature(50.0);
        monitor.checkTemperature();
        assertNull("CheckTemperature is not working for normal temp", report.getAlert());

    }

    @Test(expected = ValueOutOfBoundException.class)
    public void testCheckTemperatureForHighTemp() throws ValueOutOfBoundException {

        monitor.setTemperature(111.1);
        monitor.checkTemperature();
    }

    @Test(expected = ValueOutOfBoundException.class)
    public void testCheckTemperatureForLowTemp() throws ValueOutOfBoundException {

        monitor.setTemperature(-10.0);
        monitor.checkTemperature();
    }

    @Test
    public void testCheckTemperatureForOverHeating() throws ValueOutOfBoundException {

        monitor.setTemperature(80);
        monitor.checkTemperature();
        assertEquals("Overheating alert is not working", report.getAlert(), Alert.OVERHEATING);
    }


    @Test
    public void testGetTemperature() {
        monitor.getTemperatureFromSensor();
    }
}