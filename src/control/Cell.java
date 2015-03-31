package control;

public class Cell {
	int voltage;
	int Temperature;
	int Current;
	int id;
	boolean working;
	
	public  Cell(int voltage,int Temperature,int Current, int id, boolean working){
		this.voltage=voltage;
	 	this.Current=Current;
	 	this.Temperature=Temperature;
	 	this.id=id;
	 	this.working=working;
	}
	
	public int getVoltage() {
		return voltage;
	}
	public int getTemperature() {
		return Temperature;
	}
	public int getCurrent() {
		return Current;
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
	
	public void upVoltage(int i)
	{
		voltage = voltage + i;
	}
	public void downVoltage (int i)
	{
		voltage = voltage - i;
	}
}