package main;

import soc.BatteryReport;
import soc.ReportObservable;

public class ProcessingUnit extends Thread implements ProcessingUnitInterface, ReportObservable {

    //Defining Inputs from Sensors
    private Float kmLeftInBattery;
    private Float currentDistanceTraveled;        //This is the distance that Car traveled in current session
    private Float nextNearestPumpDistance;
    private CarSensor cs;
    public GPSStub gps;
    
    //Alerts of Processing Unit
    private Alert alert;


    private Float speed;
    private Float currentLoopTravelTime = (float) 1;        //Time in hours
    private Float consumptionRate;
    
    //Objects of Observers
    BatteryReport batteryReportCharge;


    ProcessingUnit(BatteryReport batteryReportCharge) throws ValueOutOfBoundException {
    	
    	//Adding reference to own class
    	this.batteryReportCharge=batteryReportCharge;
    	this.batteryReportCharge.addObserver(this);
    	
    	//Other required fields
        cs = new CarSensor(5.5f);
        gps = new GPSStub(1000f);
        currentDistanceTraveled = 0f;
        alert = main.Alert.NO_ALERT;

        speed = (Float) BMS.getDataInCollection(BMS.CAR_SPEED);
        consumptionRate = (Float) BMS.getDataInCollection(BMS.CAR_LOAD);

        nextNearestPumpDistance = gps.getNextNearestPumpDistance();
    }

    public Float getSpeed() {
        return this.speed;
    }

    public void setSpeed(Float _speed) throws ValueOutOfBoundException {
        if (_speed < 0)  {
            throw new ValueOutOfBoundException("Car speed value in Negative");
        }
        else
        {speed = _speed;}
    }

    public void setCarLoad(Float _carLoad) throws ValueOutOfBoundException {
       if(_carLoad<0)
       { throw new ValueOutOfBoundException("Consumption value in Negative");}
       else
    	{this.consumptionRate = _carLoad;}
    }

    @Override
    public Integer getChargingCyclesLeft() {
        // TODO Auto-generated method stub
        Integer a = (Integer) BMS.getDataInCollection(BMS.CHARGING_CYCLES);
        Integer b = (Integer) BMS.getDataInCollection(BMS.CHARGING_CYCLES_USED);
        return ((Integer) BMS.getDataInCollection(BMS.CHARGING_CYCLES) - (Integer) BMS.getDataInCollection(BMS.CHARGING_CYCLES_USED));
    }


    //Calculating this distance after every 1 sec
    public void setDistanceTravelledByCar() throws ValueOutOfBoundException {
        float previousDistance = (Float) BMS.getDataInCollection(BMS.DISTANCE_TRAVELLED);



        currentDistanceTraveled = (speed * currentLoopTravelTime);
        if (currentDistanceTraveled > this.getDistanceLeftInBattery()) {
            currentDistanceTraveled = this.getDistanceLeftInBattery();
        }
        BMS.storeDataInCollection(BMS.DISTANCE_TRAVELLED, (currentDistanceTraveled + previousDistance));

        nextNearestPumpDistance = nextNearestPumpDistance - currentDistanceTraveled;
        gps.setNextNearestPumpDistance(nextNearestPumpDistance);

        //this.updateBatteryChargeLevelLeft(currentDistanceTraveled);

    }


    public Float getDistanceLeftInBattery() throws ValueOutOfBoundException {

        Float chargeInBattery = (Float) BMS.getDataInCollection(BMS.BATTERY_CHARGE_AMOUNT); // Dummy Variable, need to insert the function which will get charge from Charge group

        return chargeInBattery / (consumptionRate);
    }


    public Float getTimeLeftInBattery() throws ValueOutOfBoundException {
        Float distanceLeft = getDistanceLeftInBattery(); // Dummy Variable, need to insert the function which will get charge from Charge group
        return (distanceLeft) / (speed);
    }


    /*@Override
    public void storeBatteryLevel() {
        // TODO Auto-generated method stub
        Float batteryLevel = (Float) BMS.getDataInCollection(BMS.BATTERY_LEVEL) - 10;    // Dummy Variable, need to insert the function which will get battery level from charge group
        BMS.storeDataInCollection(BMS.BATTERY_LEVEL, batteryLevel);
    }*/


    public void storeChargingBatteryLevel() {
        // TODO Auto-generated method stub
        Integer batteryLevel = (Integer) BMS.getDataInCollection(BMS.BATTERY_LEVEL) + 10;    // Dummy Variable, need to insert the function which will get battery level from charge group
        BMS.storeDataInCollection(BMS.BATTERY_LEVEL, batteryLevel);
    }


    public void showBatteryLevel() {
        // TODO Auto-generated method stub
        System.out.println("Battery Level : " + BMS.getDataInCollection(BMS.BATTERY_LEVEL));
    }


    //Dummy Stub For Charge Group
    public void updateBatteryChargeLevelLeft(Float _distanceTravelled) throws ValueOutOfBoundException {
        // TODO Auto-generated method stub
        Float chargeLeft = ((Float) BMS.getDataInCollection(BMS.BATTERY_CHARGE_AMOUNT)) - (consumptionRate * _distanceTravelled);

        BMS.storeDataInCollection(BMS.BATTERY_CHARGE_AMOUNT, chargeLeft);

    }


    @Override
    public Integer showAlerts(Alert alert) {
        // TODO Auto-generated method stub
        if ((alert.toString()).equals((Alert.ALERT_BATTERYLOW).toString())) {
            System.out.println("--------------- ALERT ------------\n\nBattery Low");
            return 1;
        } else if ((alert.toString()).equals((Alert.ALERT_OVERCHARGE).toString())) {
            System.out.println("--------------- ALERT ------------\n\nBattery Overcharge");
            return 2;
        } else if ((alert.toString()).equals((Alert.ALERT_HIGHTEMP).toString())) {
            System.out.println("--------------- ALERT ------------\n\nBattery has High Temperature");
            return 3;
        } else if ((alert.toString()).equals((Alert.ALERT_DAMAGE).toString())) {
            System.out.println("--------------- ALERT ------------\n\nBattery is damages. Please replace.");
            return 4;
        } else {
            return 0;
        }
    }
    
    
    
    
    
    @Override
	public void update() {
		if((this.batteryReportCharge.getAlert().toString()).equals((soc.Alert.OVER_DISCHARGE).toString()))
		{
			alert=Alert.ALERT_BATTERYLOW;
		}
		else if((this.batteryReportCharge.getAlert().toString()).equals((soc.Alert.OVERCHARGE).toString()))
		{
			alert=Alert.ALERT_OVERCHARGE;
		}
		else if((this.batteryReportCharge.getAlert().toString()).equals((soc.Alert.OVERHEATING).toString()))
		{
			alert=Alert.ALERT_HIGHTEMP;
		}
    	// TODO Auto-generated method stub
		showAlerts(alert);
		
	}


    public void execute() {
        try {
            if (!alert.toString().equals(Alert.ALERT_DAMAGE.toString())) {

                if (BMS.getBMSStatus().equals(BMSState.ONMOVE.toString())) {


                    System.out.println("------ Presenting GUI Output -------\n");

                    //storeBatteryLevel();
                    setDistanceTravelledByCar();

                    System.out.format("Car Speed : %.2f Km/hr \n",speed);
                    showBatteryLevel();
                    System.out.println("Charge Amount : " + BMS.getDataInCollection(BMS.BATTERY_CHARGE_AMOUNT));
                    System.out.format("Distance Left : %.2f Km \n",getDistanceLeftInBattery());
                    System.out.format("Total Distance travelled : %.2f Km \n", BMS.getDataInCollection(BMS.DISTANCE_TRAVELLED));
                    System.out.format("Time Left for next charge : %.2f hours \n", getTimeLeftInBattery());
                    System.out.println("Charging Cycles left : " + getChargingCyclesLeft());

                    System.out.format("Distance to next Pump : %.2f Km \n", Math.abs(gps.getNextNearestPumpDistance()));
                    
                    System.out.format("Battery Temperature : %.1f degree celcius \n",BMS.getDataInCollection(BMS.CURRENT_BATTERY_TEMPERATURE));
                    

                    if (alert.getType() > 0) {
                        showAlerts(alert);
                    }


                    System.out.println("\n------ GUI Output End -------\n\n");
                } else if (BMS.getBMSStatus().equals(BMSState.CHARGING.toString())) {
                    System.out.println("------ Presenting GUI Output -------\n");

                    storeChargingBatteryLevel();

                    showBatteryLevel();
                    System.out.println("Charge Amount : " + BMS.getDataInCollection(BMS.BATTERY_CHARGE_AMOUNT));
                    System.out.println("Charging Cycles left : " + getChargingCyclesLeft());

                    if (alert.getType() > 0) {
                        showAlerts(alert);
                    }

                    System.out.println("\n------ GUI Output End -------\n\n");
                }
            } else {
                System.out.println("--------------- ALERT ------------\n\nBattery is damages. Please replace.");
            }
        } catch (ValueOutOfBoundException exception) {
            System.err.println("Exception occured 1 : " + exception.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occured 2 : " + e.getStackTrace() +
                    e.getMessage());
        }
    }
    
    
    @Override
    public void run()
    {
    	do {
    		
    		this.execute();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        while ((Float) BMS.getDataInCollection(BMS.BATTERY_CHARGE_AMOUNT) > 0 && (Integer) BMS.getDataInCollection(BMS.BATTERY_LEVEL) < 100);
    	
    	BMS.BMS_STATE=BMSState.IDLE;
    }


}
