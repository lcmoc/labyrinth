package ch.bbw.components;

import java.util.function.Consumer;

import ch.bbw.util.Action;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class TouchDetector extends Thread {
	private boolean detect;
	private boolean running;
	private Action action;
	private EV3TouchSensor touchSensor;
	
	
	public TouchDetector(Port port, Action action) {
		this.detect = true;
		this.running = true;
		this.action = action;
		this.touchSensor = new EV3TouchSensor(port);
	}

	@Override
	public void run() {
		SensorMode touch = this.touchSensor.getTouchMode();
		float[] touchValues = new float[touch.sampleSize()];
		while (running) {
			touch.fetchSample(touchValues, 0);
			if (touchValues[0] != 0) {
				action.perform();
			}
		}
	}
	
	public void disableDetection() {
		this.detect = false;
	}
	
	public void enableDetection() {
		this.detect = true;
	}
	
	public void toggleDetection() {
		this.detect = !this.detect;
	}
	
	
}
