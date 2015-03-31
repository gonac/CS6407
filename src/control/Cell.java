package control;

public class Cell {
	int voltage;
	int temperature;
	int current;
	int id;
	boolean working;
	
	public Cell(int voltage,int Temperature,int Current, int id, boolean working){
		this.voltage=voltage;
	 	this.current=Current;
	 	this.temperature=Temperature;
	 	this.id=id;
	 	this.working=working;
	}
	
	//energy = time * current. do we need a public float getEnergy() to return voltage / current; ?

	public int getVoltage() {
		return voltage;
	}
	public int getTemperature() {
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
	
	public void upVoltage(int charge)
	{
		voltage = voltage + charge;
	}
	public void downVoltage (int load)
	{
		voltage = voltage - load;
	}
}