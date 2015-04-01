package main;

public enum Alert {

    NO_ALERT(0),
    ALERT_BATTERYLOW(1),
    ALERT_BATTERYEXAUSTED(2),
    ALERT_OVERCHARGE(3),
    ALERT_HIGHTEMP(4),
    ALERT_DAMAGE(5);
    
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
