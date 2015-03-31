package main;

import java.util.concurrent.ConcurrentHashMap;

import soc.BatteryMonitor;
import soc.BatteryReport;


public class BMS {


    //Keys for Storing corresponding data in memory
    //Fixed Inputs that are provided by Manufacturer
    public static final String CHARGING_CYCLES = "chargingCycles";                            //Provided by Manufacturer
    public static final String MAXIMUM_OPERATING_CURRENT = "maxOperatingCurrent";            //Max operating current provided by Manufacturer
    public static final String MINIMUM_OPERATING_CURRENT = "minOperatingCurrent";            //Min operating current provided by Manufacturer
    public static final String MAXIMUM_OPERATING_VOLTAGE = "maxOperatingVoltage";            //Max operating Voltage provided by Manufacturer
    public static final String MINIMUM_OPERATING_VOLTAGE = "minOperatingVoltage";            //Min operating Voltage provided by Manufacturer
    public static final String MAXIMUM_OPERATING_TEMPERATURE = "maxOperatingTemperature";    //Max operating current provided by Manufacturer
    public static final String MINIMUM_OPERATING_TEMPERATURE = "minOperatingTemperature";    //Min operating current provided by Manufacturer
    public static final String TOTAL_BATTERY_CAPACITY = "totalBatteryCapacity";                //Total battery capacity provided by Manufacturer
    public static final String TOTAL_CAPACITY_EACH_CELLS = "totalCapacityEachCell";				//Total capacity of each cell can hold

    //Data stored/updated by the system
    public static final String CHARGING_CYCLES_USED = "chargingCyclesUsed";                        //Charging cycles used by BMS, stored by Control Group
    public static final String CHARGING_CURRENT = "chargingCurrent";                            //Charging current, provided by user
    public static final String CHARGING_VOLTAGE = "chargingVolate";                            //Charging voltage, provided by user
    public static final String PRESENTCAPACITY = "currentBatteryCapacity";                    //Current battery capacity, stored by Health Group in the memory
    public static final String BATTERY_CHARGE_AMOUNT = "batteryChargeLevel";                    //Total charge amount in the battery, stored by Charge Group
    public static final String BATTERY_LEVEL = "batteryLevel";                                //Total battery level (%) in the batter, stored by Charge Group
    public static final String DISTANCE_TRAVELLED = "distanceTravelled";                        //Distance traveled, stored by Integration Group
    public static final String CHARGE_AMOUNT_CELL1 = "chargeCell1";                            //Amount of charge in Cell 1, provided by user
    public static final String CHARGE_AMOUNT_CELL2 = "chargeCell2";                            //Amount of charge in Cell 2, provided by user
    public static final String CHARGE_AMOUNT_CELL3 = "chargeCell3";                            //Amount of charge in Cell 3, provided by user
    public static final String CHARGE_AMOUNT_CELL4 = "chargeCell4";                            //Amount of charge in Cell 4, provided by user
    public static final String CHARGE_AMOUNT_CELL5 = "chargeCell5";                            //Amount of charge in Cell 5, provided by user
    public static final String CURRENT_BATTERY_TEMPERATURE = "currentBatteryTemperature";    //Current battery temperature, provided by user
    public static final String CAR_SPEED = "carSpeed";                                        //Car speed, provided by user
    public static final String CAR_LOAD = "carLoad";                                            //Load on car battery, stored by Control Group

    //BMS State

    public static BMSState BMS_STATE;
    public static ConcurrentHashMap centralStorage;                //Collection for storing data of BMS in (key, pair) format
    
    
    /*Required Objects of all groups which is required for operations like adding observers
     */
    public BatteryReport socBatteryReport;							//Object for storing observers to the list, used by Charge group


    //All Groups references
    BatteryMonitor chargeBatteryMonitor;
    ProcessingUnit processingUnit;


    public BMS() throws ValueOutOfBoundException {
        BMS_STATE = BMSState.IDLE;
        centralStorage = new ConcurrentHashMap<String, Object>();

        //Initializing Central Storage data elements
        centralStorage.put(BMS.CHARGING_CYCLES, new Integer(5000));
        centralStorage.put(BMS.MAXIMUM_OPERATING_VOLTAGE, new Float(220));
        centralStorage.put(BMS.MINIMUM_OPERATING_VOLTAGE, new Float(150));
        centralStorage.put(BMS.MAXIMUM_OPERATING_CURRENT, new Float(1.5));
        centralStorage.put(BMS.MINIMUM_OPERATING_CURRENT, new Float(0.5));
        centralStorage.put(BMS.MAXIMUM_OPERATING_TEMPERATURE, new Float(50));
        centralStorage.put(BMS.MINIMUM_OPERATING_TEMPERATURE, new Float(-10));
        centralStorage.put(BMS.TOTAL_BATTERY_CAPACITY, new Float(500));
        centralStorage.put(BMS.TOTAL_CAPACITY_EACH_CELLS, new Float((Float)this.getDataInCollection(BMS.TOTAL_BATTERY_CAPACITY)/5));

        centralStorage.put(BMS.CAR_SPEED, new Float(0));
        centralStorage.put(BMS.CHARGE_AMOUNT_CELL1, new Float(0));
        centralStorage.put(BMS.CHARGE_AMOUNT_CELL2, new Float(0));
        centralStorage.put(BMS.CHARGE_AMOUNT_CELL3, new Float(0));
        centralStorage.put(BMS.CHARGE_AMOUNT_CELL4, new Float(0));
        centralStorage.put(BMS.CHARGE_AMOUNT_CELL5, new Float(0));
        centralStorage.put(BMS.CURRENT_BATTERY_TEMPERATURE, new Float(0));
        centralStorage.put(BMS.CHARGING_CURRENT, new Float(0));
        centralStorage.put(BMS.CHARGING_VOLTAGE, new Float(0));
        centralStorage.put(BMS.CAR_LOAD, new Float(0));
        centralStorage.put(BMS.CHARGING_VOLTAGE, new Float(0));
        centralStorage.put(BMS.CHARGING_CYCLES_USED, new Integer(0));
        
        centralStorage.put(BMS.PRESENTCAPACITY, new Float(0));
        centralStorage.put(BMS.BATTERY_CHARGE_AMOUNT, new Float(0));
        centralStorage.put(BMS.BATTERY_LEVEL, new Integer(0));
        centralStorage.put(BMS.DISTANCE_TRAVELLED, new Float(0));
        
        
        /*Initializing all the required objects*/
        socBatteryReport=new BatteryReport();
        
        
        
        /*Initializing object for each module*/
       

        processingUnit = new ProcessingUnit(socBatteryReport);

    }

    public static String getBMSStatus() {
        return BMS_STATE.toString();
    }

    public static void setBMSStatus(BMSState b) {
        BMS_STATE = b;
    }

    //Function to store data in Collection
    public synchronized static void storeDataInCollection(String key, Object data) {
        centralStorage.put(key, data);
    }

    //Function to get data from Collection
    public static Object getDataInCollection(String key) {
        return centralStorage.get(key);
    }

    
    //Executing threads of other groups
    public void executeBMSModule()
    {
    	this.chargeBatteryMonitor.start();
    	this.processingUnit.start();
    }
    

    //Function for storing user Inputs into Memory and validating it
    public Boolean storeUserInputs(String[] args) throws ValueOutOfBoundException {
    	Float chargeRangeEachCell=(Float)this.getDataInCollection(BMS.TOTAL_CAPACITY_EACH_CELLS);
    	
        if (args.length == 0) {
            System.err.println("Input list is missing for the run! Please provide input list.");
            return false;
        } 
        else if (args[0].equalsIgnoreCase("driving") && args.length == 8) {
            setBMSStatus(BMSState.ONMOVE);
            try{
            	Float.parseFloat(args[1]);
            Float.parseFloat(args[2]);
            Float.parseFloat(args[3]);
            Float.parseFloat(args[4]);
            Float.parseFloat(args[5]);
            Float.parseFloat(args[6]);
            Float.parseFloat(args[7]);
            	}
            catch (NumberFormatException e)
            {	
           	System.out.println("Exception in inputs!!");
            	return false;	
            }
            catch (NullPointerException e)
            {
         	   return false;
            }
            centralStorage.put(BMS.CAR_SPEED, Float.parseFloat(args[1]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL1, Float.parseFloat(args[2]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL2, Float.parseFloat(args[3]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL3, Float.parseFloat(args[4]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL4, Float.parseFloat(args[5]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL5, Float.parseFloat(args[6]));
            centralStorage.put(BMS.CURRENT_BATTERY_TEMPERATURE, Float.parseFloat(args[7]));
            
            System.out.println("Temp : " + (Float)centralStorage.get(BMS.CURRENT_BATTERY_TEMPERATURE));


            //Setting Speed to Processing unit
            processingUnit.setSpeed((Float) BMS.getDataInCollection(BMS.CAR_SPEED));

            return true;
        } else if (args[0].equalsIgnoreCase("charging") && args.length == 9) {
            setBMSStatus(BMSState.CHARGING);
            try{
        	Float.parseFloat(args[1]);
            Float.parseFloat(args[2]);
            Float.parseFloat(args[3]);
            Float.parseFloat(args[4]);
            Float.parseFloat(args[5]);
            Float.parseFloat(args[6]);
            Float.parseFloat(args[7]);
            Float.parseFloat(args[8]);
            	}
            catch (NumberFormatException e)
            {	
           	System.out.println("Exception in inputs!!");
            	return false;	
            }
            catch (NullPointerException e)
            {
         	   return false;
            }
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL1, Float.parseFloat(args[1]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL2, Float.parseFloat(args[2]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL3, Float.parseFloat(args[3]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL4, Float.parseFloat(args[4]));
            centralStorage.put(BMS.CHARGE_AMOUNT_CELL5, Float.parseFloat(args[5]));
            centralStorage.put(BMS.CURRENT_BATTERY_TEMPERATURE, Float.parseFloat(args[6]));
            centralStorage.put(BMS.CHARGING_CURRENT, Float.parseFloat(args[7]));
            centralStorage.put(BMS.CHARGING_VOLTAGE, Float.parseFloat(args[8]));

            return true;
        } 
        
        else {
            System.err.println("Either undefined Mode entered or input list is wrong! Please check your inputs and enter MODE either as 'Charging' or 'Driving'.");
            return false;
        }
    }

    //Dummy Function to store values needed to run system
    public void initializeDummy() throws ValueOutOfBoundException {
        float batteryChargeAmount = (float) getDataInCollection(BMS.CHARGE_AMOUNT_CELL1) +
                (float) getDataInCollection(BMS.CHARGE_AMOUNT_CELL2) + (float) getDataInCollection(BMS.CHARGE_AMOUNT_CELL3) +
                (float) getDataInCollection(BMS.CHARGE_AMOUNT_CELL4) + (float) getDataInCollection(BMS.CHARGE_AMOUNT_CELL5);
        centralStorage.put(BMS.BATTERY_LEVEL, new Integer(50));
        centralStorage.put(BMS.BATTERY_CHARGE_AMOUNT, batteryChargeAmount);
        centralStorage.put(BMS.CAR_LOAD, new Float(2));

        processingUnit.setCarLoad((Float) BMS.getDataInCollection(BMS.CAR_LOAD));
    }
    
    
    
    
    public static void main(String... args) throws NumberFormatException, InterruptedException, ValueOutOfBoundException {
        //Declaring BMS Object
        final BMS bmsObject = new BMS();

        if (!bmsObject.storeUserInputs(args)) {
            return;
        }
        bmsObject.chargeBatteryMonitor=new BatteryMonitor(bmsObject.socBatteryReport);

        bmsObject.initializeDummy();
        
        bmsObject.executeBMSModule();

        //Thread to Run Integration Part
        


        System.out.println("\nThanks for checking out our BMS. Yo Yo!!!");
    }

}
