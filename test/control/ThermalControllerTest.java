package control;

import static org.junit.Assert.*;
import org.junit.Test;
import Battery.Control.ThermalController;

public class ThermalControllerTest {
	ThermalController therm = new ThermalController(50);
	
	@Test
	public void testOverheat()
	{
		assertTrue( !(therm.currTemp > therm.tempLimit) );
	}
	
	public void testBatteryHeating()
	{
		assertTrue( ( (therm.currTemp >= (therm.tempLimit * 0.9) ) && (therm.currTemp < therm.tempLimit) ) );
	}
	
	public void testCool()
	{
		assertTrue( (therm.currTemp < (therm.tempLimit * 0.9) ) );
	}
}