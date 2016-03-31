import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
//import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private Random randCrim = new Random();
	
	public ArrayList<Drone> droneList = new ArrayList<Drone>();
	public ArrayList<Criminal> criminalList = new ArrayList<Criminal>();
	
	int droneID = 0;
	
	private JPanel panel;
	
	private Node[][] nodes;
	
	CentralCommand cCommand;
	Drone drone;
	Criminal criminal;
	
	public Coordinate droneCoords = new Coordinate();
	public Coordinate crimCoords = new Coordinate();
	
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
		if(cCommandCheck==true){
			drone = new Drone();
			drone.SetDetectionRadius(3);
			droneList.add(drone);
			System.out.println(droneList.size());
			drone.setOrder(0);
			//In reverse order on 2D Array I.e. [y][x], sets spawn to CCommand's position
			drone.setPositionX(cCommand.getPositionX());
			drone.setPositionY(cCommand.getPositionY());
			
			drone.setDroneDesignation(droneID++);
			
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
		else{
			System.out.println("No");
		}
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
		criminal.setTaggedBy(-1);
		
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
		
		//For adding specific components to a gridLayout cell (The "tiles" on the "grid", refused multiple times, but first appearing here.
		//Reference: http://stackoverflow.com/questions/2510159/can-i-add-a-component-to-a-specific-grid-cell-when-a-gridlayout-is-used
		
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
		
		//New timer with actionListener to execute methods
		final Timer timer = new Timer(1000, new ActionListener(){
		
			//Execute method and refresh gui for every one second on the time (1000ms)
			public void actionPerformed(ActionEvent movement){	
				move(drone);
				revalidate();
				repaint();
			}
			
			public void move(Component obj){
			for(Drone drone: droneList){	
				int row = drone.getPositionX();
				int col = drone.getPositionY();
				List<Node> pathList;
				Criminal taggedCrim = new Criminal();
				
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
				//End of arresting protocol
			
				
			//If drone currently has criminal arrested, 
			if(drone.getHasCriminal() == true){
				drone.setOrder(2);
			}
				
			if(drone.getOrder() == 0){
				/*Loose patrol method
				 * Moves the drone around in a random, erratic order, default setting for when orders 1 (detect and arrest), and 2 (move to "more dangerous" sector point)
				 */
				boolean moved = false;
					do{
						int direction = (int) (Math.round(Math.random() * 3));
						int nextRow = row;
						int nextCol = col;
						
						mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
						//mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone);
						
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
		                    //mapArray[nextCol][nextRow].add(drone);
		                    mapArray[col][row].add(drone.getDroneShape());
		                    }
	                    else{
	                    	System.out.println("Almost went out of bounds");
	                    }
	                    detect(drone);
					}while(!moved);
			}
			else if(drone.getOrder() == 1){
				//Execute path searching to the first-detected criminal, resume standard if absolutely no more criminals in detection radius
				//Sets to return to home base, will always have "arrested" at the end of this order.
				int listCount = 0;
				//boolean arrived = false;

				for(Criminal crim: criminalList){
					if(drone.getDroneDesignation() == crim.getTaggedBy()){
						taggedCrim = crim;
						break;
					}
				}
				
				pathList = pathfinder(drone.getPositionX(), drone.getPositionY(), taggedCrim.getPositionX(), taggedCrim.getPositionY());
				
				while((!((drone.getPositionX() == taggedCrim.getPositionX()) && (drone.getPositionY() == taggedCrim.getPositionY())))){
					mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
					
					System.out.println(pathList.get(listCount).xCoord+ " "+pathList.get(listCount).yCoord );
					drone.setPositionX(pathList.get(listCount).xCoord);
					drone.setPositionY(pathList.get(listCount).yCoord);
					
					row = pathList.get(listCount).xCoord;
					col = pathList.get(listCount).yCoord;
					drone.setPositionX(pathList.get(listCount).xCoord);
					drone.setPositionY(pathList.get(listCount).yCoord);
					
					mapArray[col][row].add(drone.getDroneShape());
					listCount++;
				}
				arrest(drone);
				drone.setOrder(2);
				//drone.setHasCriminal(true);
			}
			else if(drone.getOrder() == 2){
				//If a criminal is captured as part of the previous order, then return to base without intercepting other criminals, resume standard or modified patrol once reached.
				int listCount = 0;
				pathList = pathfinder(drone.getPositionX(), drone.getPositionY(), 3, 3);
				
				while((!((drone.getPositionX() == 3) && (drone.getPositionY() == 3)))){
					System.out.println(pathList.get(listCount).xCoord+" "+pathList.get(listCount).yCoord);
					
					mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
					
					System.out.println(pathList.get(listCount).xCoord+ " "+pathList.get(listCount).yCoord );
					drone.setPositionX(pathList.get(listCount).xCoord);
					drone.setPositionY(pathList.get(listCount).yCoord);
					
					row = pathList.get(listCount).xCoord;
					col = pathList.get(listCount).yCoord;
					drone.setPositionX(pathList.get(listCount).xCoord);
					drone.setPositionY(pathList.get(listCount).yCoord);
					
					mapArray[col][row].add(drone.getDroneShape());
					listCount++;
					
				}
				//drone.setHasCriminal(false);
				
				//mapArray[drone.getPositionY()][drone.getPositionX()].remove(drone.getDroneShape());
				//mapArray[col][row].add(drone.getDroneShape());
				
				drone.setOrder(0);
			}
			}
		}});
		timer.start();
	}
	
	//Detection method, gets a drone's current location and checks a 5x5 square centered around the drone. If a criminal is spotted, it's location is recorded.
	//If there are multiple entities, then it will priorities based on a "detected-first" basis, and only go for that one.
	//Sets drone orders to interception.
	public void detect(Drone drone){
			for(Criminal crim : criminalList){
				int startPosX = (drone.getPositionX()-1 < 0) ?drone.getPositionX():drone.getPositionX()-1;
				int startPosY = (drone.getPositionY()-1 < 0) ?drone.getPositionY():drone.getPositionY()-1;
				int endPosX = (drone.getPositionX()+1 >11) ? drone.getPositionX():drone.getPositionX()+1;
				int endPosY = (drone.getPositionY()+1 >11) ? drone.getPositionY():drone.getPositionY()+1;
				
				for(int y = startPosY;y<=endPosY;y++){
					for(int x = startPosX;x<=endPosX;x++){
						if((crim.getPositionY() == y && crim.getPositionX() == x)){
							System.out.println();
							System.out.println("! " + crim.getPositionY() + " " + crim.getPositionX());
							crim.setTaggedBy(drone.getDroneDesignation());
							
							System.out.println(drone.getPositionY() + " " + drone.getPositionX());
							drone.setOrder(1);

						}
					}
				}
			}
		/*end of detect and arrest order*/
	}
	
	public void arrest(Drone drone){
		for(Iterator<Criminal> iterator = criminalList.iterator(); iterator.hasNext();){
			Criminal delCrim = iterator.next();
			if(mapArray[drone.getPositionY()][drone.getPositionX()] == mapArray[delCrim.getPositionY()][delCrim.getPositionX()] && drone.getDroneDesignation() == delCrim.getTaggedBy()){
				iterator.remove();
				mapArray[delCrim.getPositionY()][delCrim.getPositionX()].remove(delCrim.getCriminalShape());
				getContentPane().remove(delCrim);
			}
		}
	}
	
	/*Using A* Algorithmn to find shortest path to criminal, since detection radius and conditions may be changed, rather than a simpler
	 * implementation. Applied Manhattan distance heuristic given than grid and movement is non-diagonal, and it is an admissable heuristic
	 * (never overestimates distance).
	 * 
	 * All tile cost and asociated calulations have a multiplier of 10 (Eg. moving to tile in next space is 10, not 1)
	 */
	public List<Node> pathfinder(int startX, int startY, int goalX, int goalY){
		Set<Node> openList = new HashSet<Node>(); //List for nodes to analyse
		Set<Node> closedList = new HashSet<Node>(); //Nodes already visited
		Set<Node> adjList = new HashSet<Node>(); //Nodes adjacent to the current position
		
		Node start = nodes[startX][startY];
		Node goal = nodes[goalX][goalY];
		
		start.gCost = 0; //Cost of moving to square
		start.hCost = manhattanDistance(start, goal); //Cost using manhattan distance heuristic
		start.fCost = start.hCost; //Currently set to hCost, but usually added with gCost.
		
		openList.add(start); //Add start node to openList
		
		while(true){
			Node current = null;
			
			//No path found/Nothing to analyse
			if(openList.size()==0){
				break;
			}
			
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
			
			//For a 3x3 square centered around the current drone, return all valid, in-bounds nodes, excluding the central coordinate
			int startPosX = (current.xCoord-1 < 0) ?current.xCoord:current.xCoord-1;
			int startPosY = (current.yCoord-1 < 0) ?current.yCoord:current.yCoord-1;
			int endPosX = (current.xCoord+1 >11) ? current.xCoord:current.xCoord+1;
			int endPosY = (current.yCoord+1 >11) ? current.yCoord:current.yCoord+1;
			
			for(int y = startPosY;y<=endPosY;y++){
				for(int x = startPosX;x<=endPosX;x++){
					
					if((closedList.contains(nodes[x][y])!=true)){
						adjList.add(nodes[x][y]);
					}
				}
			}
			
			/*
			for(Iterator<Node> iterator = adjList.iterator(); iterator.hasNext();){
				Node adjFilter = iterator.next();
				
				if(mapArrayPosition[adjFilter.xCoord][adjFilter.yCoord] == 1){
					iterator.remove();
					adjList.remove(adjFilter);
				}
			}*/
			
			//System.out.println(adjList);
			
			for(Node neighbor: adjList){
				if(neighbor == null){
					continue;
				}
				
				int nextG = current.gCost + neighbor.gCost;
				
				if(nextG < neighbor.gCost){
					openList.remove(neighbor);
					closedList.remove(neighbor);
				}
				
				if(!openList.contains(neighbor) && !closedList.contains(neighbor)){
					neighbor.gCost = nextG;
					neighbor.hCost = manhattanDistance(neighbor, goal);
					neighbor.fCost = neighbor.gCost + neighbor.hCost;
					neighbor.parent = current;
					openList.add(neighbor);
				}
			}
		}
		
		//Create a new node returnList, and trace the path back to start using the parents of each node from the goal.
		List<Node> returnList = new ArrayList<Node>();
		Node current = goal; 
		while(current != nodes[startX][startY]){
			returnList.add(current);
			current = current.parent;
		}
		returnList.add(start);
		
		/*for(int i = 0; i < returnList.size();i++){
			System.out.println(returnList.get(i).yCoord +" "+ returnList.get(i).xCoord);
		}*/
		
		Collections.reverse(returnList); //Reverse the order so the path is from drone to designated position
		
		return returnList;
	}
	
	//Manhattan distance, calculation code takne from: http://stackoverflow.com/questions/8224470/calculating-manhattan-distance
	public int manhattanDistance(Node node1, Node node2){
		return Math.abs(node1.xCoord - node2.xCoord) + Math.abs(node1.yCoord - node2.yCoord);
	}
	
	/*Menu bar used for activating methods to operate simulation. GUI code for menu bar taken from:
	 * http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/
	 */
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
