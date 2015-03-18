package pg;
public class GPSStub {

	private Float nextNearestPumpDistance;		//Distance in Kilo Meters
	
	GPSStub(Float distance)
	{
		nextNearestPumpDistance=distance;
	}
	
	public Float getNextNearestPumpDistance()
	{
		return this.nextNearestPumpDistance;
	}
	
	public void setNextNearestPumpDistance(Float distance)
	{
		this.nextNearestPumpDistance=distance;
	}
	
}
