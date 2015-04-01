
package main;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentHashMap;

import main.BMS;
import main.GPSStub;
import main.ProcessingUnit;
import main.ValueOutOfBoundException;

import org.junit.Before;
import org.junit.Test;

import soc.Alert;
import soc.BatteryMonitor;
import soc.BatteryReport;
import soc.SOCLogic;
import soh.SOHSystem;
public class ProcessingUnitTest {
	public static ConcurrentHashMap<String, Object> centralStorage;	
	ProcessingUnit unit;
	BMS bms;
	Alert alert;
	GPSStub stub;
	BatteryReport report;
	SOCLogic soc;
	BatteryMonitor monitor;
	SOHSystem sys;
	//CarSensor cs;
	@Before
	public void setup() throws ValueOutOfBoundException
	{	
				bms = new BMS();
				String[] inputs = {"Driving","60","55","65","75","50","65","50"};
				bms.storeUserInputs(inputs);
				bms.initializeDummy();
				monitor=new BatteryMonitor(report);
				report= new BatteryReport();
				sys=new SOHSystem();
				bms.chargeBatteryMonitor=new BatteryMonitor(bms.socBatteryReport);	
				unit = new ProcessingUnit(report,sys);
				stub=new GPSStub(100f);
				
				
	}

	
	

	@Test
	public void boundaryPump() throws ValueOutOfBoundException
	{
	unit.setDistanceTravelledByCar();
	assertEquals(60.0f,BMS.getDataInCollection(BMS.DISTANCE_TRAVELLED));
	}	
	@Test
	public void normal() throws ValueOutOfBoundException
	{unit.gps.setNextNearestPumpDistance(200f);
	assertEquals(200f,unit.gps.getNextNearestPumpDistance(),0f);
	unit.setDistanceTravelledByCar();
	unit.setDistanceTravelledByCar();
	assertEquals(120f,BMS.getDataInCollection(BMS.DISTANCE_TRAVELLED));
	//1000-120
	assertEquals(880f,unit.gps.getNextNearestPumpDistance(),0f);
	}
	
	@Test
	public void testDistBattery() throws ValueOutOfBoundException
	{	
		unit.setCarLoad(20f);
		unit.setSpeed(150f);
		unit.setDistanceTravelledByCar();
		assertEquals(984.5,unit.gps.getNextNearestPumpDistance(),0.0f);
		assertEquals(15.5f,unit.getDistanceLeftInBattery(),0.0f);
		
	}
	
	@Test
	public void atPump() throws ValueOutOfBoundException
	{unit.gps.setNextNearestPumpDistance(0f);
	assertEquals(0f,unit.gps.getNextNearestPumpDistance(),0f);}
	
	@Test
	public void testObservableAlerts()
	{	
		report.setAlert(Alert.OVER_DISCHARGE);
		unit.update();
		assertEquals(Alert.OVER_DISCHARGE,report.getAlert());
		report.setAlert(Alert.OVERCHARGE);
		unit.update();
		assertEquals(Alert.OVERCHARGE,report.getAlert());
		report.setAlert(Alert.OVERHEATING);
		unit.update();
		assertEquals(Alert.OVERHEATING,report.getAlert());
		report.setAlert(Alert.UNBALANCED);
		unit.update();
		assertEquals(Alert.UNBALANCED,report.getAlert());
	}
	
	@Test
	public void storeLevel()
	{	
		int current=(int) BMS.getDataInCollection(BMS.BATTERY_LEVEL);
		unit.storeChargingBatteryLevel();
		assertEquals(current+10,BMS.getDataInCollection(BMS.BATTERY_LEVEL));
	}
	
	@Test
	public void testGetSpeed() {
		//as set in setup()
		assertEquals(60,unit.getSpeed(),0.0f);
	}
	@Test
	public void testUpdateSpeed() throws ValueOutOfBoundException
	{
		unit.setSpeed(50f);
		assertEquals(50f,unit.getSpeed(),0.0f);
	}
	@Test
	public void testGetChargingCyclesLeft() {
		BMS.storeDataInCollection(BMS.CHARGING_CYCLES_USED, 40);
		assertEquals(4960,unit.getChargingCyclesLeft(),0.0f);
	}
	
	@Test(expected=ValueOutOfBoundException.class)
	public void invalidSpeed() throws ValueOutOfBoundException
	{
		unit.setSpeed(-42f);
	}
	
	@Test
	public void boundarySpeed() throws ValueOutOfBoundException
	{
		unit.setSpeed(0f);
		assertEquals(0f,unit.getSpeed(),0f);
	}
	
	@Test
	public void testExecute()
	{
		unit.execute();
		assertEquals(60f,BMS.getDataInCollection(BMS.DISTANCE_TRAVELLED));
	}
	
	@Test(expected=NullPointerException.class)
	public void nullSpeed() throws ValueOutOfBoundException
	{
		unit.setSpeed(null);
	}
	
	@Test
	public void testGetDistanceLeftInBattery() throws ValueOutOfBoundException {
		//chargeInBattery/(consumptionRate)=155
		assertEquals(155,unit.getDistanceLeftInBattery(),0.0f);
	}

	@Test
	public void testGetTimeLeftInBattery() throws ValueOutOfBoundException {
		//(distanceLeft)/(speed)=2.58
		assertEquals(2.58,unit.getTimeLeftInBattery(),0.1f);
	}
	

	@Test
	public void testLocalAlert()
	{
		assertEquals(1,unit.showAlerts(main.Alert.ALERT_BATTERYLOW).intValue());
		assertEquals(4,unit.showAlerts(main.Alert.ALERT_DAMAGE).intValue());
		assertEquals(2,unit.showAlerts(main.Alert.ALERT_OVERCHARGE).intValue());
		assertEquals(3,unit.showAlerts(main.Alert.ALERT_HIGHTEMP).intValue());
		assertEquals(0,unit.showAlerts(main.Alert.NO_ALERT).intValue());
	}
	
	@Test
	public void testSOHAlert()
	{
		sys.setStateOfBattery(soh.Exception.BATTERYDAMAGE);
		assertEquals(-400,sys.getStateOfBattery());
		unit.updateSOH();
		//assertEquals(4,unit.getState());
		assertEquals(BMS.BMS_STATE.DAMAGED.toString(),BMS.getBMSStatus());
		//assertEquals(4,unit.showAlerts(main.Alert.ALERT_DAMAGE).intValue());
		
	}
	
	@Test
	public void updateCharge() throws ValueOutOfBoundException
	{
		Float distanceTravelled=100f;
		unit.updateBatteryChargeLevelLeft(distanceTravelled);
		//(BMS.BATTERY_CHARGE_AMOUNT)) - (consumptionRate*_distanceTravelled)
		assertEquals(110f,BMS.getDataInCollection(BMS.BATTERY_CHARGE_AMOUNT));
	}
	

	@Test
	public void setLoad() throws ValueOutOfBoundException
	{
		float _carLoad=20f;
		unit.setCarLoad(_carLoad);
		assertEquals(2f,BMS.getDataInCollection(BMS.CAR_LOAD));
	}
	
	@Test(expected=ValueOutOfBoundException.class)
	public void setLoadOutOfBound() throws ValueOutOfBoundException
	{
		float _carLoad=-20f;
		unit.setCarLoad(_carLoad);
	}
	@Test(expected=NullPointerException.class)
	public void carLoadNull() throws ValueOutOfBoundException
	{
		float _carLoad=(Float) null;
		unit.setCarLoad(_carLoad);
	
	}
	@Test
	public void carLoadBoundary() throws ValueOutOfBoundException
	{
		float _carLoad=0f;
		unit.setCarLoad(_carLoad);
	}
}
