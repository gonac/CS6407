package Battery.Control;

import java.util.Random;

public class BatteryControlUnit {

	public Cell[] fakeLimpHome(Cell brokenCell, Cell[] cellMatrix) {
		Cell[] newCell = new Cell[cellMatrix.length];
		for (int i = 0; i < cellMatrix.length; i++) {
			if (cellMatrix[i].getId() != brokenCell.getId()) {
				newCell[i] = cellMatrix[i];
			}
		}
		return newCell;
	}
	
	public Cell[] ideaLimpHome(Cell[] cellMatrix) {
		int numWorkingCells = 0;
		for (int i = 0; i < cellMatrix.length; i++)
		{
			if (cellMatrix[i].isWorking() )
			{
				numWorkingCells++;
				System.out.println(numWorkingCells + " working cells!");
			}
			else
			{
				System.out.println("Broken cell found: " + cellMatrix[i]);
			}
		}
		System.out.println("");
		Cell[] newMatrix = new Cell[numWorkingCells];
		for (int i = 0; i < newMatrix.length; i++)
		{
			System.out.println("New Array: " + i);
			for (int j = i; j < cellMatrix.length; j++)
			{
				System.out.println("Should we put " + cellMatrix[j] + " in?");
				if (cellMatrix[j].isWorking() )
				{
					newMatrix[i] = cellMatrix[j];
					System.out.println("Cell" + cellMatrix[j] + "was added to the matrix at position " + i);
					break;
				}
				else 
				{
					System.out.println("No, it's broken!");
				}
			}
		}
		
		//test the items of newMatrix
		System.out.println("Testing new matrix, which is at size " + newMatrix.length);
		for (int i = 0; i < newMatrix.length; i++)
		{
			System.out.println("Position " + i);
			if (newMatrix[i].isWorking() )
			{
				System.out.println("Cell " + newMatrix[i] + " works!");
			}
			else 
			{
				System.out.println("Cell " + i + " doesn't work! That shouldn't happen at this stage!");
			}
		}
		System.out.println("All done!");
		return newMatrix;
	}
	
//secondary approach to limpHome().
//	public Cell[] limpHome(Cell brokenCell, Cell[] cellMatrix)
//	{
//		Cell[] goodHalf = new Cell[(int)(Math.ceil(cellMatrix.length/2))];
//		if (brokenCell.getId() > cellMatrix.length)
//		{	//get first half
//			for (int i = 0; i < cellMatrix.length; i++)
//			{
//				goodHalf[i] = cellMatrix[i];
//			}
//			
//		}
//		else
//		{	//get second half
//			for (int i = (int)(Math.ceil(cellMatrix.length / 2) ); i < cellMatrix.length; i++)
//			{
//				goodHalf[i] = cellMatrix[i];
//			}
//		}
//		return goodHalf;
//	}

	public String balanceLoad(Cell[] cellLoadBalanceMatrix, String balance) {

		int loadAsKW = 20; // the load
		int loadPerCell = loadAsKW / (cellLoadBalanceMatrix.length); // should
																		// be 5
		int testLoadPerCell = 5; // just to be sure

		if (!(balance.equals("UNBALANCED"))) {
			for (int i = 0; i < cellLoadBalanceMatrix.length; i++) {
				System.out.println("Cell " + i + " has voltage "
						+ cellLoadBalanceMatrix[i].getVoltage());
				cellLoadBalanceMatrix[i].downVoltage(loadPerCell);
				System.out.println("Cell " + i + " was reduced to"
						+ cellLoadBalanceMatrix[i].getVoltage());
			}
			return "Balanced load done!";
		} else {
			int unbalancedCell = 3; // random number between 1 and 5
			int cellToShareLoad = 1;
			for (int i = 0; i < cellLoadBalanceMatrix.length; i++) {
				if (i == cellToShareLoad) {
					System.out
							.println("Cell " + i + " is receiving extra load");
					cellLoadBalanceMatrix[i].downVoltage(loadPerCell + 2);
				} else if (i == unbalancedCell) {
					System.out.println("Cell " + i
							+ " is broken and is receiving less load");
					cellLoadBalanceMatrix[i].downVoltage(loadPerCell - 2);
				} else {
					System.out.println("Reducing the voltage of cell " + i);
					cellLoadBalanceMatrix[i].downVoltage(loadPerCell);
					System.out.println("Cell " + i + " now has "
							+ cellLoadBalanceMatrix[i].getVoltage());
				}
			}
			return "Unbalanced load done!";
		}
	}

	public String balanceCharge(Cell[] cellMatrix, String balance, int rateOfCharge) {
		int voltageLimit = 100;
		boolean fullyCharged = false;

		// determine if battery is fully charged

		// charge the batteries in a balanced way
		if (!fullyCharged) {
			if (!(balance.equals("UNBALANCED"))) {
				for (int i = 0; i < cellMatrix.length; i++) {
					if (cellMatrix[i].getVoltage() <= (voltageLimit + rateOfCharge)) {
						System.out.println("Cell " + i + " has voltage "
								+ cellMatrix[i].getVoltage());
						cellMatrix[i].upVoltage(rateOfCharge);
						System.out.println("Cell " + i + " was increased to"
								+ cellMatrix[i].getVoltage());

					} else {
						System.out.println("Cell " + i
								+ " is fully charged. Ignore.");
					}
				} return "Balanced charging done!"; 
			} else // battery is unbalanced
			{
				Random rand = new Random();
				int cellRef = rand.nextInt(5);
				int cellIdToShare = rand.nextInt(5);
				while (cellIdToShare == cellRef) {
					cellIdToShare = rand.nextInt(5);
				}
				// for testing purposes, find the unbalanced cell
				// NOTE: Assuming that unbalancedCell is balanced LOWER THAN the
				// other cells
				Cell unbalancedCell = cellMatrix[cellRef];
				Cell cellToShare = cellMatrix[cellIdToShare];

				for (int i = 0; i < cellMatrix.length; i++) {
					if (cellMatrix[i].getVoltage() <= (voltageLimit + rateOfCharge)) {
						System.out.println("Cell " + i + ": voltage "
								+ cellMatrix[i].getVoltage());
						if (cellMatrix[i] == unbalancedCell) {
							cellMatrix[i].upVoltage(rateOfCharge + 2);
						} else if (cellMatrix[i] == cellToShare) {
							cellMatrix[i].upVoltage(rateOfCharge - 2);
						} else {
							cellMatrix[i].upVoltage(rateOfCharge);
						}
					}
				}
			return "Unbalanced charging done!"; 
			}
		} else {
			System.out
					.println("Battery is already fully charged. Disconnect charger!");
			return "Battery full";
		}
	}
}

/*
 * THIS SHOULD NOT RUN IN ANY LOOP FORM STEPS: -We get the current load
 * from the BMS. Before integration, we represent this as the loadAsKW
 * var below. -We look at the voltage of each cell -If all cells are
 * even, divide loadAsKW into 5 and decrement voltage level from each.
 * 
 * **NORMAL RUN [If voltage all balanced]** - divide load into 5
 * (cellMatrix.length)
 * 
 * ***State of Imbalance!*** -We hear from Charge Team if one cell's
 * voltage < 5+another cell's voltage. Mark this cell. - now fix their
 * order -desynchedCell'sCharge -= 2; nextGoodCell'sCharge += 2;
 * **Resume as normal**
 */