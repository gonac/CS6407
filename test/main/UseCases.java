package main;

import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import soc.BatteryMonitor;
import soc.BatteryReport;
import soc.SOCLogic;

public class UseCases {
	public static ConcurrentHashMap<String, Object> centralStorage;	
	ProcessingUnit unit;
	BMS bms;
	BatteryReport report;
	SOCLogic soc;
	BatteryMonitor monitor;
	@Before
	public void testSetup() throws ValueOutOfBoundException {
		String[] inputs={"Driving","60","55","65","75","50","65","50"};
		bms=new BMS();
		bms.storeUserInputs(inputs);
		bms.initializeDummy();
		monitor=new BatteryMonitor(report);
		report= new BatteryReport();
		bms.chargeBatteryMonitor=new BatteryMonitor(bms.socBatteryReport);
		
		unit=new ProcessingUnit(report);

	}
	
	@Test
	public void normal() throws ValueOutOfBoundException
	{	bms=new BMS();
		String[] inputs={"Driving","60","55","65","75","50","65","50"};
		bms.storeUserInputs(inputs);
		bms.initializeDummy();
		unit.execute();
		assertEquals(BMS.BMS_STATE.ONMOVE.toString(),BMS.getBMSStatus());
	
	}
	@Test
	public void charging() throws ValueOutOfBoundException
	{	bms=new BMS();
		String[] inputs={"charging","60","55","65","75","50","65","55","52"};
		bms.storeUserInputs(inputs);
		bms.initializeDummy();
		unit.execute();
		assertEquals(BMS.BMS_STATE.CHARGING.toString(),BMS.getBMSStatus());
	
	}
	
	
}
