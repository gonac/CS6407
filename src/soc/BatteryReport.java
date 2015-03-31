package soc;

import java.util.ArrayList;


public class BatteryReport {

    private ArrayList<ReportObservable> observers = new ArrayList<ReportObservable>();

    private Alert alert = null;


    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
        notifyAllObservers();
    }

    public void addObserver(ReportObservable observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (ReportObservable observer : observers) {
            observer.update();
        }
    }
}
