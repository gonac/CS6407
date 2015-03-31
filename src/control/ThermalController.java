package control;

public class ThermalController {
	public boolean fansOn;
	String status;
	
	public ThermalController(String status)
	{
		this.status = status;
	}
	
	public boolean balanceTemperature()
	{
		fansOn=false;
		if (!(status.equals("OVERHEATED"))) {	
			fansOn=false;
		}
		else { 
			fansOn=true;
		}
		return fansOn;
	}
}