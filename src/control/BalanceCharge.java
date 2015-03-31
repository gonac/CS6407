package control;

//CC = 3
import java.util.logging.Logger;

import main.BMS;

public class BalanceCharge {
	private Cell[] cellArray;
	private int RateOfCharge;
	int cellChargeCycles = 13;
	int cellChargesUsed = 0;
	String status="";
	
	public BatteryControlUnit bcu;

	public BalanceCharge(Cell[] cellArray, int RateOfCharge, BatteryControlUnit bcu, String status) {
		this.cellArray = cellArray;
		this.RateOfCharge = RateOfCharge;
		this.bcu = bcu;
		this.status = status;
	}

	public String balanceCharge(int rateOfCharge, Cell[] cellCharges) {
		String cellChargeBalanceMatrix = null;
		if (cellChargeCycles > 0)
		{	
			System.out.println("Before load: Cell array's initial values were "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL1) + ", " 
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL2) + ", "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL3) + ", "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL4) + ", "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL5) + "!"
			);
			//BCU.balanceCharge() should be called.
			bcu.balanceCharge(cellArray, status, rateOfCharge);
			
			//update the cell charge values in the main memory
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL1, (float)(cellArray[0].getVoltage()));
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL2, (float)(cellArray[1].getVoltage()));
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL3, (float)(cellArray[2].getVoltage()));
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL4, (float)(cellArray[3].getVoltage()));
			BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL5, (float)(cellArray[4].getVoltage()));
			System.out.println("After charge: Cell arrays now have a charge of "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL1) + ", "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL2) + ", "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL3) + ", "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL4) + ", "
					+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL5) + "!"
			);
																				
			cellChargeCycles--;
			cellChargesUsed++;
		}
		else
		{
			System.out.println("Cannot charge the battery any more; we have used up all our cycles.");
		}
		return cellChargeBalanceMatrix;
	}

	public String disconnectCharger() {
		return StaticStrings.chargerDisconnect;
	}
/*Remove this, perhaps, since reporting the rate of charge is someone else's job?
 * What we should be doing is sending the charge levels/rate of charge to soc.
	public String reportRateOfCharge(SOC soc, String cellLoadBalanceMatrix) {
		return StaticStrings.reportRateOfChargeToSOC;
	}

	public String reportRateOfCharge(Logger log, String CellLoadBalanceMatrix) {
		return StaticStrings.reportRateOfChargeToLog;
	} */
}
