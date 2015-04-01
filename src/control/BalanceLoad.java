package control;

//cc5
import java.util.logging.Level;
import java.util.logging.Logger;

import main.BMS;

public class BalanceLoad {
	// currentLoad = CAN.getCurrentLoad();
	private int currentLoad;
	private Cell[] cellArray;
	private BatteryControlUnit bcu;
	private String status="";

	// Create a Logger for HandleCriticalCharge()
	private final static Logger LOGGER = Logger.getLogger("");

	public BalanceLoad(Cell[] cellCharges, int currentLoad, BatteryControlUnit bcu, String status) {
		this.currentLoad = currentLoad;
		this.cellArray = cellCharges;
		this.bcu = bcu;
		this.status=status;
	}
	
	public void balanceLoad()
	{//print stuff is just for testing purposes. Remove as you wish.
//		System.out.println("Before load: Cell array's initial values were " 
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL1) + ", " 
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL2) + ", "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL3) + ", "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL4) + ", "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL5) + "!"
//		);
		bcu.balanceLoad(cellArray, status);
		
		//update the cell charge values in the main memory
		BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL1, (float)(cellArray[0].getCharge()));
		BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL2, (float)(cellArray[1].getCharge()));
		BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL3, (float)(cellArray[2].getCharge()));
		BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL4, (float)(cellArray[3].getCharge()));
		BMS.storeDataInCollection(BMS.CHARGE_AMOUNT_CELL5, (float)(cellArray[4].getCharge()));
		
//		System.out.println("After load: Cell arrays now have a charge of "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL1) + ", "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL2) + ", "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL3) + ", "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL4) + ", "
//				+ BMS.getDataInCollection(BMS.CHARGE_AMOUNT_CELL5) + "!"
//		);
	}

	public void reportCellLoads(Logger log, String cellLoadBalanceMatrix) {
		//System.out.println(StaticStrings.reportCellToLog);
	}
/* We should reporting the load on each cell to the soc
 * We should not be making a general alert
	public String reportCellLoads(SOC soc, String cellLoadBalanceMatrix) {
		return StaticStrings.reportCellToSOC;
	}

	public String HandleLowCharge() {
		return StaticStrings.chargeLimit;
	} 

	public String HandleCriticalCharge() {
		// CAN.alert(CriticalChargeLimit);
		// SOC.alert(CriticalChargeLimit);
		LOGGER.log(Level.SEVERE, StaticStrings.CriticalChargeLimit);
		return StaticStrings.CriticalChargeLimit;
	} */

	public String HandleCellFailure() {
		return StaticStrings.cellFailure;
	}

	public int getCurrentLoad() {
		return currentLoad;
	}

	public void setCurrentLoad(int currentLoad) {
		this.currentLoad = currentLoad;
	}

	public Cell[] getCellCharges() {
		return cellArray;
	}

	public void setCellCharges(Cell[] cellCharges) {
		this.cellArray = cellCharges;
	}
}
