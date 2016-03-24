import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SimulationEnviroment extends JFrame {
	
	//Initialised variables
	private int mapWidth = 12; //GridLayout width dimension
	private int mapHeight = 12; //GridLayout width dimension
	private JPanel[][] mapArray;
	private boolean gridCheck = false; //checks if grid is active.
	private boolean cCommandCheck = false;
	private Random randCrim = new Random();
	
	private ArrayList<Drone> droneList = new ArrayList<Drone>();
	private ArrayList<Criminal> criminalList = new ArrayList<Criminal>();
	int droneID = 0;
	
	private JPanel panel;
	
	CentralCommand cCommand;
	Drone drone;
	Criminal criminal;
	
	//Note: Add arrays here to keep track of Drones and Criminals on the grid
	
	//2D Integer array that uses digits to determine tile properties.
	int mapArrayPosition[][] = {
			{1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
			{1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
			{1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0},
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0},
			{1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0},
			{0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1},
			{0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1},
			{0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0},
			{1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
			{1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
			{1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
		};
	
	//Seperate Int 2D array that contains crimeCoEfficient values, low/zero means no priority, high = more priority
	//int crimeCoEfficientGrid[][] = {
		
	//}
	
	public SimulationEnviroment() {
		initUI();
	}
	
	private void initUI(){
		
		MenuBar();
		setTitle("'Sibyl' - Intelligent Policing System - Dissertation");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	//Mechanisms for CCommand
	public void cCommandEntity(){
		cCommand = new CentralCommand();
		
		cCommand.setPositionX(3);
		cCommand.setPositionY(3);
		
		int spawn = mapArrayPosition[cCommand.getPositionY()][cCommand.getPositionX()];
		
		if(spawn == 1 || cCommandCheck == true){
			System.out.println("No");
		}
		else{
			if(gridCheck == true){
				mapArray[cCommand.getPositionY()][cCommand.getPositionX()].add(cCommand.getCCommandShape());//.getCCommandShape());
				getContentPane().add(cCommand);
				setVisible(true);
				cCommandCheck = true;
			}
			else{
				System.out.println("No");
			}
		}
	}
	
	//Mechanisms for Drones
	public void droneEntity(){
		drone = new Drone();
		drone.setDroneDesignation(droneID++);
		drone.SetDetectionRadius(3);
		droneList.add(drone);
		System.out.println(droneList.size());
		//In reverse order on 2D Array I.e. [y][x], sets spawn to CCommand's position
		drone.setPositionX(cCommand.getPositionX());
		drone.setPositionY(cCommand.getPositionY());
		
		System.out.println(drone.getPositionX()+" "+ drone.getPositionY());
			if(gridCheck == true){
			mapArray[drone.getPositionY()][drone.getPositionX()].add(drone.getDroneShape());
			getContentPane().add(drone);
			setVisible(true);
			
			}
			else{
				System.out.println("No");
			}
		//Apply the things here
	}
	
	public void removeDrone(Drone drone){
		if(droneList.size() != 0){
			drone = droneList.get(0);
			droneList.remove(0);
			mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
			getContentPane().remove(drone);
		}
		else{
			System.out.println("Nothing to delete");
		}
		panel.revalidate();
		panel.repaint();
	}
	
	//Mechanism for Criminals
	public void criminalEntity(){
		criminal = new Criminal();
		criminalList.add(criminal);
		criminal.setIsCaught(false);
		//random map location
		int randCrimPosX = randCrim.nextInt((11 - 0)) + 0;
		int randCrimPosY = randCrim.nextInt((11 - 0)) + 0;
		
		//In reverse order on 2D Array I.e. [y][x], sets spawn to CCommand's position
		
		int spawn;
		
		do{
			
			criminal.setPositionX(randCrimPosX);
			criminal.setPositionY(randCrimPosY);
			
			spawn = mapArrayPosition[criminal.getPositionY()][criminal.getPositionX()];
		
			//Check for duplicate criminal on square not needed - Multiple crimes/severity can occur on same square.
			
		if(spawn == 1){
				System.out.println("No");
				System.out.println(criminal.getPositionX());
				System.out.println(criminal.getPositionY());
				
				//ask for new values and set again until condition met
				randCrimPosX = randCrim.nextInt((11 - 0)) + 0;
				randCrimPosY = randCrim.nextInt((11 - 0)) + 0;
				
				criminal.setPositionX(randCrimPosX);
				criminal.setPositionY(randCrimPosY);
			}
			else{
				if(gridCheck == true){
				mapArray[criminal.getPositionY()][criminal.getPositionX()].add(criminal.getCriminalShape());
				mapArray[criminal.getPositionY()][criminal.getPositionX()].add(criminal);
				getContentPane().add(criminal);
				setVisible(true);
				}
				else{
					System.out.println("No");
					System.out.println(criminal.getPositionX());
					System.out.println(criminal.getPositionY());
				}
				break;
			}
		}while(spawn == 1);
	}
	
	public void removeCriminal(Criminal criminal){
		if(criminalList.size() != 0){
			criminal = criminalList.get(0);
			criminalList.remove(0);
			mapArray[criminal.getPositionY()][criminal.getPositionX()].remove(criminal.getCriminalShape());
			getContentPane().remove(criminal);
		}
		else{
			System.out.println("Nothing to delete");
		}
		panel.revalidate();
		panel.repaint();
	}
	
	//Method for generating landscape. Only uses "Building" and "Road" tiles.
	private void grid(){
		
		panel = new JPanel(new GridLayout(mapWidth, mapHeight, 0, 0)); //12x12 Grid with no gaps
		mapArray =  new JPanel[mapWidth][mapHeight]; //Actual Panels to manipulate
		
		if(gridCheck != true){
			for(int i = 0; i < mapWidth; i++){
				for(int j = 0; j < mapHeight; j++){
					//12x12 (144) "buildings" and "roads" created here
					Tiles building = new Tiles(false, false, new Color(139,69,19), 70); //variable Building of custom class Tile, "obstacle"
					Tiles road = new Tiles(true, false, Color.gray, 70); //variable Building of custom class Tile, "pathway"
					Tiles tileTypes[] = {road, building};
					
					//Checks tile at mapArrayPosition[i][j] and applies correct tile to mapArray.
					if(mapArrayPosition[i][j] == 0){
						mapArray[i][j] = tileTypes[0];
					}
					else {
						mapArray[i][j] = tileTypes[1];
					}
	
					panel.add(mapArray[i][j]);
					getContentPane().add(panel);
					setVisible(true);
					gridCheck = true; //If grid made, then set to true, prevent duplicate grids
				}
			}System.out.println(mapArray[0][0]);
		}
		else{
			System.out.println("Already up");
		}
	}
	
	//Update all the things at once
	private void execute(Component obj){

		//Spawns criminal every 5 seconds onto map
		Timer spawnCriminal = new Timer(5000, new ActionListener(){
			public void actionPerformed(ActionEvent criminals){
				criminalEntity();
			}
		});spawnCriminal.start();
		
		//Current move random patrol movement for drones.
		Timer timer = new Timer(1000, new ActionListener(){
		
			public void actionPerformed(ActionEvent movement){	
				move(drone);
				revalidate();
				repaint();
			}
			
			public void move(Component obj){
			for(Drone drone: droneList){	
				int row = drone.getPositionX();
				int col = drone.getPositionY();
				JPanel detectionPos = mapArray[col][row];
				JPanel detectionNeg = mapArray[col][row];
				
				for(Criminal crim : criminalList){
					
					JPanel crimLoc = mapArray[crim.getPositionY()][crim.getPositionX()];
					int detRad = drone.getDetectionRadius();
					
					for(int i = 0; i < detRad;i++){
						for(int j = 0; j < detRad;j++){
							
							//If at column border
							if(col+i > 10 && row > 10){
								detectionPos = mapArray[col-detRad][row-detRad];
							}
							else if(col-i < 0 && row-j < 0){
								detectionNeg = mapArray[col-detRad][row-detRad];
							}
							else if(col+i > 10 && row-j < 0){
								detectionNeg = mapArray[col-detRad][row+detRad];
							}
							else if(col-i < 0 && row+j > 10){
								detectionNeg = mapArray[col+detRad][row-detRad];
							}
							else if(col+i > 10){
								if(row+j > 10){
									detectionPos = mapArray[col-detRad][row-detRad];
								}
								else if(row-j < 0){
									detectionPos = mapArray[col-detRad][row+j];
								}
								else{
									detectionPos = mapArray[col-detRad][row+j];
								}
							}
							else if(col-i < 0){
								detectionNeg = mapArray[col+detRad][row-j];
							}
							else if(row+j > 10){
								detectionPos = mapArray[col+i][row-detRad];
							}
							else if(row-j < 0){
								detectionNeg = mapArray[col-i][row+detRad];
							}
							//Standard detection
							else{
								detectionPos = mapArray[col+i][row+j];
								detectionNeg = mapArray[col-i][row-j];
							}
							if(detectionPos == crimLoc || detectionNeg == crimLoc){
								System.out.println("! " + crim.getPositionY() + " " + crim.getPositionX());
								System.out.println(drone.getPositionY() + " " + crim.getPositionX());
							}
						}
					}
				}
				
				
				boolean moved = false;
					do{
						int direction = (int) (Math.round(Math.random() * 3));
						int order = 0;
						int nextRow = row;
						int nextCol = col;
						
						mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
						//mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone);
						
						
						/*"Arresting protocol. Iterates through criminalList to see if one of their coordinates matches the current location of a drone, and if so, deletes.
						 * "Should also take crime-coefficient from criminal"
						 * 
						 * Because of ConcurrentModificationException (I.E. using the same "thread" to add(because of CriminalEntity() a
						 * random criminal every five seconds, usage of an iterator manage the thread to allow removing here has been used.
						 * Code taken and adapted from 
						 * http://stackoverflow.com/questions/223918/iterating-through-a-list-avoiding-concurrentmodificationexception-when-removing
						 */
						for(Iterator<Criminal> iterator = criminalList.iterator(); iterator.hasNext();){
		            			Criminal delCrim = iterator.next();
		            			if(mapArray[drone.getPositionY()][drone.getPositionX()] == mapArray[delCrim.getPositionY()][delCrim.getPositionX()] ){
		            			iterator.remove();
		            			mapArray[delCrim.getPositionY()][delCrim.getPositionX()].remove(delCrim.getCriminalShape());
		            			getContentPane().remove(delCrim);
		                    }
		                    else{
		                    	
		                    }
						}
						
	                    switch (direction) {
	                    case 0:
	                        nextRow--;
	                        break;
	                    case 1:
	                        nextCol++;
	                        break;
	                    case 2:
	                        nextRow++;
	                        break;
	                    case 3:
	                        nextCol--;
	                        break;
	                    }
						//break;
						
						//Checks if next movement is within grid, and if the position is a building or not.
	                    if (nextRow >= 0 && nextRow < 12 && nextCol >= 0 && nextCol < 12 && mapArrayPosition[nextCol][nextRow] != 1) {
	                        row = nextRow;
	                        col = nextCol;
	                        moved = true;
	                    }
	                    
	                    if(row != -1 || row != 12 || col != -1 || col != 12){
		                    drone.setPositionX(row);
		                    drone.setPositionY(col);
		                    //mapArray[nextCol][nextRow].add(drone);
		                    mapArray[col][row].add(drone.getDroneShape());
		                    }
	                    else{
	                    	System.out.println("Almost went out of bounds");
	                    }
					}while(!moved);
					//System.out.println(drone.getLocation());
			}
		}});
		timer.start();
	}
	
	private void MenuBar() {
		//Toolbar
		JMenuBar menu = new JMenuBar();
		
		//Main item "File"
		JMenu file = new JMenu("File");
		JMenu enviro = new JMenu("Enviroment");
		final JMenu drones = new JMenu("Drones");
		JMenu criminals = new JMenu("Criminals");
		JMenu cCommand = new JMenu("Central Command");
		
		//File SubItems
		//SubItem "Help" - Resets program to tabula rasa
		JMenuItem resetMenuItem = new JMenuItem("Help");
		resetMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				EventQueue.invokeLater(new Runnable() {
					
					public void run() {
						HelpWindow help = new HelpWindow();
						help.setVisible(true);
					}
				});
			}
		});
		
		//SubItem "Exit" - Terminates Program
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		file.add(resetMenuItem);
		file.add(exitMenuItem);
		menu.add(file);

		//Simulation SubItems
		//SubItem "Generate" - Resets program to tabula rasa
		JMenuItem genMenuItem = new JMenuItem("Generate");
		genMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					grid();
				};
			});
		
		//SubItem "Execute" - Terminates Program
		JMenuItem execMenuItem = new JMenuItem("Execute");
		execMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				execute(drones);
			}
		});
		
		enviro.add(genMenuItem);
		enviro.add(execMenuItem);
		menu.add(enviro);
		
		//Drones SubItems
		//SubItem "Help" - Resets program to tabula rasa
		JMenuItem addDroneMenuItem = new JMenuItem("Add Drone");
		addDroneMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					droneEntity();
				};
		});
		
		//SubItem "Exit" - Terminates Program
		JMenuItem removeDroneMenuItem = new JMenuItem("Remove Drone");
		removeDroneMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				removeDrone(drone);
			}
		});
		
		drones.add(addDroneMenuItem);
		drones.add(removeDroneMenuItem);
		menu.add(drones);
		
		//File SubItems
		//SubItem "Help" - Resets program to tabula rasa
		JMenuItem addCrimMenuItem = new JMenuItem("Add Criminal");
		addCrimMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				criminalEntity();
			}
		});
		
		//SubItem "Exit" - Terminates Program
		JMenuItem removeCrimMenuItem = new JMenuItem("Remove Criminal");
		removeCrimMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				removeCriminal(criminal);
			}
		});
		
		criminals.add(addCrimMenuItem);
		criminals.add(removeCrimMenuItem);
		menu.add(criminals);
		
		//File SubItems
		//SubItem "Help" - Resets program to tabula rasa
		JMenuItem addCCommandMenuItem = new JMenuItem("Add Central Command");
		addCCommandMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cCommandEntity();
			}
		});
		
		//SubItem "Exit" - Terminates Program
		JMenuItem removeCCommandMenuItem = new JMenuItem("Remove Central Command");
		removeCCommandMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		
		cCommand.add(addCCommandMenuItem);
		cCommand.add(removeCCommandMenuItem);
		menu.add(cCommand);
		
		setJMenuBar(menu);
	}
}
