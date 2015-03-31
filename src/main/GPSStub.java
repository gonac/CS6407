package main;

public class GPSStub {

    private Float nextNearestPumpDistance;        //Distance in Kilo Meters

    GPSStub(Float distance) throws ValueOutOfBoundException {
       
        nextNearestPumpDistance = distance;
    }

    public Float getNextNearestPumpDistance() {
        return this.nextNearestPumpDistance;
    }

    public void setNextNearestPumpDistance(Float distance) throws ValueOutOfBoundException {
    	this.nextNearestPumpDistance = distance;
    }

}
