import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class EV3LF {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
        SensorMode mode = colorSensor.getRedMode();

        Sound.twoBeeps();
        LCD.drawString("white", 0, 0);
        Button.ESCAPE.waitForPressAndRelease();
        float[] sampleWhite = new float[1];
        mode.fetchSample(sampleWhite, 0);
        LCD.drawInt(new Float(sampleWhite[0] * 100).intValue(), 2, 2);

        Delay.msDelay(1000);
        Sound.twoBeeps();
        LCD.drawString("Black", 4, 4);
        Button.ESCAPE.waitForPressAndRelease();
        float[] sampleBlack = new float[1];
        mode.fetchSample(sampleBlack, 0);
        LCD.drawInt(new Float(sampleBlack[0] * 100).intValue(), 6, 6);
        Delay.msDelay(3000);

        int defaultPower = 20;
        int multiplyingFactor = 50;

        NXTMotor largeMotorB = new NXTMotor(MotorPort.B);
        NXTMotor largeMotorC = new NXTMotor(MotorPort.C);
        float[] color = new float[1];

        while (!Button.ESCAPE.isPressed()) {

            float avgLight = (sampleBlack[0] + sampleWhite[0]) / 2;
            mode.fetchSample(color, 0);
            float cSpeed = defaultPower + multiplyingFactor * (avgLight - color[0])
                    / (sampleWhite[0] - sampleBlack[0]);
            largeMotorC.setPower(new Float(cSpeed).intValue());
            largeMotorC.forward();

            float bSpeed = defaultPower - multiplyingFactor * (avgLight - color[0])
                    / (sampleWhite[0] - sampleBlack[0]);
            largeMotorB.setPower(new Float(bSpeed).intValue());
            largeMotorB.forward();
        }

    }
}