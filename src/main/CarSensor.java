package pg;
public class CarSensor {
	
	private Float consumptionRate;	//
	private Float carSpeed;			//Speed in Km/hr
	
	CarSensor(Float _consumptionRate,Float _carSpeed) throws ValueOutOfBoundException
	{
		if(_consumptionRate<0f)
		{throw new ValueOutOfBoundException("Consumption rate value in Negative");}
		else
		{
			this.consumptionRate=_consumptionRate;
		}
		if(_carSpeed<0f)
		{throw new ValueOutOfBoundException("Speed value in Negative");}
		else
		{this.carSpeed=_carSpeed;}
	}
	

	public Float getConsumputionRate() throws ValueOutOfBoundException
	{
		
		return this.consumptionRate;
	}
	
	public Float getCarSpeed() throws ValueOutOfBoundException
	{
		
		return this.carSpeed;
	}
	
	public void updateCarSpeed(Float speed) throws ValueOutOfBoundException
	{
		if(speed<0f)
		{throw new ValueOutOfBoundException("Speed value in Negative");}
		else
		{this.carSpeed=speed;}
	}
	
	public void updateConsumptionRate(Float _consumptionRate) throws ValueOutOfBoundException
	{
		if(_consumptionRate<0)
		{throw new ValueOutOfBoundException("Consumption rate value in Negative");}
		else
		{
			this.consumptionRate=_consumptionRate;
		}
		
	}
}
