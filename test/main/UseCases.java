package pg;

import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

public class UseCases {
	public static ConcurrentHashMap<String, Object> centralStorage;	
	ProcessingUnit unit;
	BMS bms;
	@Before
	public void testSetup() throws ValueOutOfBoundException {
		/*String[] inputs={"Driving","60","55","65","75","50","65","50"};
		bms.storeUserInputs(inputs);
		bms.initializeDummy();
		bms=new BMS();*/
	}
	
	@Test
	public void normal() throws ValueOutOfBoundException
	{	bms=new BMS();
		String[] inputs={"Driving","60","55","65","75","50","65","50"};
		bms.storeUserInputs(inputs);
		bms.initializeDummy();
		unit=new ProcessingUnit();
		bms.executeProcessingUnit();
		unit.execute();
		assertEquals(BMS.BMS_STATE.ONMOVE.toString(),BMS.getBMSStatus());
	
	}
	@Test
	public void charging() throws ValueOutOfBoundException
	{	bms=new BMS();
		String[] inputs={"charging","60","55","65","75","50","65","55","52"};
		bms.storeUserInputs(inputs);
		bms.initializeDummy();
		unit=new ProcessingUnit();
		unit.execute();
		assertEquals(BMS.BMS_STATE.CHARGING.toString(),BMS.getBMSStatus());
	
	}
	
	
}
