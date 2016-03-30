import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class Drone extends JPanel{
	
	//Values of a drone
	public int droneDesignation; //Designation of drone.
	public int detectionRadius; //set radius for each Drone's detection, currently set to ints. Can be doubles depending on grid layout
	public boolean hasCriminal; //Checks if Criminal is currently being held.
	public int positionX; //X coord position
	public int positionY; //Y coord position
	public int order; // Sets order to follow
	public Tiles droneShape = new Tiles(true, true, Color.red, 24); //Set tile parameters.
	
	//Clear
	public Drone(){
	}
	
	//Initialise
	public Drone(int id, int detRad, boolean hasCrim, int posX, int posY, Tiles ds, int order){
		droneDesignation = id;
		detectionRadius = detRad;
		hasCriminal = hasCrim;
		positionX = posX;
		positionX = posY;
		droneShape = ds;
		this.order = order;
	}
	
	//Setters and getters
	public void setDroneDesignation(int id){
		droneDesignation = id;
	}
	
	public int getDroneDesignation(){
		return droneDesignation;
	}
	
	public void SetDetectionRadius(int detRad){
		detectionRadius = detRad;
	}
	
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
	
	public void setOrder(int order){
		this.order = order;
	}
	
	public int getOrder(){
		return this.order;
	}
}
