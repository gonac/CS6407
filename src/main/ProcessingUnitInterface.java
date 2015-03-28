package main;

public interface ProcessingUnitInterface {

    public Integer getChargingCyclesLeft();

    public void setDistanceTravelledByCar() throws ValueOutOfBoundException;

    public Float getDistanceLeftInBattery() throws ValueOutOfBoundException;

    public Float getTimeLeftInBattery() throws ValueOutOfBoundException;

    public void showBatteryLevel();

    public Integer showAlerts(Alert alert);

    //public void updateBatteryChargeLevelLeft(Float _distanceTravelled);

}
