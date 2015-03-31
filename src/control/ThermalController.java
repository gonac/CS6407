package control;
/* 
 * Cyclomatic Complexity === 4
 * */

public class ThermalController {
	public int tempLimit = 80;
	public int currTemp;
	public boolean fansOn;
	
	public ThermalController(int temp)
	{
		currTemp = temp;
	}
	
	public void balanceTemperature()
	{
		if ( (0.9 * tempLimit >= currTemp) && (currTemp >= 0.8*tempLimit))		{
			System.out.println("Temperature approaching limits. Initiating batteryHot() procedures.");
			batteryHot();
		}
		else if (currTemp >= 0.9*tempLimit)		{
			System.out.println("Temperature exceeding limits. Initiating overheating procedures.");
			overheated();
		}
		else		{
			if (fansOn)
			{
				System.out.println("Fans are now off");
			}
			fansOn=false;
		}
	}
	
	public void batteryHot()	{
		fansOn=true;
		System.out.println("Battery is hot! Fans are now on!");
		outputVars();
	}
	
	public void overheated()	{
		System.out.println("BCU, disconnect the battery!");
		outputVars();
	}
	
	public void outputVars()	{
		fanStatus(fansOn);
	}
	
	public void fanStatus(boolean fans)	{
		if (fans)
		{
			System.out.println("Now the fans are on!");
		}
		else
		{
			System.out.println("Now the fans are off!");
		}
	}
}