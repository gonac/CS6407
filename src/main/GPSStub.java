package main;

public class GPSStub {

    private Float nextNearestPumpDistance;        //Distance in Kilo Meters

    GPSStub(Float distance) throws ValueOutOfBoundException {
        if (distance < 0) {
            throw new ValueOutOfBoundException("Distance for next station has negative value");
        }
        else
        {nextNearestPumpDistance = distance;}
    }

    public Float getNextNearestPumpDistance() {
        return this.nextNearestPumpDistance;
    }

    public void setNextNearestPumpDistance(Float distance) throws ValueOutOfBoundException {
        if (distance< 0) {
            throw new ValueOutOfBoundException("Distance for next station has negative value");
        }
        else
        {this.nextNearestPumpDistance = distance;}
    }

}
