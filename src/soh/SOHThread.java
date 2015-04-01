package soh;

import main.BMS;
import main.BMSState;
import soc.ValueOutOfBoundException;


public class SOHThread extends Thread{

	SOHSystem sOHSystem;
	public SOHThread(SOHSystem sOHSystem){
		this.sOHSystem=sOHSystem;
	}
    public void run()
    {
        boolean on = true;
		while( on ) {

			if(sOHSystem.checkBatteryStatus()==Exception.BATTERYDAMAGE)
			{
				sOHSystem.setStateOfBattery(Exception.BATTERYDAMAGE);
				break;
			}
			
			int  RULresult= sOHSystem.getRUL();
			int  sOHresult=sOHSystem.getSOH();
			
            try {
                Thread.sleep(5000);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (BMS.getBMSStatus().equals(BMSState.IDLE.toString())) {
            	break;
            }else {
            	
    			
//    			System.out.println(sOHresult);
//    			System.out.println(RULresult);	
			}


        }
    }

}
