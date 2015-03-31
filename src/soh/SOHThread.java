package soh;

import main.BMS;
import main.BMSState;
import soc.ValueOutOfBoundException;


public class SOHThread extends Thread{

	SOHSystem sOHSystem;
	public SOHThread(){
		this.sOHSystem=sOHSystem;
	}
    public void run()
    {
        boolean on = true;
		while( on ) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (BMS.getBMSStatus().equals(BMSState.IDLE.toString())) {
            	
            }else {
            	SOHSystem sOHSystem=new SOHSystem();
   
    			int  RULresult= sOHSystem.getRUL();
    			int  sOHresult=sOHSystem.getSOH();
//    			System.out.println(sOHresult);
//    			System.out.println(RULresult);	
			}


        }
    }

}
