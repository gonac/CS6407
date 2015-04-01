package control;

import main.BMS;
import main.BMSState;
import soc.Alert;
import soc.BatteryReport;
import soc.ReportObservable;

/*CS6407 Control Team - Battery Controller
 * @author Sam O'Floinn
 */

public class BatteryController extends Thread implements ReportObservable {

	//boolean vehicleOn = true;//BMS.getVehicleStatus();
	boolean chargerConnected = false; //BMS.chargerConnected();
	int chargeRate =  10; //must get this from somewhere
	int loadRate = 10; //must get this from somewhere
	String status = ""; //set this to "" in the final code
	
	BatteryReport batteryReport;
	public BatteryControlUnit bcu;
	public BCenum testEnum;
	
	
	BalanceCharge chargeB;
	BalanceLoad loadB;
	ThermalController thermB;
	
	
//BMS.CHARGE_AMOUNT_CELL1
	Cell cellOne = new Cell(20, 20, 20, 1, true);
	Cell cellTwo = new Cell(20, 20, 20, 2, true);
	Cell cellThree = new Cell(20, 20, 20, 3, true);
	Cell cellFour = new Cell(20, 20, 20, 4, true);
	Cell cellFive = new Cell(20, 20, 20, 5, true);
	Cell[] cellArray = new Cell[]{ cellOne, cellTwo, cellThree,cellFour, cellFive};
	
	public BatteryController( BatteryReport batteryReport)
	{
		this.testEnum = BCenum.idle;
		this.batteryReport = batteryReport;
		
		batteryReport.addObserver(this);
		
		bcu = new BatteryControlUnit();		
		chargeB = new BalanceCharge(cellArray, 10, bcu, status);
		loadB = new BalanceLoad(cellArray, 2, bcu, status);
		thermB = new ThermalController(status);
	}
	
	public void update()
	{
		//get the alert enum from soc
		//if alert=="UNBALANCED", then status should also be "UNBALANCED"; etc. for each alert enum.  
		
		status = batteryReport.getAlert().toString();
		//System.out.println("Battery status is " + status);
		/*
		 if ( batteryReport.getAlert().toString().equalsIgnoreCase(Alert.OVERHEATING.toString())){
			 
		 }*/
		thermB.setStatus(status);
		thermB.balanceTemperature();
	}
	
	/*public static void main(String[] args)
	{
		BatteryReport myReport = new BatteryReport();
		BatteryController myBC = new BatteryController(myReport);
		myBC.run();
	}*/
	
	public void run()
	{
		do {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //BatteryController myBC = new BatteryController(batteryReport, true);
            
    		runCode(chargeB, loadB, thermB, bcu);

        } while (!BMS.BMS_STATE.toString().equals(BMSState.DAMAGED.toString()));
		
	}
	
	synchronized public void runCode(BalanceCharge chargeB, BalanceLoad loadB, ThermalController thermB,
			BatteryControlUnit bcu){
		for (int i = 0; i < 3; i++)
		{
			switch(BMS.BMS_STATE.getStateValue())
			{
				//Idle state
				case 0:
					//muh idle code
					/*System.out.println("In idle state.");
					if (chargerConnected)
					{
						System.out.println("Moving from idle to active state.");
						testEnum = testEnum.active;
					}
					else 
					{ //do nothing
						System.out.println("Staying in idle state.");
						testEnum = testEnum.idle;
					}*/
					break;
				
				//Charging state	
				case 1:
					//balance charge, change to fit states
					System.out.println("Balance Charge code happens now!");
					chargeB.balanceCharge(chargeRate, cellArray);
					thermB.balanceTemperature();
					
				//Moving state
				case 2:
					//balance yo' load
					loadB.balanceLoad();

					thermB.balanceTemperature();
					System.err.println("Balance Load code happens now!");
					if (chargerConnected)
					{
						System.out.println("Moving from balanceLoad to balanceCharge.");
						testEnum=testEnum.balanceCharge;
					}
					else
					{
						System.out.println("Moving from balanceLoad to balanceTemp.");
						testEnum=testEnum.balanceTemperature;
					}
					break;
					
				/*case balanceTemperature:
					System.out.println("Balance Temperature code happens now!" +
							"\nMoving from balanceTemp to active.");
					/*chargerConnected=false;
					
					testEnum=testEnum.active; 
					break;*/
			}
		}
	} 
}
