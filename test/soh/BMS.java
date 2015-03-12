package sn;

import java.util.concurrent.ConcurrentHashMap;

public class BMS {
	public static ConcurrentHashMap<String, Object> centralStorage;
	public BMS() {
		centralStorage = new ConcurrentHashMap<String, Object>();
	}

}
