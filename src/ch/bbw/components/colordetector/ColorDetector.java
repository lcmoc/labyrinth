package colordetector;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

/**
 * For testing the HiTechnic color sensor 
 * @author BB
 */


//Anmerkung B.Marti - Dies funktioniert leider nicht so gut. Es erkennt z.T Blau = Grün oder ähnlich
public class ColorDetector {

	final static int INTERVAL = 200; // milliseconds
	
	public static void main(String [] args) throws Exception {
		
		EV3ColorSensor cmps = new EV3ColorSensor(SensorPort.S4);
		String color = "Color";
		String r = "R";
		String g = "G";
		String b = "B";
		
		String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Magenta", "Orange",
				             "White", "Black", "Pink", "Gray", "Light gray", "Dark Gray", "Cyan"			
		};
		
		//Solange der Escape Button nicht gedrückt ist wiederhole...
		while(!Button.ESCAPE.isDown()) {
			LCD.clear();
			LCD.drawString(cmps.getName(), 0, 0);
			
			LCD.drawString(""+cmps.getColorID(), 0, 1);
			
			LCD.drawString(color, 0, 3);

			LCD.refresh();
			Thread.sleep(INTERVAL);
		}
	}
}