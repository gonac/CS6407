package pg;

import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;


public class BMSTest1 {
	
	/*ConcurrentHashMap<String, Object> centralStorage;*/
	CarSensor cs;
	ProcessingUnit unit;
	BMS bms;
	@Before
	public void setup() throws ValueOutOfBoundException
	{
		bms= new BMS();
		String[] inputs = {"Driving","60","55","65","75","50","65","50"};
		bms.storeUserInputs(inputs);
		bms.initializeDummy();
		unit=new ProcessingUnit();
		
	}




	@Test
	public void test8StoreInputs() throws ValueOutOfBoundException
	{
		String[] inputs={"Driving","60","55","65","75","50","65","50"};
		bms.storeUserInputs(inputs);
		
	}
	@Test
	public void testStoreDataInCollection() {
		Float distance=100f ;
		BMS.storeDataInCollection(BMS.DISTANCE_TRAVELLED, distance);
		/*centralStorage.put(BMS.DISTANCE_TRAVELLED, distance);*/
		assertEquals(distance,BMS.getDataInCollection(BMS.DISTANCE_TRAVELLED));
	}
	@Test
	public void getStatusTest()
	{
		assertEquals(BMS.BMS_STATE.ONMOVE.toString(),BMS.getBMSStatus());
	}
	@Test
	public void setStatus()
	{
		BMS.setBMSStatus(BMS.BMS_STATE.ONMOVE);
		assertEquals(BMS.BMS_STATE.ONMOVE.toString(), BMS.getBMSStatus());
	}
	
	@Test
	public void testGetDataInCollection() {
		//based on initialised values
		assertEquals(0,BMS.getDataInCollection(BMS.CHARGING_CYCLES_USED));
	}
	
	@Test
	public void processing() throws ValueOutOfBoundException
	{
		bms.executeProcessingUnit();
	}
	
	@Test
	public void testZeroArgs() throws ValueOutOfBoundException
	{
		String test[]={};
		bms.storeUserInputs(test);
		assertEquals(false,bms.storeUserInputs(test));
	}
	
	@Test
	public void testInvalidArgs() throws ValueOutOfBoundException
	{
		String test[]={"driving"};
		assertEquals(false,	bms.storeUserInputs(test));
	}
	
}
