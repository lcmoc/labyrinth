package colordetector;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class TestColorDefiner {

	public static void main(String[] args) throws InterruptedException {
		
		final ColorDefiner cd = new ColorDefiner(new EV3ColorSensor(SensorPort.S4));
		//cd.initColor();   //dies muss man einmal machen
		//cd.writeFile();	//dies muss man einmal machen
		boolean lesen = cd.readFile();	
			
		 while(Button.ESCAPE.isUp()) {
			//LCD.drawString("File : " + lesen +" ", 0, 1);
			LCD.drawString(""+cd.getColor() +"      ", 0, 3);
			//cd.displayAllValues(cd);
			Thread.sleep(100);
		}
	}

}
