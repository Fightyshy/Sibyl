import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class Tiles extends JPanel{
	
	/*public static final Color Road = Color.gray;
	public static final Color Building = new Color(139,69,19);
	public static final Color Drone = Color.red;
	public static final Color Criminal = Color.blue;
	public static final Color CCommand = Color.green;
	*/
	
	public Color tileColour;
	
	public Tiles(){
		
	}
	
	public Tiles(Color tc){
		tileColour = tc;
		setBackground(tc);
		setBorder(BorderFactory.createLineBorder(Color.black));
		//setPreferredSize(new Dimension(70, 70));
	}
	
	public void setTileColour (Color tc){
		tileColour = tc;
	}
	
	public Color getTileColour(){
		return tileColour;
	}
}
