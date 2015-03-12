package sn;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RUlSOHTest {
	@Test
	public void RULOutofRange() {

		int ExpectRUl=-503;

		float[]cellVoltage={120,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 17, 1000);
		int sOHresult = sOHSystem.getRUL();
		System.out.println(sOHresult);
		
		assertEquals(ExpectRUl,sOHresult);
	}
	
	@Test
	public void SOHOutofRange() {

	
		int ExpectSOH=-504;
		float[]cellVoltage={120,120,120,120,120};
		SOHSystem sOHSystem=new SOHSystem(cellVoltage, 50, (float) 17.9, 1000);
		
		int RULresult =sOHSystem.getSOH();
		
	
		assertEquals(ExpectSOH,RULresult);
	}
}
