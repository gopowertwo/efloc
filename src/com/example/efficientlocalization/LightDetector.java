package com.example.efficientlocalization;

/**
 * This class detects the environment based on
 * the captured light intensity, proximity sensor 
 * and current system time.
 * 
 * @author Georgi Zlatkov
 *
 */

import java.util.Calendar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightDetector implements SensorEventListener {

	private final static String TAG = "LightDetector";

	private SensorManager mySensorManager;
	private Sensor myLightSensor;
	private Sensor myProximitySensor;

	final int HIGH_THRESHOLD = 2000;
	final int LOW_THRESHOLD = 50;

	private float light;
	private float proximity;
	private Environment env;

	public LightDetector(SensorManager mySensorManager, Context myContext) {
		env = new Environment("Light");
		this.mySensorManager = mySensorManager;
		this.myLightSensor = this.mySensorManager
				.getDefaultSensor(Sensor.TYPE_LIGHT);
		this.myProximitySensor = this.mySensorManager
				.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	}

	// Listen for changes
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_PROXIMITY:
			this.proximity = event.values[0];
		case Sensor.TYPE_LIGHT:
			this.light = event.values[0];
		}
	}

	public Environment getDecison() {
		if (this.proximity == 0) {
			env.setIndoors(-1);
			env.setOutdoors(-1);
		} else {
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

			if (hour < 18 && hour > 6) {
				if (this.light > HIGH_THRESHOLD) {
					env.setIndoors(0);
					env.setOutdoors(1);
				} else {
					env.setIndoors(1);
					env.setOutdoors(0);
				}
			} else {
				if (this.light < LOW_THRESHOLD) {
					env.setIndoors(0);
					env.setOutdoors((LOW_THRESHOLD - light) / LOW_THRESHOLD);
				} else {
					env.setIndoors((HIGH_THRESHOLD - light) / HIGH_THRESHOLD);
					env.setOutdoors(0);
				}
			}
		}
		return env;
	}

	public void startUpdate() {
		this.mySensorManager.registerListener(this, this.myProximitySensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		this.mySensorManager.registerListener(this, this.myLightSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void stopUpdate() {
		mySensorManager.unregisterListener(this, this.myProximitySensor);
		mySensorManager.unregisterListener(this, this.myLightSensor);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
