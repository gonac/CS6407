package soc;


public class ChargeMonitor {

    private BatteryReport report;

    public ChargeMonitor(BatteryReport report) {
        this.report = report;
    }

    public void checkChargeLevel(int batteryLevel) throws ValueOutOfBoundException {
        if (batteryLevel > 100) {
            throw new ValueOutOfBoundException("Battery level larger than 100");
        } else if (batteryLevel == 100) {
            report.setAlert(Alert.OVERCHARGE);
        } else if (batteryLevel < 0) {
            throw new ValueOutOfBoundException("Battery level lower than 0");
        } else if (batteryLevel <= 10) {
            report.setAlert(Alert.OVER_DISCHARGE);
        }
    }

}