package ch.bbw.components;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class MotorControl {
	public static enum DriveMode {
		FORWARD,
		BACKWARD,
		TURN_LEFT,
		TURN_RIGHT,
		ONLY_LEFT,
		ONLY_RIGHT
	}
	
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private float speed;
	private boolean running;
	private DriveMode mode;
	
	public MotorControl(DriveMode mode, float speed) {
		this.mode = mode;
		this.speed = speed;
		this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	}
	
	public void start() {
		this.running = true;
		updateMotors();
	}
	
	public void rotate(int amount) {
		int left = amount;
		int right = amount;
		switch (mode) {
			case BACKWARD:
				right = -amount;
			case TURN_LEFT:
				left = -amount;
				break;
			case TURN_RIGHT:
				right = -amount;
				break;
			case ONLY_LEFT:
				right = 0;
				break;
			case ONLY_RIGHT:
				left = 0;
				break;
			default:
				throw new RuntimeException("No Proper mode initialized");
		}
		leftMotor.rotate(left);
		rightMotor.rotate(right);
	}
	
	public void stop() {
		this.running = false;
		this.leftMotor.stop();
		this.rightMotor.stop();
	}
	
	public void changeSpeed(float speed) {
		this.speed = speed;
		updateSpeed();
	}
	
	public void changeMode(DriveMode mode) {
		this.mode = mode;
		updateMotors();
	}
	
	private void updateSpeed() {
		leftMotor.setSpeed(speed);
		updateMotors();
	}
	
	
	private void updateMotors() {
		if (running) {
			switch (mode) {
				case FORWARD:
					leftMotor.forward();
					rightMotor.forward();
					return;
				case BACKWARD:
					leftMotor.backward();
					rightMotor.backward();
					return;
				case TURN_LEFT:
					leftMotor.backward();
					rightMotor.forward();
					return;
				case TURN_RIGHT:
					leftMotor.forward();
					rightMotor.backward();
					return;
				case ONLY_LEFT:
					leftMotor.forward();
					rightMotor.stop();
					return;
				case ONLY_RIGHT:
					leftMotor.stop();
					rightMotor.forward();
					return;
				default:
					throw new RuntimeException("No Proper mode initialized");
			}
		}
	}
}
