package Battery.Control.JUnit;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.Test;

import Battery.Charge.SOC;
import Battery.Control.BalanceCharge;
import Battery.Control.StaticStrings;

public class BalanceChargeTest {
	
	BalanceCharge b = new BalanceCharge("50", "80");
	private final static Logger LOGGER = Logger.getLogger("");
	double BatteryCharge = 40;
	double reChargeLimit = 60;
	
	public void setUp() {
		
	}
	
	@Test
	public void testAll() {
		if(BatteryCharge < reChargeLimit) {
			testReportRateOfChargeLog();
			testReportRateOfChargeSOC();
		} else {
			testDisconnectCharger();
		}
	}
	
	@Test
	public void testDisconnectCharger() {
		assertEquals(StaticStrings.chargerDisconnect, b.disconnectCharger());
	}
	
	@Test
	public void testReportRateOfChargeLog() {
		assertEquals(StaticStrings.reportRateOfChargeToLog, b.reportRateOfCharge(LOGGER, "cellBalanceMatrix"));
	}

	@Test
	public void testReportRateOfChargeSOC() {
		assertEquals(StaticStrings.reportRateOfChargeToSOC, b.reportRateOfCharge(new SOC(), "cellBalanceMatrix"));
	}
}
