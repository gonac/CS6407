package control;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.Test;

import Battery.Control.BalanceLoad;
import Battery.Control.StaticStrings;

public class BalanceLoadTest {
	private final static Logger LOGGER = Logger.getLogger("");
	
	BalanceLoad b = new BalanceLoad("50", "80");
	double[] BatteryCharge = {50, 80};
	double[] CriticalChargeLimit = {40,90};
	
	double lowChargeLimit = 60;
	 
	boolean[] cellFailure = {false, true};
	
	public void setUp() {
		
	}
	
	@Test
	public void testAll() {
		for(int i = 0; i < BatteryCharge.length; i ++) {
			if(!cellFailure[i]) {
				if(BatteryCharge[i] <= lowChargeLimit) {
					testHandleLowCharge();
					if(BatteryCharge[i] <= CriticalChargeLimit[i]) {
						testHandleCriticalCharge();
					}
				}
			} else {
				testHandleCellFailure();
			}
		}
	}
	
	@Test
	public void testHandleLowCharge() {
		assertEquals(StaticStrings.chargeLimit, b.HandleLowCharge());
	}
	
	@Test
	public void testHandleCriticalCharge(){
		assertEquals(StaticStrings.CriticalChargeLimit, b.HandleCriticalCharge());
	}
	
	@Test
	public void testHandleCellFailure() {
		assertEquals(StaticStrings.cellFailure, b.HandleCellFailure());
	}
}
