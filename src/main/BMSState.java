package main;

public enum BMSState {
    IDLE(0),
    CHARGING(1),
    ONMOVE(2),
    DAMAGED(4);

    private final int stateValue;

    BMSState(int value) {
        stateValue = value;
    }
    public int getStateValue()
    {
    	return stateValue;
    }
}
