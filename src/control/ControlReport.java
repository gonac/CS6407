package control;

import java.util.ArrayList;

import control.ControlAlert;
import control.ControlReportObservable;


public class ControlReport {

    private ArrayList<ControlReportObservable> observers = new ArrayList<ControlReportObservable>();

    private ControlAlert alert = null;


    public ControlAlert getAlert() {
        return alert;
    }

    public void setAlert(ControlAlert alert) {
        this.alert = alert;
        notifyAllObservers();
    }

    public void addObserver(ControlReportObservable observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (ControlReportObservable observer : observers) {
            observer.updateControl();
        }
    }
}
