import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class Drone extends JPanel{
	
	//Values of a drone
	public int droneDesignation; //Designation of drone.
	public int detectionRadius; //set radius for each Drone's detection, currently set to ints. Can be doubles depending on grid layout
	public boolean hasCriminal; //Checks if Criminal is currently being held.
	public int positionX;
	public int positionY;
	public Tiles droneShape = new Tiles(true, true, Color.red, 24);
	
	//Instantiate
	public Drone(){
	}
	
	//Initialise
	public Drone(int id, int detRad, boolean hasCrim, int posX, int posY, Tiles ds){
		droneDesignation = id;
		detectionRadius = detRad;
		hasCriminal = hasCrim;
		positionX = posX;
		positionX = posY;
		droneShape = ds;
	}
	
	//Value setting block
	public void setDroneDesignation(int id){
		droneDesignation = id;
	}
	
	public int getDroneDesignation(){
		return droneDesignation;
	}
	
	public void SetDetectionRadius(int detRad){
		detectionRadius = detRad;
	}
	
	//Value getting block
	public int getDetectionRadius(){
		return detectionRadius;
	}
	
	public void setHasCriminal(boolean hasCrim){
		hasCriminal = hasCrim;
	}
	
	public boolean getHasCriminal(){
		return hasCriminal;
	}
	
	public void setPositionX(int posX){
		positionX = posX;
	}
	
	public int getPositionX(){
		return positionX;
	}
	
	public void setPositionY(int posY){
		positionY = posY;
	}
	
	public int getPositionY(){
		return positionY;
	}
	
	public void setDroneShape(Tiles ds){
		droneShape = ds;
	}
	
	public Tiles getDroneShape(){
		return droneShape;
	}
	
	/*Methods below
	 * One for movement/casual patrol
	 * One for detection
	 * One for intercepting
	 * One for apprehending and returning to base
	 */
	
	public void movementPatrol(Graphics g){
		
	}
	
	public void movementIntercept(){
		
	}
	
	public void detection(){
		
	}
	
	public void arrestAndReturn(){
		
	}
}
