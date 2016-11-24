import java.lang.Math;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class PacketRouting {

	int n;				// Vertex number
	int B, C;    		// Buffer and link capacity
	int b, c;    		// Virtual buffer and link capacity	
	int lh, lv;			// Tile horizontal and vertical length
	int pmax;			// Maximum path length
	
	int totalRequestNumber;		// Number of request in the algorithm
	int deliveredRequestNumber; // Number of delivered requests
	
	
	PriorityQueue<Request> request; // Holding incoming request in a time slot
	
	SpaceTimeGrid stg;
	ArrayList<Tiling> tilings;
	
	// Initial constructor
	PacketRouting(int vertexNumber, int bufferCap, int linkCap) {
		
		initializeParameters(vertexNumber, bufferCap, linkCap);
		initializeTilings();
		initializeSpaceTimeGrid();
		
		//  Priority queue for holding incoming requests in a time slot
		// sorted by source destination distance
		request = new PriorityQueue<Request>(250, new RequestComparator());
				
		writeOutConfiguration();
	}
	
	// Adds request to the list, sorted by source-destination distance
	public void addRequest(Request req) {
		request.add(req);
	}
	
	public void addRequest(int source, int destination, int time) {
		request.add(new Request(source, destination, time));
	}
	
	public void loop() {
		// A bunch of new requests arrive
		filter();
		
		ArrayList<Integer> init, sketch;
		
		init   = new ArrayList<Integer>();
		sketch = new ArrayList<Integer>();
		
		for(Request req: request) {
			if(req.distance() <= lv) {
				if(routeNear(req)) {
					deliveredRequestNumber++;
				}
			}
			else {
				init = initRoute(req);	// Reject request if init is empty
				sketch = integralPathPacking(req); // If return value is empty then request rejected
				System.out.println("Far request -> Using IPP and INIT algorithm");
			}
		}
	}
	
	// Packet routing for NEAR type requests
	private boolean routeNear(Request req) {
		
		int j = req.getTime() - req.getSource();
		
		boolean congestion = false;
		
		ArrayList<Vertex> tmp = new ArrayList<Vertex>();
		
		// Check if the package can go right to the destination without hold
		for(int i=req.getSource(); !congestion && i<req.getDestination(); i++) {
			tmp.add(stg.getVertex(i, j));
			if(stg.getVertex(i, j).getNearC() == c) {
				congestion = true;
			}
		}
		
		// If there was no congestion then update vertexes
		if(!congestion) {
			for(Vertex v : tmp) {
				v.incNearC();
			}
			return true;
		}
		
		return false;
	}
	
	// Initial packet routing for FAR type requests
	// Returns an array of integers
	//  0 means hold (go EAST)
	//  1 means send (go NORTH)
	// If the array is empty there is congestion and the packet cannot be sent
	private ArrayList<Integer> initRoute(Request req) {
		
		int tilingNumber, j;
		boolean congestion;
		Tile tmp;

		ArrayList<Integer> path;
		
		j = req.getTime() - req.getSource();
		tilingNumber = getTilingNumber(req);
	
		congestion = false;
		
		tmp = tilings.get(tilingNumber).getSWTile(req);
		path = new ArrayList<Integer>();
		
		// Trying to route in a straight line to N
		for(int i=req.getSource(); i<tmp.getX()+lv/2; i++) {
			path.add(1);
			if(stg.getVertex(i, j).getFarC(tilingNumber) == c) {
				congestion = true;
				path.clear();
				break;
			}
		}
		
		// If not succeeded try to go EAST
		if(congestion) {
			for(int i=j; i<tmp.getY()+lh/2; i++) {
				path.add(0);
				if(stg.getVertex(req.getSource(), i).getFarB(tilingNumber) == b) {
					congestion = true;
					path.clear();
					break;
				}
			}
		}
		
		return path;
	}
	
	// Integral path routing for FAR type requests
	// Returns an array of integers
	// 0 means hold (go EAST)
	// 1 means send (go NORTH)
	// If the array is empty then request is rejected
	private ArrayList<Integer> integralPathPacking(Request req) {
		int source, destination, tilingNumber;
		Tiling tmp;
		
		source = req.getTime() - req.getSource();
		destination = req.getDestination();
		
		tilingNumber = getTilingNumber(req);
	
		tmp = tilings.get(tilingNumber);
		
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		getIPPpath(tmp.getTileIndex(source), tmp.getTileIndex(destination), tilingNumber, path);
		
		if (path.size() > pmax) {
			return new ArrayList<Integer>();
		}
		
		return path;
	}
	
	private void getIPPpath(int start, int dest, int tilingNumber, ArrayList<Integer> path) {
		
		if (start != dest) {
			if (path.size() > pmax) {
				
			}
			else {
				
			}
		}
		
	}
	
	// Initialize parameters
	private void initializeParameters(int n, int B, int C) {

		this.n	= n;
		
		// Checking if buffer capacity is in range
		if(B < 5) {
			System.out.println("Buffer capacity is less than 5!");
			System.out.println("Using default value 5");
			this.B = 5;
		}
		else if(B > Math.log(n)){
			System.out.println("Buffer capacity is greater than log(N)!");
			System.out.println("Using default value 5");
			this.B = 5;
		}
		else {
			this.B = B;
		}

		// Checking if buffer capacity is in range
		if(C < 5) {
			System.out.println("Link capacity is less than 5!");
			System.out.println("Using default value 5");
			this.C = 5;
		}
		else if(C > Math.log(n)){
			System.out.println("Link capacity is greater than log(N)!");
			System.out.println("Using default value 5");
			this.C = 5;
		}
		else {
			this.C = C;
		}
		

		// Set virtual track capacities
		b =	B / 5;
		c =	C / 5;
		
		// Calculate maximum path length
		pmax = 2*n*(1 + B/C);
		
		// Calculate tile horizontal and vertical length
		double tmp = Math.log(1 + 3*pmax);
		lh = (int) (6*tmp)/(5*c);
		lv = (int) (6*tmp)/(5*b);
		
		// Initialize request related parameters
		totalRequestNumber = 0;
		deliveredRequestNumber = 0;
	}
	
	// Initialize tilings
	// We use 4 types of tilings in the algorithm
	private void initializeTilings() {
		tilings = new ArrayList<Tiling>();
		tilings.add(new Tiling(n, lv, lh, -lv/2, -lh/2));
		tilings.add(new Tiling(n, lv, lh, -lv/2,     0));
		tilings.add(new Tiling(n, lv, lh,     0, -lh/2));
		tilings.add(new Tiling(n, lv, lh,     0,     0));
		
		
		for(int i=0; i<(pmax-n)/lh+2; i++) {
			for(int j=0; j<tilings.size(); j++) {
				tilings.get(j).addColumn();
			}
		}						
	}
	
	// Initialize space-time grid
	//  The space-time grid is an acyclic infinite graph, so we use only a part of it
	// and after each time sloth we slide the whole graph
	private void initializeSpaceTimeGrid() {
		
		int minColumn = tilings.get(0).getMostLeftColumn();
		int maxColumn = tilings.get(0).getMostRightColumn();
		
		for(int i=1; i<tilings.size(); i++) {
			minColumn = Math.min(minColumn, tilings.get(i).getMostLeftColumn());
			maxColumn = Math.max(maxColumn, tilings.get(i).getMostRightColumn());
		}		
		
		stg = new SpaceTimeGrid(n, b, c, minColumn, maxColumn);
		
	}
	
	// Returns the tiling number in which the req is found in the SW quadrant of a tile s
	// It is guaranteed that it is found in one of the tilings (only one)
	private int getTilingNumber(Request req) {
		
		for(int i=0; i<tilings.size(); i++) {
			if(tilings.get(i).getSWTile(req) != null) {
				return i;
			}
		}
		
		return -1;
	}
	
	// Returns the tile in which the req is found in the SW quadrant of a tile s
	// It is guaranteed that it is found in one of the tilings (only one)	
	private Tile getTile(Request req) {
		Tile tmp = null;
		
		for(int i=0; i<tilings.size(); i++) {
			tmp = tilings.get(i).getSWTile(req);
			if(tmp != null) {
				return tmp;
			}
		}		
		return tmp;
	}

	// Write out configuration of the algorithm
	// For DEBUGGING
	private void writeOutConfiguration() {
		System.out.println("Packet routing configuration:");
		System.out.println(" - vertex number: "   + n);
		System.out.println(" - buffer capacity: " + B);
		System.out.println(" - link capacity: "   + C);
		System.out.println(" - tile vertical length: "   + lv);
		System.out.println(" - tile horizontal length: " + lh);
		System.out.println(" - maximum path length: " + pmax);
		System.out.println(" - tiling length: " + tilings.get(0).getTilingWidth());
		System.out.println(" - tiling height: " + tilings.get(0).getTilingHeight());
	}

	// Filter request, containing only the first b+c for each vertex
	private void filter() {

		// List for checking if source capacity is overloaded
		int tmp[] = new int[n];
		for(int i=0; i<n; tmp[i++]=0);
		
		// A temporary list that contains the filtered request
		ArrayList<Request> filtered = new ArrayList<Request>();
		
		// Go through all the requests
		// If capacity is overloaded then drop the request, else keep it 
		while(!request.isEmpty()) {
			if(tmp[request.peek().getSource()] < b+c) {
				filtered.add(request.poll());
			}
			else {
				request.poll();
			}
		}
		
		// Load request from filtered list into request priority queue
		for(Request req : filtered) {
			request.add(req);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////	
	public static void main(String[] args) {
		
		PacketRouting tmp = new PacketRouting(1000, 5, 6);
		System.out.println("Test");
	}
}