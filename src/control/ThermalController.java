package control;

import soc.Alert;

public class ThermalController {
	public boolean fansOn;
	String status;
	
	public ThermalController(String status)
	{
		this.status = status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public boolean balanceTemperature()
	{
		if ((status.equals(Alert.OVERHEATING.toString()))) {	
			fansOn=true;
		}
		else { 
			fansOn=false;
		}
		return fansOn;
	}
}