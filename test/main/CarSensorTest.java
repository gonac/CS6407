package pg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CarSensorTest {
	CarSensor cs;
	@Before
	public void setUp() throws ValueOutOfBoundException
	{	
		float speed=0;
		float _consumptionRate=0;
		CarSensor cs2=cs = new CarSensor(_consumptionRate,speed);
		speed=52;
		_consumptionRate=42;
		cs = new CarSensor(_consumptionRate,speed);
		
	}


	@Test
	public void testGetConsumputionRate() throws ValueOutOfBoundException {
		assertEquals(42,cs.getConsumputionRate(),0.0f);
	}

	@Test
	public void testUpdateCarSpeed() throws ValueOutOfBoundException {
		//test1
		float speed=52f;
		cs.updateCarSpeed(speed);
		assertEquals(speed,cs.getCarSpeed(), 0.0f);

	}
	
	@Test
	public void testBoundaryUpdate() throws ValueOutOfBoundException {
		float speed=0f;
		cs.updateCarSpeed(speed);
		assertEquals(speed,cs.getCarSpeed(), 0.0f);

	}
	
	@Test(expected=ValueOutOfBoundException.class)
	public void exceptionSpeed() throws ValueOutOfBoundException
	{
		float speed=-52;
		cs.updateCarSpeed(speed);
		assertEquals(speed,cs.getCarSpeed(),0.0f);

	}


	@Test
	public void updateConsumingPattern() throws ValueOutOfBoundException
	{
		float _consumption=63f;
		cs.updateConsumptionRate(_consumption);
		assertEquals(_consumption,cs.getConsumputionRate(),0.0f);
	}
	
	@Test
	public void updateConsumingPatternBoundary() throws ValueOutOfBoundException
	{
		float _consumption=0f;
		cs.updateConsumptionRate(_consumption);
		assertEquals(_consumption,cs.getConsumputionRate(),0.0f);
	}
	
	@Test(expected=ValueOutOfBoundException.class)
	public void negativeConsumption() throws ValueOutOfBoundException
	{
		Float test=-63f;
		cs.updateConsumptionRate(test);
		assertEquals(test,cs.getConsumputionRate(),0.0f);
	}
	@Test(expected=NullPointerException.class)
	public void nullConsumption() throws ValueOutOfBoundException
	{
		Float test=null;
		cs.updateConsumptionRate(test);
		assertEquals(test,cs.getConsumputionRate(),0.0f);
	}

}
