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

	private static final int INDOORS = 1;
	private static final int OUTDOORS = 0;
	
	private Environment env;
	private Context context;
	private PowerUtil power = new PowerUtil();

	public ChargingDetector(Context myContext) {
		this.context = myContext;
		env = new Environment("Charge");
	}

	public Environment getDecison() {

		if (power.isCharging(context)) {
			env.setIndoors(INDOORS);
			env.setOutdoors(OUTDOORS);
		} else {
			env.setIndoors(-1);
			env.setOutdoors(-1);
		}
		return env;
	}

	// Check for both AC and USB charging
	// Could be improved to check if USB is outdoors(laptop/remote batteries)
	class PowerUtil {
		public boolean isCharging(Context context) {
			Intent intent = context.registerReceiver(null, new IntentFilter(
					Intent.ACTION_BATTERY_CHANGED));
			int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			return plugged == BatteryManager.BATTERY_PLUGGED_AC
					|| plugged == BatteryManager.BATTERY_PLUGGED_USB;
		}
	}

}
