package main;

import static org.junit.Assert.assertEquals;
import main.BMS;
import main.CarSensor;
import main.ProcessingUnit;
import main.ValueOutOfBoundException;

import org.junit.Before;
import org.junit.Test;

import soc.BatteryMonitor;
import soc.BatteryReport;
import soc.SOCLogic;
import soh.SOHSystem;


public class BMSTest1 {
	
	/*ConcurrentHashMap<String, Object> centralStorage;*/
	CarSensor cs;
	ProcessingUnit unit;
	BMS bms;
	BatteryReport report;
	SOCLogic soc;
	BatteryMonitor monitor;
	SOHSystem sys;
	@Before
	public void setup() throws ValueOutOfBoundException
	{	String[] inputs = {"Driving","60","55","65","75","50","65","50"};
		soc=new SOCLogic();
		bms= new BMS();
		bms.storeUserInputs(inputs);
		sys=new SOHSystem();
		monitor=new BatteryMonitor(report);
		report= new BatteryReport();
		bms.chargeBatteryMonitor=new BatteryMonitor(bms.socBatteryReport);
		
		unit=new ProcessingUnit(report,sys);
		
	}


	@Test
	public void dummy() throws ValueOutOfBoundException
	{
		bms.initializeDummy();
		assertEquals("2.0",BMS.getDataInCollection(BMS.CAR_LOAD).toString());
	}
	@Test
	public void testThread()
	{
		
	}

	@Test
	public void test8StoreInputs() throws ValueOutOfBoundException
	{	
		String[] inputs={"Driving","60","55","65","75","50","65","50"};
		assertEquals(true,bms.storeUserInputs(inputs));
		assertEquals(60f,BMS.getDataInCollection(BMS.CAR_SPEED));
	}
	

	@Test
	public void test8invalidSpeed() throws ValueOutOfBoundException
	{
		String[] inputs={"Driving","2A","55","65","75","50","65","50"};
		assertEquals(false,bms.storeUserInputs(inputs));
	}
	
	@Test
	public void test8Null() throws ValueOutOfBoundException
	{
		String[] inputs={"Driving",null,"55","65","75","50","65","50"};
		assertEquals(false,bms.storeUserInputs(inputs));
	}
	@Test
	public void test9inputs() throws ValueOutOfBoundException
	{
		String[] inputs={"charging","20","55","65","75","50","65","50","25"};
		assertEquals(true,bms.storeUserInputs(inputs));
		assertEquals(BMSState.CHARGING.toString(),BMS.getBMSStatus());
	}
	
	@Test
	public void test9Null() throws ValueOutOfBoundException
	{
		String[] inputs={"charging",null,"55","65","75","50","65","50","25"};
		assertEquals(false,bms.storeUserInputs(inputs));
	}
	
	@Test
	public void test9Invalid() throws ValueOutOfBoundException
	{
		String[] inputs={"charging","NA","55","65","75","50","65","50","25"};
		assertEquals(false,bms.storeUserInputs(inputs));
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
		BMS.setBMSStatus(BMS.BMS_STATE.CHARGING);
		assertEquals(BMS.BMS_STATE.CHARGING.toString(), BMS.getBMSStatus());
	}
	
	@Test
	public void testGetDataInCollection() {
		//based on initialised values
		assertEquals(0,BMS.getDataInCollection(BMS.CHARGING_CYCLES_USED));
	}
	
	
	@Test
	public void capacityCheck()
	{
		float cap= (float) BMS.getDataInCollection(BMS.TOTAL_BATTERY_CAPACITY)/5;
		assertEquals(cap,BMS.getDataInCollection(BMS.TOTAL_CAPACITY_EACH_CELLS));
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
	
	/*@Test
	public void testMain() throws Exception, InterruptedException, ValueOutOfBoundException
	{
		BMS.main("charging","52","55","65","75","50","65","50","25");
		BMS.main("azd");
	}*/
	
}
