import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class Criminal extends JPanel{
	public int crimeCoefficient; //Abstract value of crime severity
	public int taggedBy;
	public int positionX;
	public int positionY;
	public Tiles criminalShape = new Tiles(true, true, Color.blue, 24);
	
	public Criminal(){
		
	}
	
	public Criminal(int crimeCo, int tag, int posX, int posY, Tiles cs){
		crimeCoefficient = crimeCo;
		taggedBy = tag;
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
	
	public void setTaggedBy(int tag){
		taggedBy = tag;
	}
	
	public int getTaggedBy(){
		return taggedBy;
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
