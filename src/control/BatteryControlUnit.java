package control;

import java.util.Random;

import soc.CellBalanceMonitor;
import control.ControlAlert;
import control.ControlReport;

public class BatteryControlUnit {
	
	public Cell[] limpHome(Cell[] cellMatrix) {
		int numWorkingCells = 0;
		for (int i = 0; i < cellMatrix.length; i++)
		{
			if (cellMatrix[i].isWorking() )
			{
				numWorkingCells++;
				//System.out.println(numWorkingCells + " working cells!");
			}
			/*else
			{
				System.out.println("Broken cell found: " + cellMatrix[i]);
			}*/
		}
		//System.out.println("");
		Cell[] newMatrix = new Cell[numWorkingCells];
		for (int i = 0; i < newMatrix.length; i++)
		{
			//System.out.println("New Array: " + i);
			for (int j = i; j < cellMatrix.length; j++)
			{
				//System.out.println("Should we put " + cellMatrix[j] + " in?");
				if (cellMatrix[j].isWorking() )
				{
					newMatrix[i] = cellMatrix[j];
					//System.out.println("Cell" + cellMatrix[j] + "was added to the matrix at position " + i);
					break;
				}
				/*else 
				{
					System.out.println("No, it's broken!");
				}*/
			}
		}
		
		//test the items of newMatrix.
		//This exists solely for testing purposes. Remove before completion 
		/*System.out.println("Testing new matrix, which is at size " + newMatrix.length);
		for (int i = 0; i < newMatrix.length; i++)
		{
			//System.out.println("Position " + i);
			if (newMatrix[i].isWorking() )
			{
				System.out.println("Cell " + newMatrix[i] + " works!");
			}
			else 
			{
				System.out.println("Cell " + i + " doesn't work! That shouldn't happen at this stage!");
			}
		}
		System.out.println("All done!");*/
		return newMatrix;
	}

	public String balanceLoad(Cell[] cellLoadBalanceMatrix, String balance) {

		int loadAsKW = 20; // the load
		int loadPerCell = loadAsKW / (cellLoadBalanceMatrix.length); // should
																		// be 5
		//int testLoadPerCell = 5; // just to be sure

		if (!(balance.equals("UNBALANCED"))) {
			for (int i = 0; i < cellLoadBalanceMatrix.length; i++) {
				//System.out.println("Cell " + i + " has voltage "
				//		+ cellLoadBalanceMatrix[i].getVoltage());
				cellLoadBalanceMatrix[i].downVoltage(loadPerCell);
	//			System.out.println("Cell " + i + " was reduced to"
//						+ cellLoadBalanceMatrix[i].getVoltage());
			}
			return "Balanced load done!";
		} else {
			int unbalancedCell = CellBalanceMonitor.getUnbalancedCell(); // random number between 1 and 5
			int cellToShareLoad = 1;
			for (int i = 0; i < cellLoadBalanceMatrix.length; i++) {
				if (i == cellToShareLoad) {
	//				System.out
		//					.println("Cell " + i + " is receiving extra load");
					cellLoadBalanceMatrix[i].downVoltage(loadPerCell + 2);
				} else if (i == unbalancedCell) {
	//				System.out.println("Cell " + i
	//						+ " is unbalanced and is receiving less load");
					cellLoadBalanceMatrix[i].downVoltage(loadPerCell - 2);
				} else {
	//				System.out.println("Reducing the voltage of cell " + i);
					cellLoadBalanceMatrix[i].downVoltage(loadPerCell);
				}
	//			System.out.println("Cell " + i + " now has a voltage of "
//						+ cellLoadBalanceMatrix[i].getVoltage());
				}
		}
		return "Unbalanced load done!";
	}

	public String balanceCharge(Cell[] cellMatrix, String balance, int rateOfCharge) {

		ControlReport report = new ControlReport();
		ControlAlert alert;
		
		int voltageLimit = 100;
		boolean fullyCharged = false;

		// determine if battery is fully charged

		// charge the batteries in a balanced way
		if (!(balance.equals("ALERT_OVERCHARGE")) ) 
		{
			report.setAlert(ControlAlert.CHARGING);
			if (!(balance.equals("UNBALANCED"))) {
				for (int i = 0; i < cellMatrix.length; i++) {
					if (cellMatrix[i].getVoltage() <= (voltageLimit + rateOfCharge)) {
//						System.out.println("Cell " + i + " has voltage "
//								+ cellMatrix[i].getVoltage());
						cellMatrix[i].upVoltage(rateOfCharge);
//						System.out.println("Cell " + i + " was increased to"
//								+ cellMatrix[i].getVoltage());

					} else {
//						System.out.println("Cell " + i
//								+ " is fully charged. Ignore.");
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
				// NOTE: Assuming that unbalancedCell is balanced LOWER THAN the other cells
				//CellBalanceMonitor.getUnbalancedCell()
				//WE GET THE UNBALANCED CELL BY CALLING THE "getUnbalancedCell()" method from CellBalanceMonitor.java
				Cell unbalancedCell = cellMatrix[cellRef];
				Cell cellToShare = cellMatrix[cellIdToShare];

				for (int i = 0; i < cellMatrix.length; i++) {
					if (cellMatrix[i].getVoltage() <= (voltageLimit + rateOfCharge)) {
//						System.out.println("Cell " + i + ": voltage "
//								+ cellMatrix[i].getVoltage());
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
			report.setAlert(ControlAlert.FULLY_CHARGED);
			return "Battery full";
		}
	}
}