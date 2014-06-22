package com.example.efficientlocalization;

/**
 * 
 * 
 * 
 * @author Georgi Zlatkov
 * 
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerDetector implements SensorEventListener {
	
	private final static String TAG = "efiLoc";

	private static final int MIN_FORCE = 3;
	private static final int MIN_CHANGES = 5;

	private SensorManager mySensorManager;
	private Sensor myAccelerometer;

	private boolean myInitialized;
	private float lastX = 0;
	private float lastY = 0;
	private float lastZ = 0;
	private int counter = 0;
	private long lastTime = 0;
	private long time = 0;
	
	private boolean isMoving = false;

	public AccelerometerDetector(SensorManager mySensorManager,
			Context myContext) {
		this.mySensorManager = mySensorManager;

		myAccelerometer = this.mySensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		myInitialized = false;
		lastTime = System.currentTimeMillis();
	}
	
	// Simple motion detector! Could be improved!
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!myInitialized) {
			lastX = x;
			lastY = y;
			lastZ = z;
			myInitialized = true;
		} else {
			float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);
			if(totalMovement > MIN_FORCE) {
				counter++;
			}
			time = System.currentTimeMillis();
			if(time -lastTime > 30000) {
				isMoving = false;
				lastTime = time;
			}
			if(counter >= MIN_CHANGES) {
				Log.v(TAG, "Accelerometer moving");
				isMoving = true;
				counter = 0;
			}
			lastX = x;
			lastY = y;
			lastZ = z;
		}
	}
	

	public void startUpdate() {
		mySensorManager.registerListener(this,
				this.myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void stopUpdate() {
		Log.v(TAG, "Accelerometer is stopped!");
		mySensorManager.unregisterListener(this,
				this.myAccelerometer);
	}

	
	public boolean getState() {
		return this.isMoving;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
