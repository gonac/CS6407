package control;

import main.BMS;

public class ThermalController {
	public boolean fansOn;
	String status;
	
	public ThermalController(String status)
	{
		this.status = status;
	}
	
	public void setStatus(String status)
	{

		this.status=status;

		this.status = status;

	}
	
	public boolean balanceTemperature()
	{

		if ((status.equals(soc.Alert.OVERHEATING.toString()))) {	
			fansOn=true;
		}
		else { 
			fansOn=false;
		}
		BMS.storeDataInCollection(BMS.FANS_ON_OFF, fansOn);
		return fansOn;
	}
}
