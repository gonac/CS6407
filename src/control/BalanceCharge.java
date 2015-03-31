package Battery.Control;

//CC = 3
import java.util.logging.Logger;

import Battery.Charge.SOC;

public class BalanceCharge {
	private String cellCharges = new SOC().getCellCharges();
	private String RateOfCharge;
	int cellChargeCycles = 13;

	public BalanceCharge(String cellCharges, String RateOfCharge) {
		this.cellCharges = cellCharges;
		this.RateOfCharge = RateOfCharge;
	}

	public String balanceCharge(String rateOfCharge, String cellCharges) {
		String cellChargeBalanceMatrix = null;	
		if (cellChargeCycles > 0)
		{	
			//BCU.balanceCharge() should be called.
			cellChargeCycles--;
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

	public String reportRateOfCharge(SOC soc, String cellLoadBalanceMatrix) {
		return StaticStrings.reportRateOfChargeToSOC;
	}

	public String reportRateOfCharge(Logger log, String CellLoadBalanceMatrix) {
		return StaticStrings.reportRateOfChargeToLog;
	}
}
