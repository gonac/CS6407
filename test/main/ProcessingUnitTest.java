package main;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentHashMap;

import main.BMS;
import main.GPSStub;
import main.ProcessingUnit;
import main.ValueOutOfBoundException;

import org.junit.Before;
import org.junit.Test;

import soc.BatteryMonitor;
import soc.BatteryReport;
import soc.SOCLogic;
public class ProcessingUnitTest {
	public static ConcurrentHashMap<String, Object> centralStorage;	
	ProcessingUnit unit;
	BMS bms;
	Alert alert;
	GPSStub stub;
	BatteryReport report;
	SOCLogic soc;
	BatteryMonitor monitor;
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
				bms.chargeBatteryMonitor=new BatteryMonitor(bms.socBatteryReport);	
				unit = new ProcessingUnit(report);
				stub=new GPSStub(0f);
				
				
				
	}

	
	

	@Test
	public void boundaryPump() throws ValueOutOfBoundException
	{unit.setDistanceTravelledByCar();}
	
	@Test(expected=ValueOutOfBoundException.class)
	public void exceptionPumpDistance() throws ValueOutOfBoundException
	{
		stub.setNextNearestPumpDistance(-20f);	
	}
	
	@Test
	public void normal() throws ValueOutOfBoundException
	{stub.setNextNearestPumpDistance(10f);
	assertEquals(10f,stub.getNextNearestPumpDistance(),0f);
	unit.setDistanceTravelledByCar();}
	@Test
	public void atPump() throws ValueOutOfBoundException
	{stub.setNextNearestPumpDistance(0f);
	assertEquals(0f,stub.getNextNearestPumpDistance(),0f);}
	
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
		assertEquals(5000,unit.getChargingCyclesLeft(),0.0f);
	}
	
	@Test
	public void setSpeed() throws ValueOutOfBoundException
	{
		unit.setSpeed(20f);
		assertEquals(20f,unit.getSpeed(),0f);
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
	public void testAlert()
	{
		assertEquals(1,unit.showAlerts(Alert.ALERT_BATTERYLOW).intValue());
		unit.execute();
		assertEquals(4,unit.showAlerts(Alert.ALERT_DAMAGE).intValue());
		assertEquals(2,unit.showAlerts(alert.ALERT_OVERCHARGE).intValue());
		assertEquals(3,unit.showAlerts(alert.ALERT_HIGHTEMP).intValue());
		assertEquals(0,unit.showAlerts(alert.NO_ALERT).intValue());
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