package colordetector;


import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.Button;

/* 2018, B.Marti
 * Farbsensor ca. 5mm bis 10mm ab Boden -- PORT IST AUF 4 eingestellt!!!
 * Mit dem Button "Down" lassen sich nach Instruktion rot, geld, blau, grün , schwarz und weiss einlesen. Diese werden in ein File gespeichert
 * Die Werte werden solange eingelesen wie der Button gedrückt wird --> upper und lower Toleranzbereich
 * -- das heisst, eine Sekunde lange auf einer Farbe gedrückt halten und ev. ein wenig bewegen, dann loslassen
 * 
 * Verwendung:
 * Das erste Mal um die Farben ins File zu schreiben:
 * 1: Instanz ColorDefiner erstellen
 * 2. initColor() aufrufen
 * 3: writeFile() aufrufen
 * anschliessend in der normalen Verwendung
 * 4: readFile() einmalig aufrufen
 * dann mit getColor() die aktuelle Farbe lesen
 * 
 */
public class ColorDefiner {
		
		//toleranzwert nach unten und oben. 1.6 scheint ein guter Wert zu sein.
		private static final double TOLPR = 1.6;

		private RBBColor green = new RBBColor("green");
		private RBBColor yellow = new RBBColor("yellow");
		private RBBColor red = new RBBColor("red");
		private RBBColor white = new RBBColor("white");
		private RBBColor black = new RBBColor("black");
		private RBBColor blue = new RBBColor("blue");
		private float[] sampleColor = new float[3];
		private EV3ColorSensor colorSensor;

		class RBBColor {
			private String identifier;
			private double redLow, redHigh, greenLow, greenHigh, blueLow, blueHigh;
						
			public 	RBBColor(String identifier){
				this.identifier = identifier;
			}	
			public void setColorRange(double redLow, double redHigh, double greenLow, double greenHigh, double blueLow, double blueHigh){
				this.redLow=redLow;
				this.redHigh=redHigh;
				this.greenLow=greenLow;
				this.greenHigh=greenHigh;
				this.blueLow=blueLow;
				this.blueHigh=blueHigh;
			}
			
			public String getidentifier() {
				return identifier;
			}
			
			public double getRedLow() {
				return redLow;
			}
			
			public void setRedLow(double redLow) {
				this.redLow = redLow;
			}
			public double getRedHigh() {
				return redHigh;
			}
			public void setRedHigh(double redHigh) {
				this.redHigh = redHigh;
			}
			public double getGreenLow() {
				return greenLow;
			}
			public void setGreenLow(double greenLow) {
				this.greenLow = greenLow;
			}
			public double getGreenHigh() {
				return greenHigh;
			}
			public void setGreenHigh(double greenHigh) {
				this.greenHigh = greenHigh;
			}
			public double getBlueLow() {
				return blueLow;
			}
			public void setBlueLow(double blueLow) {
				this.blueLow = blueLow;
			}
			public double getBlueHigh() {
				return blueHigh;
			}
			public void setBlueHigh(double blueHigh) {
				this.blueHigh = blueHigh;
			}
		}	
			
		public RBBColor getGreen() {
			return green;
		}
		public void setGreen(RBBColor green) {
			this.green = green;
		}
		public RBBColor getYellow() {
			return yellow;
		}
		public void setYellow(RBBColor yellow) {
			this.yellow = yellow;
		}
		public RBBColor getRed() {
			return red;
		}
		public void setRed(RBBColor red) {
			this.red = red;
		}
		public RBBColor getWhite() {
			return white;
		}
		public void setWhite(RBBColor white) {
			this.white = white;
		}
		public RBBColor getBlack() {
			return black;
		}
		public void setBlack(RBBColor black) {
			this.black = black;
		}
		public RBBColor getBlue() {
			return blue;
		}
		public void setBlue(RBBColor blue) {
			this.blue = blue;
		}
		
		public  ColorDefiner(EV3ColorSensor colorSensor ){
			this.colorSensor = colorSensor;
			colorSensor.setCurrentMode("RGB");
			colorSensor.setFloodlight(false);
		}
		
		public void initColor(){
			
			//----------------------------------
			LCD.clear();
			while(Button.DOWN.isUp()) {	
				colorSensor.fetchSample(sampleColor, 0);
				LCD.drawString("Red, press down" + "    ", 0, 1);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
			}
			
			colorSensor.fetchSample(sampleColor, 0);
			red.setColorRange(sampleColor[0],sampleColor[0],sampleColor[1],sampleColor[1],sampleColor[2],sampleColor[2]);

			while(Button.DOWN.isDown()) {
				colorSensor.fetchSample(sampleColor, 0);
				if(sampleColor[0]< red.getRedLow()) red.setRedLow(sampleColor[0]/TOLPR);
				if(sampleColor[0]> red.getRedHigh()) red.setRedHigh(sampleColor[0]*TOLPR);
				if(sampleColor[1]< red.getGreenLow()) red.setGreenLow(sampleColor[1]/TOLPR);
				if(sampleColor[1]> red.getGreenHigh()) red.setGreenHigh(sampleColor[1]*TOLPR);
				if(sampleColor[2]< red.getBlueLow()) red.setBlueLow(sampleColor[2]/TOLPR);
				if(sampleColor[2]> red.getBlueHigh()) red.setBlueHigh(sampleColor[2]*TOLPR);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
				try{
					Thread.sleep(50);
				} catch (InterruptedException e) {break;}
			}
			
			//----------------------------------
			while(Button.DOWN.isUp()) {	
				colorSensor.fetchSample(sampleColor, 0);
				LCD.drawString("YELLOW, press down, shift 1s" + "    ", 0, 1);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
			}
			colorSensor.fetchSample(sampleColor, 0);
			yellow.setColorRange(sampleColor[0],sampleColor[0],sampleColor[1],sampleColor[1],sampleColor[2],sampleColor[2]);
			while(Button.DOWN.isDown()) {
				colorSensor.fetchSample(sampleColor, 0);
				if(sampleColor[0]< yellow.getRedLow()) yellow.setRedLow(sampleColor[0]/TOLPR);
				if(sampleColor[0]> yellow.getRedHigh()) yellow.setRedHigh(sampleColor[0]*TOLPR);
				if(sampleColor[1]< yellow.getGreenLow()) yellow.setGreenLow(sampleColor[1]/TOLPR);
				if(sampleColor[1]> yellow.getGreenHigh()) yellow.setGreenHigh(sampleColor[1]*TOLPR);
				if(sampleColor[2]< yellow.getBlueLow()) yellow.setBlueLow(sampleColor[2]/TOLPR);
				if(sampleColor[2]> yellow.getBlueHigh()) yellow.setBlueHigh(sampleColor[2]*TOLPR);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
				try{
					Thread.sleep(50);
				} catch (InterruptedException e) {break;}
			}
			
			//----------------------------------
			while(Button.DOWN.isUp()) {	
				colorSensor.fetchSample(sampleColor, 0);
				LCD.drawString("BLUE, press down, shift 1s" + "    ", 0, 1);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
			}
			colorSensor.fetchSample(sampleColor, 0);
			blue.setColorRange(sampleColor[0],sampleColor[0],sampleColor[1],sampleColor[1],sampleColor[2],sampleColor[2]);
			while(Button.DOWN.isDown()) {
				colorSensor.fetchSample(sampleColor, 0);
				if(sampleColor[0]< blue.getRedLow()) blue.setRedLow(sampleColor[0]/TOLPR);
				if(sampleColor[0]> blue.getRedHigh()) blue.setRedHigh(sampleColor[0]*TOLPR);
				if(sampleColor[1]< blue.getGreenLow()) blue.setGreenLow(sampleColor[1]/TOLPR);
				if(sampleColor[1]> blue.getGreenHigh()) blue.setGreenHigh(sampleColor[1]*TOLPR);
				if(sampleColor[2]< blue.getBlueLow()) blue.setBlueLow(sampleColor[2]/TOLPR);
				if(sampleColor[2]> blue.getBlueHigh()) blue.setBlueHigh(sampleColor[2]*TOLPR);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
				try{
					Thread.sleep(50);
				} catch (InterruptedException e) {break;}
			}
			
			while(Button.DOWN.isUp()) {	
				colorSensor.fetchSample(sampleColor, 0);
				LCD.drawString("GREEN, press down, shift 1s" + "    ", 0, 1);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
			}
			colorSensor.fetchSample(sampleColor, 0);
			green.setColorRange(sampleColor[0],sampleColor[0],sampleColor[1],sampleColor[1],sampleColor[2],sampleColor[2]);

			while(Button.DOWN.isDown()) {
				colorSensor.fetchSample(sampleColor, 0);
				if(sampleColor[0]< green.getRedLow()) green.setRedLow(sampleColor[0]/TOLPR);
				if(sampleColor[0]> green.getRedHigh()) green.setRedHigh(sampleColor[0]*TOLPR);
				if(sampleColor[1]< green.getGreenLow()) green.setGreenLow(sampleColor[1]/TOLPR);
				if(sampleColor[1]> green.getGreenHigh()) green.setGreenHigh(sampleColor[1]*TOLPR);
				if(sampleColor[2]< green.getBlueLow()) green.setBlueLow(sampleColor[2]/TOLPR);
				if(sampleColor[2]> green.getBlueHigh()) green.setBlueHigh(sampleColor[2]*TOLPR);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
				try{
					Thread.sleep(50);
				} catch (InterruptedException e) {break;}
			}
								
			//----------------------------------
			while(Button.DOWN.isUp()) {	
				colorSensor.fetchSample(sampleColor, 0);
				LCD.drawString("BLACK, press down, shift 1s" + "    ", 0, 1);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
			}
			colorSensor.fetchSample(sampleColor, 0);
			black.setColorRange(sampleColor[0],sampleColor[0],sampleColor[1],sampleColor[1],sampleColor[2],sampleColor[2]);
			while(Button.DOWN.isDown()) {
				colorSensor.fetchSample(sampleColor, 0);
				if(sampleColor[0]< black.getRedLow()) black.setRedLow(sampleColor[0]/TOLPR);
				if(sampleColor[0]> black.getRedHigh()) black.setRedHigh(sampleColor[0]*TOLPR);
				if(sampleColor[1]< black.getGreenLow()) black.setGreenLow(sampleColor[1]/TOLPR);
				if(sampleColor[1]> black.getGreenHigh()) black.setGreenHigh(sampleColor[1]*TOLPR);
				if(sampleColor[2]< black.getBlueLow()) black.setBlueLow(sampleColor[2]/TOLPR);
				if(sampleColor[2]> black.getBlueHigh()) black.setBlueHigh(sampleColor[2]*TOLPR);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
				try{
					Thread.sleep(50);
				} catch (InterruptedException e) {break;}
			}
			//----------------------------------
			while(Button.DOWN.isUp()) {	
				colorSensor.fetchSample(sampleColor, 0);
				LCD.drawString("WHITE, press down, shift 1s" + "    ", 0, 1);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
			}
			colorSensor.fetchSample(sampleColor, 0);
			white.setColorRange(sampleColor[0],sampleColor[0],sampleColor[1],sampleColor[1],sampleColor[2],sampleColor[2]);
			while(Button.DOWN.isDown()) {
				colorSensor.fetchSample(sampleColor, 0);
				if(sampleColor[0]< white.getRedLow()) white.setRedLow(sampleColor[0]/TOLPR);
				if(sampleColor[0]> white.getRedHigh()) white.setRedHigh(sampleColor[0]*TOLPR);
				if(sampleColor[1]< white.getGreenLow()) white.setGreenLow(sampleColor[1]/TOLPR);
				if(sampleColor[1]> white.getGreenHigh()) white.setGreenHigh(sampleColor[1]*TOLPR);
				if(sampleColor[2]< white.getBlueLow()) white.setBlueLow(sampleColor[2]/TOLPR);
				if(sampleColor[2]> white.getBlueHigh()) white.setBlueHigh(sampleColor[2]*TOLPR);
				LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 5);
				try{
					Thread.sleep(50);
				} catch (InterruptedException e) {break;}
			}	
		}
		
		//gibt die Farbe als String zurück
		 public String getColor(){
			 	colorSensor.fetchSample(sampleColor, 0);
			 	
				if(sampleColor[0]>green.getRedLow()&&sampleColor[0]<green.getRedHigh()&&sampleColor[1]>green.getGreenLow()&&sampleColor[1]<green.getGreenHigh()
						&&sampleColor[2]>green.getBlueLow()&&sampleColor[2]<green.getBlueHigh()) return "green";
				else if(sampleColor[0]>yellow.getRedLow()&&sampleColor[0]<yellow.getRedHigh()&&sampleColor[1]>yellow.getGreenLow()&&sampleColor[1]<yellow.getGreenHigh()
						&&sampleColor[2]>yellow.getBlueLow()&&sampleColor[2]<yellow.getBlueHigh()) return "yellow";
				else if(sampleColor[0]>red.getRedLow()&&sampleColor[0]<red.getRedHigh()&&sampleColor[1]>red.getGreenLow()&&sampleColor[1]<red.getGreenHigh()
						&&sampleColor[2]>red.getBlueLow()&&sampleColor[2]<red.getBlueHigh()) return "red";
				else if(sampleColor[0]>white.getRedLow()&&sampleColor[0]<white.getRedHigh()&&sampleColor[1]>white.getGreenLow()&&sampleColor[1]<white.getGreenHigh()
						&&sampleColor[2]>white.getBlueLow()&&sampleColor[2]<white.getBlueHigh()) return "white";
				else if(sampleColor[0]>black.getRedLow()&&sampleColor[0]<black.getRedHigh()&&sampleColor[1]>black.getGreenLow()&&sampleColor[1]<black.getGreenHigh()
						&&sampleColor[2]>black.getBlueLow()&&sampleColor[2]<black.getBlueHigh()) return "black";
				else if(sampleColor[0]>blue.getRedLow()&&sampleColor[0]<blue.getRedHigh()&&sampleColor[1]>blue.getGreenLow()&&sampleColor[1]<blue.getGreenHigh()
						&&sampleColor[2]>blue.getBlueLow()&&sampleColor[2]<blue.getBlueHigh()) return "blue";
				
				return "undefined";
		 }
		 
		 public void displayAllValues(ColorDefiner cd){

			 while(Button.ESCAPE.isUp()) {
				 colorSensor.fetchSample(sampleColor, 0);
				 LCD.clear();
				 LCD.drawString("Color : " + cd.getColor() + "        ", 0, 1);
				 LCD.drawString("RGB: " + Math.round(sampleColor[0]*1000) + " " +  Math.round(sampleColor[1]*1000) +" " +  Math.round(sampleColor[2]*1000) + "         ", 0, 2);
				 
				 //LCD.drawString("bk:r " + (int)(black.getRedLow()*1000) + " " + (int)(black.getRedHigh()*1000), 0, 3);
				 //LCD.drawString("bke:g " + (int)(black.getGreenLow()*1000) + " " + (int)(black.getGreenHigh()*1000), 0, 4);
				 //LCD.drawString("bk:b " + (int)(black.getBlueLow()*1000) + " " + (int)(black.getBlueHigh()*1000), 0, 5);
				 //LCD.drawString("red:r " + (int)(red.getRedLow()*1000) + " " + (int)(red.getRedHigh()*1000), 0, 3);
				 //LCD.drawString("red:g " + (int)(red.getGreenLow()*1000) + " " + (int)(red.getGreenHigh()*1000), 0, 4);
				 //LCD.drawString("red:b " + (int)(red.getBlueLow()*1000) + " " + (int)(red.getBlueHigh()*1000), 0, 5);
				 
				 //weiss
				 LCD.drawString("w:r " + (int)(white.getRedLow()*1000) + " " + (int)(white.getRedHigh()*1000), 0, 3);
				 LCD.drawString("w:g " + (int)(white.getGreenLow()*1000) + " " + (int)(white.getGreenHigh()*1000), 0, 4);
				 LCD.drawString("w:b " + (int)(white.getBlueLow()*1000) + " " + (int)(white.getBlueHigh()*1000), 0, 5);
				 
				 try{
						Thread.sleep(100);
					} catch (InterruptedException e) {break;}
			 } 
		 }
		 
		 public void writeFile(){
			 String filename = "color.txt";
			 File file = new File(filename);

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			
			    if ( ! file.exists() ) {
			        file.createNewFile();
			    }
			    //red, green, blue, white, yellow, black
			    writer.write(""+red.getRedLow()+";"+red.getRedHigh()+";"+red.getGreenLow()+";"+red.getGreenHigh()+";"+red.getBlueLow()+";"+red.getBlueHigh()+";\n");
			    writer.write(""+green.getRedLow()+";"+green.getRedHigh()+";"+green.getGreenLow()+";"+green.getGreenHigh()+";"+green.getBlueLow()+";"+green.getBlueHigh()+";\n");
			    writer.write(""+blue.getRedLow()+";"+blue.getRedHigh()+";"+blue.getGreenLow()+";"+blue.getGreenHigh()+";"+blue.getBlueLow()+";"+blue.getBlueHigh()+";\n");
			    writer.write(""+white.getRedLow()+";"+white.getRedHigh()+";"+white.getGreenLow()+";"+white.getGreenHigh()+";"+white.getBlueLow()+";"+white.getBlueHigh()+";\n");  
			    writer.write(""+yellow.getRedLow()+";"+yellow.getRedHigh()+";"+yellow.getGreenLow()+";"+yellow.getGreenHigh()+";"+yellow.getBlueLow()+";"+yellow.getBlueHigh()+";\n");
			    writer.write(""+black.getRedLow()+";"+black.getRedHigh()+";"+black.getGreenLow()+";"+black.getGreenHigh()+";"+black.getBlueLow()+";"+black.getBlueHigh()+";");
			    
			} catch (IOException e) {
			    System.out.println(e.getMessage());
		    }
		 }
		 
		 public boolean readFile(){
	 
			String filename = "color.txt";
			BufferedReader br;
			boolean ok=true;
			try{
			
				br = new BufferedReader(new FileReader(filename));
				String line;
				//red, green, blue, white, yellow, black
				if((line = br.readLine()) != null) {
					String[] parts = line.split(";"); 
					red.setRedLow(Double.parseDouble(parts[0]));
					red.setRedHigh(Double.parseDouble(parts[1]));
					red.setGreenLow(Double.parseDouble(parts[2]));
					red.setGreenHigh(Double.parseDouble(parts[3]));
					red.setBlueLow(Double.parseDouble(parts[4]));
					red.setBlueHigh(Double.parseDouble(parts[5]));			
			    } else ok = false;
				if((line = br.readLine()) != null) {
					String[] parts = line.split(";"); 
					green.setRedLow(Double.parseDouble(parts[0]));
					green.setRedHigh(Double.parseDouble(parts[1]));
					green.setGreenLow(Double.parseDouble(parts[2]));
					green.setGreenHigh(Double.parseDouble(parts[3]));
					green.setBlueLow(Double.parseDouble(parts[4]));
					green.setBlueHigh(Double.parseDouble(parts[5]));
			    }else ok = false;
				if((line = br.readLine()) != null) {
					String[] parts = line.split(";"); 
					blue.setRedLow(Double.parseDouble(parts[0]));
					blue.setRedHigh(Double.parseDouble(parts[1]));
					blue.setGreenLow(Double.parseDouble(parts[2]));
					blue.setGreenHigh(Double.parseDouble(parts[3]));
					blue.setBlueLow(Double.parseDouble(parts[4]));
					blue.setBlueHigh(Double.parseDouble(parts[5]));
			    }else ok = false;
				if((line = br.readLine()) != null) {
					String[] parts = line.split(";"); 
					white.setRedLow(Double.parseDouble(parts[0]));
					white.setRedHigh(Double.parseDouble(parts[1]));
					white.setGreenLow(Double.parseDouble(parts[2]));
					white.setGreenHigh(Double.parseDouble(parts[3]));
					white.setBlueLow(Double.parseDouble(parts[4]));
					white.setBlueHigh(Double.parseDouble(parts[5]));
			    }else ok = false;
				if((line = br.readLine()) != null) {
					String[] parts = line.split(";"); 
					yellow.setRedLow(Double.parseDouble(parts[0]));
					yellow.setRedHigh(Double.parseDouble(parts[1]));
					yellow.setGreenLow(Double.parseDouble(parts[2]));
					yellow.setGreenHigh(Double.parseDouble(parts[3]));
					yellow.setBlueLow(Double.parseDouble(parts[4]));
					yellow.setBlueHigh(Double.parseDouble(parts[5]));
			    }else ok = false;
				if((line = br.readLine()) != null) {
					String[] parts = line.split(";"); 
					black.setRedLow(Double.parseDouble(parts[0]));
					black.setRedHigh(Double.parseDouble(parts[1]));
					black.setGreenLow(Double.parseDouble(parts[2]));
					black.setGreenHigh(Double.parseDouble(parts[3]));
					black.setBlueLow(Double.parseDouble(parts[4]));
					black.setBlueHigh(Double.parseDouble(parts[5]));
			    }else ok = false;
					
			} catch (IOException e) {
			    System.out.println(e.getMessage());
			    ok = false;
		    }
			return ok;
		 }
		 
		 
		 //Kann als "Standalone" Testklasse ausgeführt
		public static void main(String[] args) throws InterruptedException { 

			final ColorDefiner cd = new ColorDefiner(new EV3ColorSensor(SensorPort.S4));
			cd.initColor();
			cd.writeFile();

			cd.readFile();	
			
			//cd.getColor()
			cd.displayAllValues(cd);
			
		}
}
