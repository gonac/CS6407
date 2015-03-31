package soc;


public class CellBalanceMonitor {

    private static final int CELL_DIFF = 5;
    private BatteryReport report;
    private int unbalancedCellPos;

    public CellBalanceMonitor(BatteryReport report) {
        this.report = report;
    }

    public void checkCellBalance(int[] cellSOC) {
        int chargeSum = 0;
        int avg;

        for (int t : cellSOC) {
            chargeSum += t;
        }
        avg = chargeSum / cellSOC.length;
        for (int i = 0; i < cellSOC.length; i++) {
            if (Math.abs(avg - cellSOC[i]) > CELL_DIFF) {
                // Because all of the cell needs to be fixed,
                // so just give the position of the first cell we found
                unbalancedCellPos = i;
                report.setAlert(Alert.UNBALANCED);
                break;
            }
        }
    }

    public int getUnbalancedCell() {
        return unbalancedCellPos;
    }
}
