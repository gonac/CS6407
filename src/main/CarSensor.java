package main;

public class CarSensor {

    private Float consumptionRate;    //

    CarSensor(Float _consumptionRate) throws ValueOutOfBoundException {
        if (_consumptionRate < 0f) {
            throw new ValueOutOfBoundException("Consumption rate value in Negative");
        } else {
            this.consumptionRate = _consumptionRate;
        }
    }


    public Float getConsumputionRate() throws ValueOutOfBoundException {

        return this.consumptionRate;
    }

    public void updateConsumptionRate(Float _consumptionRate) throws ValueOutOfBoundException {
        if (_consumptionRate < 0) {
            throw new ValueOutOfBoundException("Consumption rate value in Negative");
        } else {
            this.consumptionRate = _consumptionRate;
        }

    }
}
