package control;

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
	
	public void upCharge(int charge)
	{
		charge = charge + charge;
	}
	public void downCharge (int load)
	{
		charge = charge - load;
	}
}