package soc;


public class CellBalanceMonitor {

    private static final int CELL_DIFF = 5;
    private BatteryReport report;

    public CellBalanceMonitor(BatteryReport report) {
        this.report = report;
    }

    public void checkCellBalance(int[] cellSOC) {
        int chargeSum = 0;
        int avg = 0;

        for (int t : cellSOC) {
            chargeSum += t;
        }
        avg = chargeSum / cellSOC.length;
        for (int soc : cellSOC) {
            if (Math.abs(avg - soc) > CELL_DIFF) {
                report.setAlert(Alert.UNBALANCED);
            }
        }
    }
}
