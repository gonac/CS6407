package pg;
public interface ProcessingUnitInterface {
	
	public Integer getChargingCyclesLeft();
	
	public void setDistanceTravelledByCar() throws ValueOutOfBoundException;
	
	public Float getDistanceLeftInBattery() throws ValueOutOfBoundException;
	
	public Float getTimeLeftInBattery() throws ValueOutOfBoundException;
	
	public void storeBatteryLevel();
	
	public void showBatteryLevel();
	
	public void storeCarSpeed(Float speed);
	
	public void execute();
	
	public Integer showAlerts(Integer alert);
	
	//public void updateBatteryChargeLevelLeft(Float _distanceTravelled);

}
