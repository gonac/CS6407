package soc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CellBalanceMonitorTest {

    private BatteryReport report;
    private CellBalanceMonitor monitor;

    @Before
    public void setUp() {
        this.report = new BatteryReport();
        this.monitor = new CellBalanceMonitor(report);
    }

    @Test
    public void testCheckCelBalanceForBalance() {
        monitor.checkCellBalance(new int[]{1, 1, 1, 1, 1});
        assertNull("CheckCellBalance is not working for balanced cellSOC", report.getAlert());
    }

    @Test
    public void testCheckCallBalanceForUnbalance() {
        monitor.checkCellBalance(new int[]{1, 10, 1, 10, 1});
        assertEquals("CheckCellBalance is not working for unbalanced cellSOC", report.getAlert(), Alert.UNBALANCED);

    }
}