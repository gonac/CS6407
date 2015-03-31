package control;

import main.BMS;
import soc.Alert;
import soc.BatteryReport;
import soc.ReportObservable;

/*CS6407 Control Team - Battery Controller
 * @author Sam O'Floinn
 */

public class BatteryController extends Thread implements ReportObservable {

	boolean vehicleOn = true;//BMS.getVehicleStatus();
	boolean chargerConnected = false; //BMS.chargerConnected();
	int chargeRate =  10; //must get this from somewhere
	int loadRate = 10; //must get this from somewhere
	String status = "UNBALANCED"; //set this to "" in the final code
	
	BatteryReport batteryReport;
	public BatteryControlUnit bcu = new BatteryControlUnit();
	public BCenum testEnum;
//BMS.CHARGE_AMOUNT_CELL1
	Cell cellOne = new Cell(20, 20, 20, 1, true);
	Cell cellTwo = new Cell(20, 20, 20, 2, true);
	Cell cellThree = new Cell(20, 20, 20, 3, true);
	Cell cellFour = new Cell(20, 20, 20, 4, true);
	Cell cellFive = new Cell(20, 20, 20, 5, true);
	Cell[] cellArray = new Cell[]{ cellOne, cellTwo, cellThree,cellFour, cellFive};
	
	public BatteryController( BatteryReport batteryReport )
	{
		this.testEnum = BCenum.idle;
		this.batteryReport = batteryReport;
		
		batteryReport.addObserver(this);
	}
	
	public void update()
	{
		//get the alert enum from soc
		//if alert=="UNBALANCED", then status should also be "UNBALANCED"; etc. for each alert enum.  
		
		status = batteryReport.getAlert().toString();
		System.out.println("Battery status is " + status);
		/*
		 if ( batteryReport.getAlert().toString().equalsIgnoreCase(Alert.OVERHEATING.toString())){
			 
		 }*/
	}
	
	public static void main(String[] args)
	{
		BatteryReport myReport = new BatteryReport();
		BatteryController myBC = new BatteryController(myReport);
		myBC.run();
	}
	
	public void run()
	{
		BatteryController myBC = new BatteryController(batteryReport);
		BalanceCharge chargeB = new BalanceCharge(cellArray, 10, bcu, status);
		BalanceLoad loadB = new BalanceLoad(cellArray, 2, bcu, status);
		ThermalController thermB = new ThermalController(status);
		myBC.runCode(chargeB, loadB, thermB, bcu);
	}
	
	synchronized public void runCode(BalanceCharge chargeB, BalanceLoad loadB, ThermalController thermB,
			BatteryControlUnit bcu){
		for (int i = 0; i < 3; i++)
		{
			switch(testEnum)
			{
				case idle:
					//muh idle code
					System.out.println("In idle state.");
					if (vehicleOn || chargerConnected)
					{
						System.out.println("Moving from idle to active state.");
						testEnum = testEnum.active;
					}
					else 
					{ //do nothing
						System.out.println("Staying in idle state.");
						testEnum = testEnum.idle;
					}
					break;
					
				case active:
					//muh Active code
					System.out.println("In active state.");
					if (!vehicleOn && !chargerConnected)
					{
						System.out.println("Moving from active to idle.");
						testEnum = testEnum.idle;
					}
					else if (vehicleOn)
					{
						System.out.println("Moving from active to balanceLoad.");
						testEnum = testEnum.balanceLoad;
					}
					break;
					
				case balanceCharge:
					//balance charge, change to fit states
					System.out.println("Balance Charge code happens now!");
					chargeB.balanceCharge(chargeRate, cellArray);
					
					testEnum=testEnum.balanceTemperature;
					
				case balanceLoad:
					//balance yo' load
					loadB.balanceLoad();
					System.out.println("Balance Load code happens now!");
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
					
				case balanceTemperature:
					System.out.println("Balance Temperature code happens now!" +
							"\nMoving from balanceTemp to active.");
					thermB.balanceTemperature();
					/*chargerConnected=false;
					vehicleOn = false;
					
					testEnum=testEnum.active; */
					break;
			}
		}
	} 
}
