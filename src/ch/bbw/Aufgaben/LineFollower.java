import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.internal.io.SocketOutputStream;
import sensorTest.TouchSensor;

public class Main {
    public static void main(String[] args) {
		EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
		SensorMode touch = touchSensor.getTouchMode();
        float[] touchSample = new float[touch.sampleSize()];
		float[] sampleColor = new float[1];

		// colors: NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN
        // 1 - 10

        motorLeft.setSpeed(slow);
        motorRight.setSpeed(fast);

        do {
            colorSensor.fetchSample(sampleColor, 0);
           if (colorSensor.getColorID() == 7 || colorSensor.getColorID() == 13) {
           				motorL.setSpeed(slow);
           				motorR.setSpeed(fast);

           				motorL.forward();
           				motorR.forward();
           			} else if (colorSensor.getColorID() == 6) {
           				motorL.setSpeed(fast);
           				motorR.setSpeed(slow);

           				motorL.forward();
           				motorR.forward();

           			} else if (colorSensor.getColorID() == 0) {
           				motorL.rotate(385, false);
           				motorR.rotate(-385, false);

           				motorL.forward();
           				motorR.forward();

           			} else if (colorSensor.getColorID() == 2) {
           				motorL.stop(true);
           				motorR.stop(true);

           			} else {
           				motorL.stop(true);
           				motorR.stop(true);

           			}

        } while (!Button.ESCAPE.isDown());

        motorL.close();
        motorR.close();
        colorSensor.close();
    }
}