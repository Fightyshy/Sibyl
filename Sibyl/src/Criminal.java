import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class Criminal extends JPanel{
	public int crimeCoefficient; //Abstract value of crime severity
	public boolean isCaught; //If false, remains on map, if true after acknowledging forced deletion or capture, then vanishes
	public int positionX;
	public int positionY;
	public Tiles criminalShape = new Tiles(true, true, Color.blue, 24);
	
	public Criminal(){
		
	}
	
	public Criminal(int crimeCo, boolean caught, int posX, int posY, Tiles cs){
		crimeCoefficient = crimeCo;
		isCaught = caught;
		positionX = posX;
		positionY = posY;
		criminalShape = cs;
	}
	
	public void setCrimeCoefficient(int crimeCo){
		crimeCoefficient = crimeCo;
	}
	
	public int getCrimeCoefficient(){
		return crimeCoefficient;
	}
	
	public void setIsCaught(boolean caught){
		isCaught = caught;
	}
	
	public boolean getIsCaught(){
		return isCaught;
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
	
	public void setCriminalShape(Tiles cs){
		criminalShape.setTileColor(Color.blue);
		criminalShape.setIsPassable(true);
		criminalShape.setIsEntity(true);
		criminalShape.setSize(24);
		criminalShape = cs;
	}
	
	public Tiles getCriminalShape(){
		return criminalShape;
	}
}
