package sn;

public class MemoryOut {
	float voltage;int time;float Current;
	 final static float InitialCapacity=18000;
	public MemoryOut(float voltage, int time, float current) {
		// TODO Auto-generated constructor stub
		this.voltage=voltage;
		this.time=time;
		this.Current=current;
		
	}

	public float getPresentCapacity() {
		// TODO Auto-generated method stub
		return time*Current;
	}

	public float getInitialCapacity() {
		// TODO Auto-generated method stub
		return InitialCapacity;
	}

}
