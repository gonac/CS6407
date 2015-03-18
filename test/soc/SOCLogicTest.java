package soc;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SOCLogicTest {

    private SOCLogic socLogic;

    @Before
    public void setUp() throws Exception {
        //this.report = new BatteryReport();
        this.socLogic = new SOCLogic();
    }

    @Test
    public void testCellSOCValidValues() throws Exception {
        socLogic.setUp(new int[]{40, 39, 40, 41, 42}, 50,
                new float[]{45, 46, 47, 43, 50}, 250, 100, 500);
        socLogic.cellSOC();
        assertArrayEquals("SOCLogic calculating wrong values", new int[]{45, 46, 47, 43, 50}, socLogic.getCellSoc());
    }

    @Test(expected = ValueOutOfBoundException.class)
    public void testCellSOCInvalidNormalValues() throws Exception {
        socLogic.setUp(new int[]{100, 101, 99, 101, 100}, 50,
                new float[]{100, 99, 100, 101, 90}, 280, 50, 250);
        socLogic.cellSOC();
    }


    @Test
    public void testBatterySOCValid() throws ValueOutOfBoundException {
        socLogic.setUp(new int[]{100, 101, 99, 101, 100}, 50,
                new float[]{45, 46, 47, 43, 45}, 250, 50, 500);
        socLogic.batterySOC();
        assertEquals("SOCLogic calculating wrong values", 50, socLogic.getBatterySoc());
    }

    @Test(expected = ValueOutOfBoundException.class)
    public void testBatterySOCInvalid() throws ValueOutOfBoundException {
        socLogic.setUp(new int[]{100, 101, 99, 101, 100}, 50,
                new float[]{75, 70, 80, 70, 60}, 280, 50, 250);
        socLogic.batterySOC();
    }
}