class Node{
	int xCoord;
	int yCoord;
	int gCost;
	int hCost;
	int fCost;
	Node parent;
	
	public Node(){
		
	}
	
	public Node(int xc, int yc){
		xCoord = xc;
		yCoord = yc;
		this.parent = null;
		
	}
}