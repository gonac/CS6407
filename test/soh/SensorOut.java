package sn;

public class SensorOut {
	float voltage[];float Temperature;float Current;
	public  SensorOut(float voltage[],float Temperature,float Current){
		this.voltage=voltage;
	 	this.Current=Current;
	 	this.Temperature=Temperature;
	
	}
	public float getVoltage() {
		float av_voltage;
		int sum = 0;
		for(int i=0;i<voltage.length;i++){
			sum+=voltage[i];
		}
		av_voltage=(float)sum/voltage.length;
				return av_voltage;
	}
	public float getTemperature() {
		return Temperature;
	}
	public float getCurrent() {
		return Current;
	}
	
	
}
