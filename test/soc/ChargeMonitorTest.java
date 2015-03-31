package soc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChargeMonitorTest {
    private BatteryReport report;
    private ChargeMonitor monitor;

    @Before
    public void setUp() {
        this.report = new BatteryReport();
        this.monitor = new ChargeMonitor(report);
    }

    @Test
    public void testCheckChargeLevelForOverCharge() throws ValueOutOfBoundException {
        this.monitor.checkChargeLevel(100);
        assertEquals("CheckChargeLevel is not working for over charge", Alert.OVERCHARGE, report.getAlert());
    }

    @Test
    public void testCheckChargeLevelForOverDischarge() throws ValueOutOfBoundException {
        this.monitor.checkChargeLevel(9);
        assertEquals("CheckChargeLevel is not working for over discharge", Alert.OVER_DISCHARGE, report.getAlert());
    }

    @Test(expected = ValueOutOfBoundException.class)
    public void testCheckChargeLevelForExcessLevel() throws ValueOutOfBoundException {
        this.monitor.checkChargeLevel(110);
    }

    @Test(expected = ValueOutOfBoundException.class)
    public void testCheckChargeLevelForBelowMinimLevel() throws ValueOutOfBoundException {
        this.monitor.checkChargeLevel(-10);
    }
}