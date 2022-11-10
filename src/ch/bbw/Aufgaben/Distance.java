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
        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S3);
        ultrasonicSensor.setCurrentMode("Distance");
        SensorMode touch = touchSensor.getTouchMode();
        float[] touchSample = new float[touch.sampleSize()];
        float[] distanceSample = new float[touch.sampleSize()];

        do {
            touch.fetchSample(touchSample, 0);
        } while (sample[0] == 0);

        motorLeft.setSpeed(360);
        motorRight.setSpeed(360);

        motorLeft.forward();
        motorRight.forward();

        Thread.sleep(1000);
        motorLeft.stop(true);
        motorRight.stop(true);
        Thread.sleep(1000);

        do {
            ultrasonicSensor.fetchSample(distanceSample, 0);
            int distanze = (int) (100*distanceSample[0]);
            if(distanze > 10) {
                motorL.setSpeed(360);
                motorR.setSpeed(360);

                motorL.forward();
                motorR.forward();
            } else {
                motorL.stop(true);
                motorR.stop(true);
            }

        } while (!Button.ESCAPE.isDown());

        motorL.close();
        motorR.close();
        ultrasonicSensor.close();
    }
}