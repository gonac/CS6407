package soh;

public class BatteryOut {

	float voltage[];float Temperature;float Current; 
	public  BatteryOut(float voltage[],float Temperature,float Current){
		this.voltage=voltage;
	 	this.Current=Current;
	 	this.Temperature=Temperature;	
	}

	public float[] getVoltage() {

			
			return voltage;
		

	}
	public float getTemperature() {
		return Temperature;
	}
	public float getCurrent() {
		return Current;
	}
	
	
}
