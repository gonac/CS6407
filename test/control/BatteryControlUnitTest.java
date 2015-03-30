package Battery.Control.JUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import Battery.Control.BatteryControlUnit;
import Battery.Control.Cell;

public class BatteryControlUnitTest {
	BatteryControlUnit bcu = new BatteryControlUnit();
		
	@Test
	public void LimpHomeTest()
	{
		Cell[] cellMatrix = new Cell[5];
		Cell zero = new Cell(20,15,15,0, true);
		Cell one = new Cell(22,15,15,1, true);
		Cell two = new Cell(21, 15,15,2, true);
		Cell three = new Cell (23,15,15,3, true);
		Cell four = new Cell(24,15,15,4, true);
		
		cellMatrix[0] = zero;
		cellMatrix[1] = one;
		cellMatrix[2] = two;
		cellMatrix[3] = three;
		cellMatrix[4] = four;
		
		Cell[] noZero = new Cell[] {one, two, three, four};
		
		assertTrue(!((bcu.ideaLimpHome(cellMatrix).length)==(noZero.length)));
		assertArrayEquals(bcu.ideaLimpHome(cellMatrix).equals(cellMatrix));
		cellMatrix[0].cellBroke();
		assertTrue(!(bcu.ideaLimpHome(cellMatrix).equals(cellMatrix)));
	}
	
	private void assertArrayEquals(boolean equals) {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void balanceLoadTest()
	{
		Cell[] cellMatrix = new Cell[5];
		cellMatrix[0] = new Cell(20,15,15,0, true);
		cellMatrix[1] = new Cell (22,15,15,1, true);
		cellMatrix[2] = new Cell(21, 15,15,2, true);
		cellMatrix[3] = new Cell (23,15,15,3, true);
		cellMatrix[4] = new Cell(24,15,15,4, true);
		
		
		assertEquals(bcu.balanceLoad(cellMatrix, "hi"),"Balanced load done!");
		assertEquals(bcu.balanceLoad(cellMatrix, "UNBALANCED"), "Unbalanced load done!");
	}
	
	@Test
	public void balanceChargeTest()
	{
		Cell[] cellMatrix = new Cell[5];
		cellMatrix[0] = new Cell(20,15,15,0, true);
		cellMatrix[1] = new Cell (22,15,15,1, true);
		cellMatrix[2] = new Cell(21, 15,15,2, true);
		cellMatrix[3] = new Cell (23,15,15,3, true);
		cellMatrix[4] = new Cell(24,15,15,4, true);
		
		assertEquals(bcu.balanceCharge(cellMatrix, "hi", 5), "Balanced charging done!");
		assertEquals(bcu.balanceCharge(cellMatrix, "UNBALANCED", 5), "Unbalanced charging done!");
	}

}
