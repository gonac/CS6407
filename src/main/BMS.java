package pg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class BMS {

	
	//Keys for Storing corresponding data in memory
	//Fixed Inputs that are provided by Manufacturer
	static final String CHARGING_CYCLES="chargingCycles";							//Provided by Manufacturer
	static final String MAXIMUM_OPERATING_CURRENT="maxOperatingCurrent";			//Max operating current provided by Manufacturer
	static final String MINIMUM_OPERATING_CURRENT="minOperatingCurrent";			//Min operating current provided by Manufacturer
	static final String MAXIMUM_OPERATING_VOLTAGE="maxOperatingVoltage";			//Max operating Voltage provided by Manufacturer
	static final String MINIMUM_OPERATING_VOLTAGE="minOperatingVoltage";			//Min operating Voltage provided by Manufacturer
	static final String MAXIMUM_OPERATING_TEMPERATURE="maxOperatingTemperature";	//Max operating current provided by Manufacturer
	static final String MINIMUM_OPERATING_TEMPERATURE="minOperatingTemperature";	//Min operating current provided by Manufacturer
	static final String TOTAL_BATTERY_CAPACITY="totalBatteryCapacity";				//Total battery capacity provided by Manufacturer	
	
	//Data stored/updated by the system
	static final String CHARGING_CYCLES_USED="chargingCyclesUsed";						//Charging cycles used by BMS, stored by Control Group
	static final String CHARGING_CURRENT="chargingCurrent";							//Charging current, provided by user
	static final String CHARGING_VOLTAGE="chargingVolate";							//Charging voltage, provided by user
	static final String PRESENTCAPACITY="currentBatteryCapacity";					//Current battery capacity, stored by Health Group in the memory
	static final String BATTERY_CHARGE_AMOUNT="batteryChargeLevel";					//Total charge amount in the battery, stored by Charge Group
	static final String BATTERY_LEVEL="batteryLevel";								//Total battery level (%) in the batter, stored by Charge Group
	static final String DISTANCE_TRAVELLED="distanceTravelled";						//Distance traveled, stored by Integration Group
	static final String CHARGE_AMOUNT_CELL1="chargeCell1";							//Amount of charge in Cell 1, provided by user
	static final String CHARGE_AMOUNT_CELL2="chargeCell2";							//Amount of charge in Cell 2, provided by user
	static final String CHARGE_AMOUNT_CELL3="chargeCell3";							//Amount of charge in Cell 3, provided by user
	static final String CHARGE_AMOUNT_CELL4="chargeCell4";							//Amount of charge in Cell 4, provided by user
	static final String CHARGE_AMOUNT_CELL5="chargeCell5";							//Amount of charge in Cell 5, provided by user
	static final String CURRENT_BATTERY_TEMPERATURE="currentBatteryTemperature";	//Current battery temperature, provided by user
	static final String CAR_SPEED="carSpeed";										//Car speed, provided by user
	static final String CAR_LOAD="carLoad";											//Load on car battery, stored by Control Group
	
	//BMS State
	
	public static BMSState BMS_STATE;
	public static ConcurrentHashMap centralStorage;				//Collection for storing data of BMS in (key, pair) format
	
	
	//All Groups references
	ProcessingUnit processingUnit;
	
	
	public BMS() throws ValueOutOfBoundException
	{
		BMS_STATE=BMSState.IDLE;
		centralStorage=new ConcurrentHashMap<String,Object>();
		
		//Initializing Central Storage data elements
		centralStorage.put(BMS.CHARGING_CYCLES, new Integer(5000));
		centralStorage.put(BMS.MAXIMUM_OPERATING_VOLTAGE, new Float(220));
		centralStorage.put(BMS.MINIMUM_OPERATING_VOLTAGE, new Float(150));
		centralStorage.put(BMS.MAXIMUM_OPERATING_CURRENT, new Float(1.5));
		centralStorage.put(BMS.MINIMUM_OPERATING_CURRENT, new Float(0.5));
		centralStorage.put(BMS.MAXIMUM_OPERATING_TEMPERATURE, new Float(50));
		centralStorage.put(BMS.MINIMUM_OPERATING_TEMPERATURE, new Float(-10));
		
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
		centralStorage.put(BMS.TOTAL_BATTERY_CAPACITY, new Float(0));
		centralStorage.put(BMS.PRESENTCAPACITY, new Float(0));
		centralStorage.put(BMS.BATTERY_CHARGE_AMOUNT, new Float(0));
		centralStorage.put(BMS.BATTERY_LEVEL, new Float(0));
		centralStorage.put(BMS.DISTANCE_TRAVELLED, new Float(0));
		
		processingUnit=new ProcessingUnit();
		
	}
	
	public static void setBMSStatus(BMSState b)
	{
		BMS_STATE = b;
	}
	
	public static String getBMSStatus()
	{
		return BMS_STATE.toString();
	}
	
	
	//Function to store data in Collection
	public synchronized static void storeDataInCollection(String key,Object data)
	{
		centralStorage.put(key, data);
	}
	//Function to get data from Collection
	public static Object getDataInCollection(String key)
	{
		return centralStorage.get(key);
	}
	
	
	
	public void executeProcessingUnit() throws ValueOutOfBoundException
	{
		processingUnit.execute();
	}
	
	
	
	//Function for storing user Inputs into Memory and validating it
	public Boolean storeUserInputs(String [] args)
	{
		if(args.length==0)
		{
			System.err.println("Input list is missing for the run! Please provide input list.");
			return false;
		}
		else if(args[0].equalsIgnoreCase("driving") && args.length == 8)
		{
			this.setBMSStatus(BMSState.ONMOVE);
			if(Float.isNaN(Float.parseFloat(args[1])))
			{
				System.err.println("Speed is not is correct format, please provide in numeric format!!");
				return false;
			}
			else if(Float.isNaN(Float.parseFloat(args[2])) || Float.isNaN(Float.parseFloat(args[3])) || Float.isNaN(Float.parseFloat(args[4])) || Float.isNaN(Float.parseFloat(args[5])) || Float.isNaN(Float.parseFloat(args[6])))
			{
				System.err.println("Battery Charge levels are not is correct format, please provide in numeric format for 5 battery cells!!");
				return false;
			}
			else if(Float.isNaN(Float.parseFloat(args[7])))
			{
				System.err.println("Battery temperature is not in correct format, please provide in numeric format!!");
				return false;
			}
			centralStorage.put(BMS.CAR_SPEED, Float.parseFloat(args[1]));
			centralStorage.put(BMS.CHARGE_AMOUNT_CELL1, Float.parseFloat(args[2]));
			centralStorage.put(BMS.CHARGE_AMOUNT_CELL2, Float.parseFloat(args[3]));
			centralStorage.put(BMS.CHARGE_AMOUNT_CELL3, Float.parseFloat(args[4]));
			centralStorage.put(BMS.CHARGE_AMOUNT_CELL4, Float.parseFloat(args[5]));
			centralStorage.put(BMS.CHARGE_AMOUNT_CELL5, Float.parseFloat(args[6]));
			centralStorage.put(BMS.CURRENT_BATTERY_TEMPERATURE, Float.parseFloat(args[7]));
			
			
			//Setting Speed to Processing unit
			processingUnit.setSpeed((Float)BMS.getDataInCollection(BMS.CAR_SPEED));
			
			return true;
		}
		else if(args[0].equalsIgnoreCase("charging")  && args.length == 9)
		{
			this.setBMSStatus(BMSState.CHARGING);
			
			if(Float.isNaN(Float.parseFloat(args[1])) || Float.isNaN(Float.parseFloat(args[2])) || Float.isNaN(Float.parseFloat(args[3])) || Float.isNaN(Float.parseFloat(args[4])) || Float.isNaN(Float.parseFloat(args[5])))
			{
				System.err.println("Battery Charge levels are not is correct format, please provide in numeric format for 5 battery cells!!");
				return false;
			}
			else if(Float.isNaN(Float.parseFloat(args[6])))
			{
				System.err.println("Battery temperature is not in correct format, please provide in numeric format!!");
				return false;
			}
			else if(Float.isNaN(Float.parseFloat(args[7])))
			{
				System.err.println("Battery charging current is not in correct format, please provide in numeric format!!");
				return false;
			}
			else if(Float.isNaN(Float.parseFloat(args[8])))
			{
				System.err.println("Battery charging voltage is not in correct format, please provide in numeric format!!");
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
		else
		{
			System.err.println("Either undefined Mode entered or input list is wrong! Please check your inputs and enter MODE either as 'Charging' or 'Driving'.");
			return false;
		}
	}
	
	
	
	//Dummy Function to store values needed to run system
	public void initializeDummy()
	{
		float batteryChargeAmount=(float)getDataInCollection(BMS.CHARGE_AMOUNT_CELL1) + 
				(float)getDataInCollection(BMS.CHARGE_AMOUNT_CELL2) + (float)getDataInCollection(BMS.CHARGE_AMOUNT_CELL3) + 
				(float)getDataInCollection(BMS.CHARGE_AMOUNT_CELL4) +(float)getDataInCollection(BMS.CHARGE_AMOUNT_CELL5);
		centralStorage.put(BMS.BATTERY_LEVEL, new Float(50));
		centralStorage.put(BMS.BATTERY_CHARGE_AMOUNT, batteryChargeAmount);
		centralStorage.put(BMS.CAR_LOAD,new Float(2));
		
		processingUnit.setCarLoad((Float)BMS.getDataInCollection(BMS.CAR_LOAD));
	}
		
	
	
	
	public static void main(String ... args) throws NumberFormatException, InterruptedException, ValueOutOfBoundException 
	{
		//Declaring BMS Object
		final BMS bmsObject=new BMS();
		
		if(!bmsObject.storeUserInputs(args))
		{
			return;
		}
		
		bmsObject.initializeDummy();
		
		//Thread to Run Integration Part
		new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				do
				{
					
					try {
						bmsObject.executeProcessingUnit();
						Thread.sleep(1000);
					} catch (InterruptedException | ValueOutOfBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}while((Float)getDataInCollection(BMS.BATTERY_CHARGE_AMOUNT) > 0 && (Float)getDataInCollection(BMS.BATTERY_LEVEL) < 100);
			}
			
		}.run();;
		
		
		
		System.out.println("\nThanks for checking out our BMS. Yo Yo!!!");
	}
	
}
