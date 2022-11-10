package ch.bbw;

import ch.bbw.components.MotorControl;
import ch.bbw.components.MotorControl.DriveMode;
import ch.bbw.util.Action;
import ch.bbw.components.TouchDetector;
import lejos.hardware.port.SensorPort;

public class MainThread {
	public static void main(String[] args) {
		/* final MotorControl mc = new MotorControl(DriveMode.FORWARD, 1000);
		private boolean driving = false;

		@Override
		public void perform() {
			if (driving) {
				mc.stop();
				driving = false;
			} else {
				mc.start();
				driving = true;
			}
		}
		 */

		EV3ColorSensor colorSensor = new EV3LargeRegulatedMotor(MotorPort.A)

		private ColorDefiner colorDefiner = new ColorDefiner(Port 4);
		
	}
}
