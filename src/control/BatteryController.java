package control;
/*CS6407 Control Team - Battery Controller
 * @author Sam O'Floinn
 * 
 * CYCLOMATIC COMPLEXITY === 6
 */

public class BatteryController {

	public boolean vehicleOn = true;//BMS.getVehicleStatus();
	public boolean chargerConnected = false; //BMS.chargerConnected();

	public BCenum testEnum;
	
	public BatteryController()
	{
		this.testEnum = BCenum.idle;
	}
	/*Things to test in the jUnit:
	 * - booleans are true at certain states AND enums are at a certain value
	 * - 
	 */
	public static void main(String[] args)
	{
		BatteryController myBC = new BatteryController();
		myBC.joyrideInViceCity();
	}
	
	synchronized public void joyrideInViceCity(){
		//ThermalController thermal = new ThermalController(50);
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
					
					testEnum=testEnum.balanceTemperature;
					
				case balanceLoad:
					//balance yo' load
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
					//someone get this hothead outta here
					System.out.println("Balance Temperature code happens now!" +
							"\nMoving from balanceTemp to active.");
					chargerConnected=false;
					vehicleOn = false;
					
					testEnum=testEnum.active;
					break;
			}
		}
	} 
}
