import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class Drone extends JPanel{
	
	//Values of a drone
	public int droneDesignation; //Designation of drone.
	public boolean hasCriminal; //Checks if Criminal is currently being held.
	public int positionX; //X coord position
	public int positionY; //Y coord position
	public int order; // Sets order to follow
	public Tiles droneShape = new Tiles(Color.red); //Set tile parameters.
	
	//Clear
	public Drone(){
	}
	
	//Initialise
	public Drone(int id, boolean hasCrim, int posX, int posY, Tiles ds, int order){
		droneDesignation = id;
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
