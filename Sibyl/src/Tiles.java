import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
class Tiles extends JPanel{
	
	/*public static final Color Road = Color.gray;
	public static final Color Building = new Color(139,69,19);
	public static final Color Drone = Color.red;
	public static final Color Criminal = Color.blue;
	public static final Color CCommand = Color.green;
	*/
	
	public boolean isPassable;
	public boolean isEntity;
	public Color tileColor;
	public int size;
	
	public Tiles(){
		
	}
	
	public Tiles(boolean isPass, boolean isEnt, Color tc, int sz){
		isPassable = isPass;
		isEntity = isEnt;
		tileColor = tc;
		size = sz;
		setBackground(tc);
		setBorder(BorderFactory.createLineBorder(Color.black));
		//setPreferredSize(new Dimension(70, 70));
	}
	
	public void setIsPassable (boolean isPass){
		isPassable = isPass;
	}
	
	public boolean getIsPassable(){
		return isPassable;
	}
	
	public void setIsEntity (boolean isEnt){
		isEntity = isEnt;
	}
	
	public boolean getIsEntity(){
		return isEntity;
	}
	
	public void setTileColor (Color tc){
		tileColor = tc;
	}
	
	public Color getTileColor(){
		return tileColor;
	}
	
	public void setSize(int sz){
		size = sz;
	}
	
	public int getSize(int sz){
		return size;
	}
}
