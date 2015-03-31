package control;

//cc5
import java.util.logging.Level;
import java.util.logging.Logger;

import Battery.Charge.SOC;

public class BalanceLoad {
	// currentLoad = CAN.getCurrentLoad();
	private String currentLoad;
	private String cellCharges = new SOC().getCellCharges();

	// Create a Logger for HandleCriticalCharge()
	private final static Logger LOGGER = Logger.getLogger("");

	public BalanceLoad(String currentLoad, String cellCharges) {
		this.currentLoad = currentLoad;
		this.cellCharges = cellCharges;
		//BCU.balanceLoad() should be called.
	}

	public void reportCellLoads(Logger log, String cellLoadBalanceMatrix) {
		System.out.println(StaticStrings.reportCellToLog);
	}

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
	}

	public String HandleCellFailure() {
		return StaticStrings.cellFailure;
	}

	public String getCurrentLoad() {
		return currentLoad;
	}

	public void setCurrentLoad(String currentLoad) {
		this.currentLoad = currentLoad;
	}

	public String getCellCharges() {
		return cellCharges;
	}

	public void setCellCharges(String cellCharges) {
		this.cellCharges = cellCharges;
	}
}
