package soh;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class SOHTest {


	
	@Test
	public void CaseCellOneBroken() {
		
		int ExpectRUl=-401;
		int ExpectSOH=-401;
		float[]cellVoltage={0,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 30.11, 500);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
		
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	
	}
	
	@Test
	public void CaseCellTwoBroken() {
		
		int ExpectRUl=-402;
		int ExpectSOH=-402;
		float[]cellVoltage={120,0,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 30.11, 500);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
		
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	
	}
	
	@Test
	public void CaseCellThreeBroken() {
		
		int ExpectRUl=-403;
		int ExpectSOH=-403;
		float[]cellVoltage={120,120,0,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 30.11, 500);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
		
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	
	}
	@Test
	public void CaseCellFourBroken() {
		
		int ExpectRUl=-404;
		int ExpectSOH=-404;
		float[]cellVoltage={120,120,120,0,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 30.11, 500);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
		
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	
	}
	@Test
	public void CaseCellFiveBroken() {
		
		int ExpectRUl=-405;
		int ExpectSOH=-405;
		float[]cellVoltage={120,120,120,120,0};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 30.11, 500);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
		
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	
	}
	
	@Test
	public void CaseTemperatureWrong() {

		int ExpectRUl=-501;
		int ExpectSOH=-501;
		float[]cellVoltage={120,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 80, (float) 30.11, 500);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
		
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	}
	@Test
	public void memoryDataWrong() {

		int ExpectRUl=-502;
		int ExpectSOH=-502;
		float[]cellVoltage={120,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 30.11, 1000);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
		
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	}
	
	
	
	@Test
	public void Rightvalue() {

		int ExpectRUl=788;
		int ExpectSOH=66;
		float[]cellVoltage={120,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 40, 300);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();

		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void TestSensor() {

		int ExpectVoltageresult=120;
	
		float[]cellVoltage={120,120,120,120,120};
		SensorOut SensorOut=new SensorOut(cellVoltage, 50, (float) 40);
		int Voltageresult = (int)SensorOut.getVoltage();
		

		assertEquals(ExpectVoltageresult,Voltageresult);

	}
	@Test
	public void DangerousStatevalue() {

		int ExpectRUl=23;
		int ExpectSOH=2;
		float[]cellVoltage={120,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 10, 50);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();
	
		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	}
	@Test
	public void LowStatevalue() {

		int ExpectRUl=59;
		int ExpectSOH=5;
		float[]cellVoltage={120,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 20, 50);
		int sOHresult = sOHSystem.getRUL();
		int RULresult =sOHSystem.getSOH();

		assertEquals(ExpectRUl,sOHresult);
		assertEquals(ExpectSOH,RULresult);
	}
	
}
