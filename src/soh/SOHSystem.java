package soh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import main.BMS;
import soc.ReportObservable;

public class SOHSystem {

	private ArrayList<SOHObserver> observers = new ArrayList<SOHObserver>();

	float voltage[];
	float Temperature;
	float Current;
	int time;
	int brokenNo=0;
	float PresentCapacity_me;
	float InCA_me;
	CPUOut cpuOut;
	SOHSystem s;

	private int stateOfBattery;

	public int getStateOfBattery() {
		return stateOfBattery;
	}

	public void setStateOfBattery(int stateOfBattery) {
		this.stateOfBattery = stateOfBattery;
		notifyAllObservers();
	}

	public void addObserver(SOHObserver observer) {
		observers.add(observer);
	}

	public void notifyAllObservers() {
		for (SOHObserver observer : observers) {
			observer.updateSOH();
		}
	}

	public SOHSystem() {
		// TODO Auto-generated constructor stub

		float[] voltageArr = { (float) (Math.random() * 120),
				(float) (Math.random() * 120), (float) (Math.random() * 120),
				(float) (Math.random() * 120), (float) (Math.random() * 120) };
		//float [] voltageArr = {121f,121f,121f,121f,121f};
		this.voltage = voltageArr;
		this.Current = (float) (Math.random() * 40);
		// if(this.Current < 1f)
		// {
		// this.Current = 1f;
		// }
		this.Temperature = (Float) BMS
				.getDataInCollection(BMS.CURRENT_BATTERY_TEMPERATURE);
		this.time = (int) (Math.random() * 500);

	}

	BatteryOut getDataFromBattery() {
		BatteryOut batteryOut = new BatteryOut(voltage, Temperature, Current);

		return batteryOut;
	}

	
	
	int getBrokenCell() {
		int broken = 0;
		for (int i = 0; i < voltage.length; i++) {
			if (voltage[i] <= 0 || voltage[i] > 120) {
				broken += 1;
				brokenNo=i+1;
			} else if (i == 0
					&& (Float) BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL1) < 0) {
				broken++;
			} else if (i == 0
					&& (Float) BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL2) < 0) {
				broken++;
			} else if (i == 0
					&& (Float) BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL3) < 0) {
				broken++;
			} else if (i == 0
					&& (Float) BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL4) < 0) {
				broken++;
			} else if (i == 0
					&& (Float) BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL5) < 0) {
				broken++;
			}

		}

		return broken;

	}
			int getCellNO(){
				return brokenNo;
				
			}
	SensorOut getDataFromSensor(BatteryOut batteryOut) {
		float[] v_BO = batteryOut.getVoltage();
		float T_BO = batteryOut.getTemperature();
		float c_BO = batteryOut.getCurrent();

		SensorOut sensorOut = new SensorOut(v_BO, T_BO, c_BO);
		return sensorOut;

	}

	@SuppressWarnings({ "unused", "static-access" })
	void storeDataToMemory(SensorOut sensorOut) {

		float v_SO = sensorOut.getVoltage();
		float T_SO = sensorOut.getTemperature();
		float c_SO = sensorOut.getCurrent();

		MemoryOut memoryOut = new MemoryOut(v_SO, time, c_SO);
		// HashMap<String,float> hashMap = new HashMap<String,float>();

		float PresentCapacity = memoryOut.getPresentCapacity();
		float InCA = memoryOut.getInitialCapacity();
		// float PresentCapacity = 15000;
		// float InCA = 18000;
		BMS.storeDataInCollection(BMS.PRESENTCAPACITY, PresentCapacity);
		BMS.storeDataInCollection(BMS.TOTAL_BATTERY_CAPACITY, InCA);
	}

	@SuppressWarnings("static-access")
	void getDataFromCPU() {

		PresentCapacity_me = (float) BMS
				.getDataInCollection(BMS.PRESENTCAPACITY);
		InCA_me = (float) BMS.getDataInCollection(BMS.TOTAL_BATTERY_CAPACITY);

	}

	@SuppressWarnings("static-access")
	int getRUL() {

		BatteryOut batteryOut = this.getDataFromBattery();
		SensorOut sensorOut = this.getDataFromSensor(batteryOut);
		float T = sensorOut.getTemperature();
		Exception expection = new Exception();
		int result = 0;
		int brokenCell = getBrokenCell();
		
		if (brokenCell < 5) {
			this.storeDataToMemory(sensorOut);
			getDataFromCPU();
			if (PresentCapacity_me <= 18000 && PresentCapacity_me >= 0) {
				cpuOut = new CPUOut(PresentCapacity_me, InCA_me);
				int RUL = cpuOut.getRUL();
				result = RUL;
				//System.err.println("Battery Life : " + result);

			} else {
				result = expection.memoryDataWrong;
			}

		} else {
			result = expection.BATTERYDAMAGE;
			
			
			return result;
		}

		if (result < 0) {
			setStateOfBattery(result);
		}
		BMS.storeDataInCollection(BMS.BATTERY_LIFE, result);
		return result;
	}

	@SuppressWarnings("static-access")
	int getSOH() {

		BatteryOut batteryOut = this.getDataFromBattery();
		SensorOut sensorOut = this.getDataFromSensor(batteryOut);
		float T = sensorOut.getTemperature();
		Exception expection = new Exception();
		int result = 0;
		int brokenCell = getBrokenCell();
		if (brokenCell < 5) {
			this.storeDataToMemory(sensorOut);
			getDataFromCPU();
			if (PresentCapacity_me <= 18000 && PresentCapacity_me >= 0) {
				cpuOut = new CPUOut(PresentCapacity_me, InCA_me);
				int SOH = cpuOut.getSOH();

				result = SOH;
				//System.err.println("Battery Life : " + result);
			} else {
				result = expection.memoryDataWrong;
			}
		}

		else {
			result = expection.BATTERYDAMAGE;
			
			return result;
		}

		BMS.storeDataInCollection(BMS.BATTERY_HEALTH, result);
		return result;

	}
	
	
	
	
	public int checkBatteryStatus()
	{
		if(this.getBrokenCell()>=5)
		{
			return Exception.BATTERYDAMAGE;
		}
		return 0;
	}

}
