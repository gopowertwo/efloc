package com.example.efficientlocalization;

/**
 * @author Georgi Zlatkov
 */

public class Environment {

	private String detector;
	private float indoors = -2;
	private float outdoors = -2;

	public Environment(String detector) {
		this.detector = detector;
	}

	public String getDetector() {
		return this.detector;
	}

	public float getIndoors() {
		return this.indoors;
	}

	public float getOutdoors() {
		return this.outdoors;
	}

	public void setIndoors(float indoors) {
		this.indoors = indoors;
	}

	public void setOutdoors(float outdoors) {
		this.outdoors = outdoors;
	}

}
