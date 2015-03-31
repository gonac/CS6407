package Battery.Control;

public class thmain {
	public static void main(String[] args) {
		ThermalController therm = new ThermalController(50);
		System.out.println(therm.currTemp);
		System.out.println(therm.tempLimit);
	}
}
