package com.example.efficientlocalization;

/**
 * This class detects the environment based on
 * if the device is charging or not.
 * 
 * @author Georgi ZLatkov
 *
 */

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class ChargingDetector {

	private static final int PLUGGED_AC = 1;
	private static final int PLUGGED_USB = 0;
	private static final int BATTERY = -1;

	private Environment env;
	private Context context;

	public ChargingDetector(Context myContext) {
		this.context = myContext;
		env = new Environment("Charge");
	}

	public Environment getDecison() {

		int result = isCharging(context);
		
		if (result == PLUGGED_AC) {
			env.setIndoors(1);
			env.setOutdoors(-1);
		} else if (result == PLUGGED_USB) {
			env.setIndoors(0);
			env.setOutdoors(0);
		} else {
			env.setIndoors(-1);
			env.setOutdoors(-1);
		}
		return env;
	}
	
	public int isCharging(Context context) {
		Intent intent = context.registerReceiver(null, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

		if (plugged == BatteryManager.BATTERY_PLUGGED_AC) {
			return PLUGGED_AC;
		} else if (plugged == BatteryManager.BATTERY_PLUGGED_USB) {
			return PLUGGED_USB;
		} else {
			return BATTERY;
		}

	}

	// Check for both AC and USB charging
	class PowerUtil {
		
	}

}
