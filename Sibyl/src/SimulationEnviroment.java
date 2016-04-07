import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
//import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
	private boolean cCommandCheck = false; //Checks if central command is already up
	private boolean executeCheck = false; //check if main execution block already activated.
	private Random randCrim = new Random(); //Random criminal position
	private Random randCrimCo = new Random(); //Random criminal crime coefficient
	
	public ArrayList<Drone> droneList = new ArrayList<Drone>(); //List of currently active drones
	public ArrayList<Criminal> criminalList = new ArrayList<Criminal>(); //List of currently active criminals
	
	int droneID = 0; //Value to be assigned for droneDesignation
	
	private JPanel panel; //New JPanel
	
	private Node[][] nodes; //Grid of nodes
	
	//Initialise the objects below
	public CentralCommand cCommand;
	public Drone drone;
	public Criminal criminal;
	
	public Integer[] crimeCo = {0, 0, 0, 0}; //Crime coefficient values for 4 sectors, divided into 6x6 "sectors". "Integer" instead of "int" to use "Collections" library to get highest crime coefficient
	
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
		//initialise specific GUI dimensions
		menuBar();
		setTitle("'Sibyl' - Intelligent Policing System - Dissertation");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	//Mechanisms for CCommand
	public void cCommandEntity(){
		cCommand = new CentralCommand(); //new entity
		
		//Fixed deploy to (3,3)
		cCommand.setPositionX(3);
		cCommand.setPositionY(3);
		
		//Check if location is valid, if "1", then does not allow.
		int spawn = mapArrayPosition[cCommand.getPositionY()][cCommand.getPositionX()];
		
		//Only one central command can be active at a time. This check for that and if square is valid terrain.
		if(spawn == 1 || cCommandCheck == true){
			System.out.println("Error - Already generated");
		}
		else{
			//If grid is generated, then add and make visible.
			if(gridCheck == true){
				mapArray[cCommand.getPositionY()][cCommand.getPositionX()].add(cCommand.getCCommandShape());//.getCCommandShape());
				getContentPane().add(cCommand);
				setVisible(true);
				cCommandCheck = true;
			}
			else{
				System.out.println("Error - Grid not generated");
			}
		}
	}
	
	//Mechanisms for Drones
	public void droneEntity(){
		//Check if central command is up. Drones will not spawn if not active.
		if(cCommandCheck==true){
			drone = new Drone();
			drone.setDroneDesignation(droneID++); //Incremental droneDesignation based on how many time this command had been triggered
			droneList.add(drone); //Add to list of active drones
			System.out.println(droneList.size());
			drone.setOrder(0); //Set to default patrol
			//In reverse order on 2D Array I.e. [y][x], sets spawn to CCommand's position
			drone.setPositionX(cCommand.getPositionX());
			drone.setPositionY(cCommand.getPositionY());
			
			//If true, then add and make visible
			System.out.println(drone.getPositionX()+" "+ drone.getPositionY());
				if(gridCheck == true){
				mapArray[drone.getPositionY()][drone.getPositionX()].add(drone.getDroneShape());
				getContentPane().add(drone);
				setVisible(true);
				
				}
				else{
					System.out.println("Error - Central Command not generated");
				}
			//Apply the things here
		}
		else{
			System.out.println("Error - Grid not generated");
		}
	}
	
	public void removeDrone(Drone drone){
		//Check drone list to see if empty, if not, then delete first available drone.
		if(droneList.size() != 0){
			drone = droneList.get(0); //get drone from list
			droneList.remove(0); //remove first available drone from list
			mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape()); //remove drone at position
			getContentPane().remove(drone); //remove drone from content pane
		}
		else{
			System.out.println("Nothing to delete");
		}
		//refresh GUI
		panel.revalidate();
		panel.repaint();
	}
	
	//Mechanism for Criminals
	public void criminalEntity(){
		criminal = new Criminal();
		//random map location
		int randCrimPosX = randCrim.nextInt((11 - 0)+1) + 0;
		int randCrimPosY = randCrim.nextInt((11 - 0)+1) + 0;
		
		//random crime coefficient
		int randCrimeCo = (randCrimCo.nextInt((10 - 1)) + 1)+1;
		
		//In reverse order on 2D Array I.e. [y][x], sets spawn to CCommand's position
		
		int spawn;
		
		do{
			
			//set position and tag value to -1.
			criminal.setPositionX(randCrimPosX);
			criminal.setPositionY(randCrimPosY);
			criminal.setTaggedBy(-1);
			
			spawn = mapArrayPosition[criminal.getPositionY()][criminal.getPositionX()];
			//check if tile is valid
			if(spawn == 1){
				System.out.println("Error - invalid position");
				
				//ask for new values and set again until condition met
				randCrimPosX = randCrim.nextInt((11 - 0)) + 0;
				randCrimPosY = randCrim.nextInt((11 - 0)) + 0;
				
				criminal.setPositionX(randCrimPosX);
				criminal.setPositionY(randCrimPosY);
				criminal.setCrimeCoefficient(randCrimeCo);
			}
			else{
				//Add 
				if(gridCheck == true){
					//Add criminal to position and make visible
					mapArray[criminal.getPositionY()][criminal.getPositionX()].add(criminal.getCriminalShape());
					criminal.setCrimeCoefficient(randCrimeCo);
					getContentPane().add(criminal);
					criminalList.add(criminal); //Add to active criminalList
					setVisible(true);
					}
					else{
						System.out.println("Error - Grid not generated");
					}
					break;	
			}
			//Check for duplicate criminal on square not needed - Multiple crimes/severity can occur on same square.
			
			
		
		}while(spawn == 1);
	}
	
	//Same as removing an active Drone, except using the criminalList
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
		
		//For adding specific components to a gridLayout cell (The "tiles" on the "grid", refused multiple times, but first appearing here.
		//Reference: http://stackoverflow.com/questions/2510159/can-i-add-a-component-to-a-specific-grid-cell-when-a-gridlayout-is-used
		
		panel = new JPanel(new GridLayout(mapWidth, mapHeight, 0, 0)); //12x12 Grid with no gaps
		mapArray =  new JPanel[mapWidth][mapHeight]; //Actual Panels to manipulate
		
		if(gridCheck != true){
			for(int i = 0; i < mapWidth; i++){
				for(int j = 0; j < mapHeight; j++){
					//12x12 (144) "buildings" and "roads" created here
					Tiles building = new Tiles(new Color(139,69,19)); //variable Building of custom class Tile, "obstacle"
					Tiles road = new Tiles(Color.gray); //variable Building of custom class Tile, "pathway"
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
			}
			
			//Generate nodes onto grid, used for pathfinder to navigate
			nodes = new Node[mapWidth][mapHeight];
			for(int i = 0; i < mapWidth; i++){
				for(int j = 0; j < mapHeight; j++){
					nodes[i][j] = new Node(i,j);
				}
			}
		}
		else{
			System.out.println("Already up");
		}
	}
	
	//Update all the things at once
	private void execute(Component obj){

		/*Code borrowed and adapted from for use in:
		 * Simultaneous activation of drones using arrayLists: http://stackoverflow.com/questions/21617038/how-can-i-use-an-arraylist-to-display-moving-objects
		 */
		
		//Spawns criminal every 5 seconds onto map
		Timer spawnCriminal = new Timer(5000, new ActionListener(){
			public void actionPerformed(ActionEvent criminals){
				criminalEntity();
			}
		});spawnCriminal.start();
		
		//Prior to activating, checks if the crime coefficient for ALL sectors is higher than 20, then starts a repeating interval every 10 seconds that makes drones follow another order rather than standard
		//patrol
			Timer assumeControl = new Timer(10000, new ActionListener(){
				public void actionPerformed(ActionEvent control){
					Random randDrone = new Random();
					System.out.println("done");
					//If when this triggers, reset all criminal designations to default.
					for(Criminal crimReset: criminalList){
						crimReset.setTaggedBy(-1);
					}
					//If the max value crime coefficient is higher than 20, select a random drone to go to that sector.
					//Max value getter referenced from: http://www.java2s.com/Tutorial/Java/0140__Collections/Minimumandmaximumnumberinarray.htm
					if((int) Collections.max(Arrays.asList(crimeCo)) > 20){
						int randDroneChoosen = randDrone.nextInt((droneList.size()-0)+1)+0;
						
						if(randDroneChoosen>-1 && randDroneChoosen< droneList.size()){
							droneList.get(randDroneChoosen).setOrder(3);
						}
					}
					else{
						System.out.println("Not yet");
					}
				}
			});
			assumeControl.start();
				
		//New timer with actionListener to execute methods
		final Timer mainTimer = new Timer(1000, new ActionListener(){
		
			//Execute method and refresh gui for every one second on the time (1000ms)
			public void actionPerformed(ActionEvent movement){	
				move(drone);
				revalidate();
				repaint();
			}
			
			public void move(Component obj){
				//Perform for each active drone
			for(Drone drone: droneList){	
				int row = drone.getPositionX(); //X coordinate position
				int col = drone.getPositionY(); //Y coordinate position
				List<Node> pathList = new ArrayList<Node>(); //ArrayList containing coordinates to paths
				Criminal taggedCrim = new Criminal(); //Criminal object that is the currently tagged criminal for one drone.
			
			//If drone currently has criminal arrested, 
			if(drone.getHasCriminal() == true){
				drone.setOrder(2);
			}
				
			if(drone.getOrder() == 0){
				/*Loose patrol method
				 * Moves the drone around in a random, erratic order, default setting for when orders 1 (detect and arrest), and 2 (move to "more dangerous" sector point)
				 * Code referenced from: http://stackoverflow.com/questions/21032140/moving-jlabel-to-a-different-place-in-the-jpanel-pacman-like-game
				 */
				boolean moved = false;
					do{
						int direction = (int) (Math.round(Math.random() * 3)); //Random direction
						
						//Set current value to variable for manipulating
						int nextRow = row;
						int nextCol = col;
						
						mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
						
						//Switch to choose which direction
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
						
						//Checks if next movement is within grid, and if the position is a building or not.
	                    if (nextRow >= 0 && nextRow < 12 && nextCol >= 0 && nextCol < 12 && mapArrayPosition[nextCol][nextRow] != 1) {
	                        row = nextRow;
	                        col = nextCol;
	                        moved = true;
	                    }
	                    
	                    if(row != -1 || row != 12 || col != -1 || col != 12){
		                    drone.setPositionX(row);
		                    drone.setPositionY(col);
		                    mapArray[col][row].add(drone.getDroneShape());
		                    }
	                    else{
	                    	System.out.println("Almost went out of bounds");
	                    }
	                    detect(drone);
					}while(!moved);
			}
			else if(drone.getOrder() == 3){
				int xLoc = 0;
				int yLoc = 0;
				int listCount = 0;
				
				int maxCrimeCo = (int) Collections.max(Arrays.asList(crimeCo)); //Get the first, maximum crime coefficient value based on all sectors.
				
				for(int i = 0; i < 4; i++){
					System.out.println(crimeCo[i]);
					//Code below used from: http://www.java2s.com/Tutorial/Java/0140__Collections/Minimumandmaximumnumberinarray.htm
					
					//For each possible sector, check if it's the highest value, then set the pathfinder to the a location within the sector
					if(maxCrimeCo == crimeCo[0]){
						pathList = pathfinder(drone.getPositionX(), drone.getPositionY(), cCommand.getPositionX(), cCommand.getPositionY());
						xLoc = 3;
						yLoc = 3;
						break;
					}
					else if(maxCrimeCo == crimeCo[1]){
						pathList = pathfinder(drone.getPositionX(), drone.getPositionY(), 9, 2);
						xLoc = 9;
						yLoc = 2;
						break;
					}
					else if(maxCrimeCo == crimeCo[2]){
						pathList = pathfinder(drone.getPositionX(), drone.getPositionY(), 3, 9);
						xLoc = 3;
						yLoc = 9;
						break;
					}
					else if(maxCrimeCo == crimeCo[3]){
						pathList = pathfinder(drone.getPositionX(), drone.getPositionY(), 9, 8);
						xLoc = 9;
						yLoc = 8;
						break;
					}
				}
					while(!((drone.getPositionX() == xLoc) && (drone.getPositionY() == yLoc))){
						mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
						
						System.out.println(pathList.get(listCount).yCoord+ " "+pathList.get(listCount).xCoord );
						
						row = pathList.get(listCount).xCoord;
						col = pathList.get(listCount).yCoord;
						drone.setPositionX(row);
						drone.setPositionY(col);
						
						mapArray[col][row].add(drone.getDroneShape());
						listCount++;
					}
					drone.setOrder(0);
			}
			else if(drone.getOrder() == 1){
				//Execute path searching to the first-detected criminal, resume standard if absolutely no more criminals in detection radius
				//Sets to return to home base, will always have "arrested" at the end of this order.
				int listCount = 0;

				//Set the drone's current acquisition as taggedCrim
				for(Criminal crim: criminalList){
					if(drone.getDroneDesignation() == crim.getTaggedBy()){
						taggedCrim = crim;
					}
				}
				
				pathList = pathfinder(row, col, taggedCrim.getPositionX(), taggedCrim.getPositionY()); //Establish path
				
				//Execute while pathList is not empty, increment a listCount that goes through pathList and mores drone to end destination
				while((!((drone.getPositionX() == taggedCrim.getPositionX()) && (drone.getPositionY() == taggedCrim.getPositionY())))){
					mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
					
					System.out.println(pathList.get(listCount).yCoord+ " "+pathList.get(listCount).xCoord );
					row = pathList.get(listCount).xCoord;
					col = pathList.get(listCount).yCoord;
					drone.setPositionX(row);
					drone.setPositionY(col);
					
					mapArray[col][row].add(drone.getDroneShape());
					listCount++;
				}
				arrest(drone); //execute arrest
				drone.setHasCriminal(true);//execute recall
			}
			else if(drone.getOrder() == 2){
				//If a criminal is captured as part of the previous order, then return to base without intercepting other criminals, resume standard or modified patrol once reached.
				int listCount = 0;
				pathList = pathfinder(row, col, 3, 3);
				
				//pathList and while loop is same in function as above, only routing back to home base.
				while((!((drone.getPositionX() == 3) && (drone.getPositionY() == 3)))){
					System.out.println(pathList.get(listCount).yCoord+" "+pathList.get(listCount).xCoord);
					
					mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
					
					row = pathList.get(listCount).xCoord;
					col = pathList.get(listCount).yCoord;
					drone.setPositionX(row);
					drone.setPositionY(col);
					
					mapArray[col][row].add(drone.getDroneShape());
					listCount++;
					
				}
				drone.setHasCriminal(false); //"Release" criminal at "central command"				
				drone.setOrder(0); //Resume patrol
			}
			}
		}});
		mainTimer.start(); //Start timer
	}
	
	//Detection method, gets a drone's current location and checks a 3x3 square centered around the drone. If a criminal is spotted, it's location is recorded.
	//If there are multiple entities, then it will priorities based on a "detected-first" basis, and only go for that one.
	//Sets drone orders to interception.
	//Detection radius is fixed like this because of map size. This is idealy scalable according to map size, but has the current size for this simulation.
	public void detect(Drone drone){
				int startPosX = (drone.getPositionX()-1 < 0) ?drone.getPositionX():drone.getPositionX()-1;
				int startPosY = (drone.getPositionY()-1 < 0) ?drone.getPositionY():drone.getPositionY()-1;
				int endPosX = (drone.getPositionX()+1 >11) ? drone.getPositionX():drone.getPositionX()+1;
				int endPosY = (drone.getPositionY()+1 >11) ? drone.getPositionY():drone.getPositionY()+1;
				
				for(int y = startPosY;y<=endPosY;y++){
					for(int x = startPosX;x<=endPosX;x++){
						for(Criminal crim : criminalList){
							if((crim.getPositionY() == y && crim.getPositionX() == x) && crim.getTaggedBy() != drone.getDroneDesignation() && crim.getTaggedBy() == -1){
								System.out.println();
								System.out.println("! " + crim.getPositionY() + " " + crim.getPositionX());
								crim.setTaggedBy(drone.getDroneDesignation());
								
								System.out.println(drone.getPositionY() + " " + drone.getPositionX());
								drone.setOrder(1);
								break;
							}
						}
					}
				}
		/*end of detect and arrest order*/
	}
	
	/*"Arresting protocol. Iterates through criminalList to see if one of their coordinates matches the current location of a drone, and if so, deletes.
	 * Should also take "crime-coefficient" from criminal and append to sectorList, which contains a total value of these and a associated point.
	 * 
	 * Because of ConcurrentModificationException (I.E. using the same "thread" to add(because of CriminalEntity() a
	 * random criminal every five seconds, usage of an iterator manage the thread to allow removing here has been used.
	 * 
	 * Code taken and adapted from:
	 * http://stackoverflow.com/questions/223918/iterating-through-a-list-avoiding-concurrentmodificationexception-when-removing
	 * Error definitation taken from: https://docs.oracle.com/javase/7/docs/api/java/util/ConcurrentModificationException.html
	 */
	
	public void arrest(Drone drone){
		for(Iterator<Criminal> iterator = criminalList.iterator(); iterator.hasNext();){
			Criminal delCrim = iterator.next();
			if(mapArray[drone.getPositionY()][drone.getPositionX()] == mapArray[delCrim.getPositionY()][delCrim.getPositionX()] && drone.getDroneDesignation() == delCrim.getTaggedBy()){
				
				//If statements here to which location get crime coefficent to add.
				if((delCrim.getPositionX() < 6 && delCrim.getPositionY() < 6) && drone.getDroneDesignation() == delCrim.getTaggedBy()){
					crimeCo[0] = delCrim.getCrimeCoefficient() + crimeCo[0];
				}
				else if((delCrim.getPositionX() > 5 && delCrim.getPositionY() > 5) && drone.getDroneDesignation() == delCrim.getTaggedBy()){
					crimeCo[3] = delCrim.getCrimeCoefficient() + crimeCo[3];
				}
				else if((delCrim.getPositionX() > 5 && delCrim.getPositionY() < 6) && drone.getDroneDesignation() == delCrim.getTaggedBy()){
					crimeCo[1] = delCrim.getCrimeCoefficient() + crimeCo[1];
				}
				else if((delCrim.getPositionX() < 6 && delCrim.getPositionY() > 5) && drone.getDroneDesignation() == delCrim.getTaggedBy()){
					crimeCo[2] = delCrim.getCrimeCoefficient() + crimeCo[2];
				}
				
				iterator.remove();
				mapArray[delCrim.getPositionY()][delCrim.getPositionX()].remove(delCrim.getCriminalShape());
				getContentPane().remove(delCrim);
				for(int i = 0; i < 4; i++){
					System.out.println("Sector" + i+": "+crimeCo[i]);
				}
			}
		}
	}
	
	/*Using A* Algorithmn to find shortest path to criminal, since detection radius and conditions may be changed, rather than a simpler
	 * implementation. Applied Manhattan distance heuristic given than grid and movement is non-diagonal, and it is an admissable heuristic
	 * (never overestimates distance).
	 * 
	 * All tile cost and asociated calulations have a multiplier of 10 (Eg. moving to tile in next space is 10, not 1)
	 * 
	 * Code and concepts, borrowed and adapted from the following references:
	 * http://software-talk.org/blog/2012/01/a-star-java/
	 * http://www.thehelper.net/threads/c-java-what-are-the-requirements-for-implementing-an-a-star-algorithm.161267/
	 * http://www.cokeandcode.com/main/tutorials/path-finding/
	 * http://www.policyalmanac.org/games/aStarTutorial.htm
	 * http://stackoverflow.com/questions/5601889/unable-to-implement-a-star-in-java
	 */
	public List<Node> pathfinder(int startX, int startY, int goalX, int goalY){
		Set<Node> openList = new HashSet<Node>(); //List for nodes to analyse
		Set<Node> closedList = new HashSet<Node>(); //Nodes already visited
		Set<Node> adjList = new HashSet<Node>(); //Nodes adjacent to the current position
		
		//Set respective coordinates as nodes to manipulate.
		Node start = nodes[startX][startY];
		Node goal = nodes[goalX][goalY];
		
		start.gCost = 0; //Cost of moving to square
		start.hCost = manhattanDistance(start, goal); //Cost using manhattan distance heuristic
		start.fCost = start.hCost; //Currently set to hCost, but usually added with gCost.
		
		openList.add(start); //Add start node to openList
		
		while(true){
			Node current = null; //No current
			
			//No path found/Nothing to analyse
			if(openList.size()==0){
				break;
			}
			
			//If a node in the openList is not a wall, and either null or greater than a node's total heuristic cost to the goal, make it the current node, also check if passable terrain
			for(Node node: openList){
				if(current == null || (node.fCost < current.fCost) && (mapArrayPosition[node.xCoord][node.yCoord] == 0)){
					current = node;
				}
			}
			
			//Path already found, stop pathfinding and return the path to goal
			if(current == goal){
				break;
			}
			
			//Remove from openList and add to closedList the current node, so it is not checked again in the iteration
			openList.remove(current);
			closedList.add(current);
			
			//For directly adjacent nodes (tiles), add them to adjList for further processing in the algorithmn.
			//Below is a series of multiconditional checks to see if the current location is at the "edge" of the map, set appropriate value if is/is not.
			//Code adapted and referenced from here: http://stackoverflow.com/questions/22747109/avoid-out-of-bounds-exception-in-2d-array 
			///Concept of conditional operators from here: http://www.tutorialspoint.com/java/java_basic_operators.htm
			int startPosX = (current.xCoord-1 < 0) ?current.xCoord:current.xCoord-1;
			int startPosY = (current.yCoord-1 < 0) ?current.yCoord:current.yCoord-1;
			int endPosX = (current.xCoord+1 >11) ? current.xCoord:current.xCoord+1;
			int endPosY = (current.yCoord+1 >11) ? current.yCoord:current.yCoord+1;
			
			//Add all squares directly adjacent to drone. Diagonals ignored because such movement is not desired.
				adjList.add(nodes[current.xCoord][startPosY]);
				adjList.add(nodes[current.xCoord][endPosY]);
				adjList.add(nodes[startPosX][current.yCoord]);
				adjList.add(nodes[endPosX][current.yCoord]);
			
			//System.out.println(adjList);
			
			//For adjacent nodes
			for(Node neighbor: adjList){
				if(neighbor == null){
					continue;
				} //Ignore and continue iterating if neighbor is null
				
				int nextG = current.gCost + neighbor.gCost; //Get cost to next square
				
				//If the cost is less than a neighbor's cost, remove from both lists, check if terrain in valid to pass through
				if(nextG < neighbor.gCost && mapArrayPosition[neighbor.yCoord][neighbor.xCoord] == 0){
					openList.remove(neighbor);
					closedList.remove(neighbor);
				}
				
				//If in neither, set costs and add to openList, check if terrain in valid to pass through
				if(!openList.contains(neighbor) && !closedList.contains(neighbor) && mapArrayPosition[neighbor.yCoord][neighbor.xCoord] == 0){
					neighbor.gCost = nextG;
					neighbor.hCost = manhattanDistance(neighbor, goal);
					neighbor.fCost = neighbor.gCost + neighbor.hCost;
					neighbor.parent = current;
					openList.add(neighbor);
				}
			}
		}
		
		//Create a new node returnList, and trace the path back to start using the parents of each node from the goal.
		//Changed to checking for if the path is at the start point, rather than if parent is null, since that causes memory leaks.
		List<Node> returnList = new ArrayList<Node>();
		Node current = goal; 
		while(current != nodes[startX][startY]){
			returnList.add(current);
			current = current.parent;
		}
		returnList.add(start);
		
		Collections.reverse(returnList); //Reverse the order so the path is from drone to designated position
		
		return returnList;
	}
	
	//Manhattan distance, calculation code takne from: http://stackoverflow.com/questions/8224470/calculating-manhattan-distance
	//Takes both vertical (y) and horizontal (x) coordinates from two nodes.
	public int manhattanDistance(Node node1, Node node2){
		return Math.abs(node1.xCoord - node2.xCoord) + Math.abs(node1.yCoord - node2.yCoord);
	}
	
	/*Menu bar used for activating methods to operate simulation. GUI code for menu bar taken from:
	 * http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/
	 */
	private void menuBar() {
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
				System.out.println("Closing program...");
				System.exit(0);
			}
		});
		
		file.add(resetMenuItem);
		file.add(exitMenuItem);
		menu.add(file);

		//Simulation SubItems
		//SubItem "Generate" - Resets program to tabula rasa
		final JMenuItem genMenuItem = new JMenuItem("Generate");
		genMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(gridCheck != true){	
					grid();
					genMenuItem.setEnabled(false);
				}
			}
		});
		
		//SubItem "Execute" - Terminates Program
		final JMenuItem execMenuItem = new JMenuItem("Execute");
		execMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(executeCheck != true){
					execute(drones);
					execMenuItem.setEnabled(false);
					executeCheck = true;
				}
			}
		});
		
		enviro.add(genMenuItem);
		enviro.add(execMenuItem);
		menu.add(enviro);
		
		//Drones SubItems
		//SubItem "Add Drone" - Adds a single drone to grid
		JMenuItem addDroneMenuItem = new JMenuItem("Add Drone");
		addDroneMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					droneEntity();
				};
		});
		
		//SubItem "Remove Drone" - Removes drone at droneList index zero from grid
		JMenuItem removeDroneMenuItem = new JMenuItem("Remove Drone");
		removeDroneMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				removeDrone(drone);
			}
		});
		
		drones.add(addDroneMenuItem);
		drones.add(removeDroneMenuItem);
		menu.add(drones);
		
		//Criminal SubItems
		//SubItem "Add Criminal" - Adds a criminal to the grid
		JMenuItem addCrimMenuItem = new JMenuItem("Add Criminal");
		addCrimMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				criminalEntity();
			}
		});
		
		//SubItem "Remove Criminal" - Removes criminal at criminalList index zero from grid
		JMenuItem removeCrimMenuItem = new JMenuItem("Remove Criminal");
		removeCrimMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				removeCriminal(criminal);
			}
		});
		
		criminals.add(addCrimMenuItem);
		criminals.add(removeCrimMenuItem);
		menu.add(criminals);
		
		//Central Command SubItem
		//SubItem " Add Central Command - Adds Central Command to the grid
		JMenuItem addCCommandMenuItem = new JMenuItem("Add Central Command");
		addCCommandMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cCommandEntity();
			}
		});
		
		cCommand.add(addCCommandMenuItem);
		menu.add(cCommand);
		
		setJMenuBar(menu);
	}
}
