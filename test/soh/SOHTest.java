package sn;

import static org.junit.Assert.*;

import org.junit.Test;

public class SOHTest {


	
	@Test
	public void CaseCellBroken() {
		
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
	
}
