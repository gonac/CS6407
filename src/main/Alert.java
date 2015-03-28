package main;

public enum Alert {

    NO_ALERT(0),
    ALERT_BATTERYLOW(1),
    ALERT_OVERCHARGE(2),
    ALERT_HIGHTEMP(3),
    ALERT_DAMAGE(4);
    
    Alert(int type)
    {
    	alert_type=type;
    }
    
    int alert_type;
    
    public int getType()
    {
    	return alert_type;
    }

}
