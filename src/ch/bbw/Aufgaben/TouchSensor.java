import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.internal.io.SocketOutputStream;
import sensorTest.TouchSensor;

public class Main {
    public static void main(String[] args) {
        EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
		SensorMode touch = touchSensor.getTouchMode();
		float[] sample = new float[touch.sampleSize()];

        do {
            touch.fetchSample(sample, 0);
        } while (sample[0] == 0);

        motorLeft.setSpeed(360);
        motorRight.setSpeed(360);

        motorLeft.forward();
        motorRight.forward();

        Thread.sleep(1000);
        motorLeft.stop(true);
        motorRight.stop(true);
        Thread.sleep(1000);

        motorL.close();
        motorR.close();
    }
}