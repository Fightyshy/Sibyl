import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class CentralCommand extends JPanel{
	
	public int positionX;
	public int positionY;
	public Tiles cCommandShape = new Tiles(true, true, Color.green, 24);
	
	public CentralCommand(){
		
	}
	
	public CentralCommand(int posX, int posY, Tiles cCS){
		positionX = posX;
		positionY = posY;
		cCommandShape = cCS;
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
	
	public void setCCommandShape(Tiles ds){
		cCommandShape.setTileColor(Color.green);
		cCommandShape.setIsPassable(true);
		cCommandShape.setIsEntity(true);
		cCommandShape = ds;
	}
	
	public Tiles getCCommandShape(){
		return cCommandShape;
	}
}
