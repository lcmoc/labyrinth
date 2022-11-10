import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public static void main(String[] args) throws Exception {
        EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.A);

        motorL.setSpeed(360);
        motorR.setSpeed(360);
        motorL.forward();
        motorR.forward();
        Thread.sleep(3000);

        motorL.stop(true);
        motorR.stop(true);
        Thread.sleep(3000);

        motorR.rotate(-800,false);
        Thread.sleep(3000);

        motorL.forward();
        motorR.forward();
        Thread.sleep(3000);

        motorL.stop(true);
        motorR.stop(true);

        motorL.close();
        motorR.close();
}
