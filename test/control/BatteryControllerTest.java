package Battery.Control.JUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Battery.Control.BatteryController;

public class BatteryControllerTest {

	BatteryController bc = new BatteryController();
	
	@Test
	public void testIdle()
	{
		//assertTrue(bc.testEnum == BCenum.idle);
		assertFalse(!(bc.vehicleOn || bc.chargerConnected));
	}
	
	@Test
	public void testActive()
	{
		//assertTrue(bc.testEnum == BCenum.active);
		assertTrue(bc.vehicleOn || bc.chargerConnected);
	}
	
	@Test
	public void testBalancingCharge()
	{
		//assertTrue(bc.testEnum == BCenum.balanceCharge);
		assertTrue(bc.chargerConnected);
	}
	
	@Test
	public void testBalancingLoad()
	{
		//assertTrue(bc.testEnum == BCenum.balanceLoad);
		assertTrue(bc.vehicleOn);
	}
	
	
	@Test
	public void testBalancingTemp() {
		//assertTrue(bc.testEnum == BCenum.balanceTemperature);
		assertTrue(bc.vehicleOn);
	}
}