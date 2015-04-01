package control;

import main.BMS;

public class Cell {
	float charge;
	float temperature;
	int current;
	int id;
	boolean working;
	
	public Cell(float charge,float Temperature,int Current, int id, boolean working){
		this.charge=charge;
	 	this.current=Current;
	 	this.temperature=Temperature;
	 	this.id=id;
	 	this.working=working;
	}
	
	//energy = time * current. do we need a public float getEnergy() to return charge / current; ?

	public float getCharge() {
		return charge;
	}
	public float getTemperature() {
		return temperature;
	}
	public int getCurrent() {
		return current;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isWorking()
	{
		return working;
	}
	
	public void cellBroke()
	{
		working = false;
	}
	
	public void upCharge(int charge, int index)
	{
		this.charge = this.charge + charge;
		if(this.charge>100)
		{
			this.charge=100;
		}
		if(index==0)
		{
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL1, this.charge);
		}
		else if(index==1)
		{
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL2, this.charge);
		}
		else if(index==2)
		{
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL3, this.charge);
		}
		else if(index==3)
		{
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL4, this.charge);
		}
		else if(index==4)
		{
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL5, this.charge);
		}
	}
	public void downCharge (int load)
	{
		charge = charge - load;
		if(charge<0)
		{
			charge=0;
		}
	}
}