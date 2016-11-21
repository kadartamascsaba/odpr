import java.lang.Math;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class PacketRouting {

	int n;				// Vertex number
	int B, C;    		// Buffer and link capacity
	int b, c;    		// Virtual buffer and link capacity	
	int lh, lv;			// Tile horizontal and vertical length
	int pmax;			// Maximum path length
	
	PriorityQueue<Request> request;
	ArrayList<Vertex> vertex;
	ArrayList<Request> accepted;
	
	ArrayList<Tiling> tilings;
	
	// Initial constructor
	PacketRouting(int vertexNumber, int bufferCap, int linkCap) {
		
		// Checking if buffer capacity is in range
		if(bufferCap < 5) {
			System.out.println("Buffer capacity is less than 5!");
			System.out.println("Using default value 5");
			B = 5;
		}
		else {
			B = bufferCap;
		}

		// Checking if link capacity is in range
		if(linkCap < 5) {
			System.out.println("Link capacity is less than 5!");
			System.out.println("Using default value 5");
			C = 5;
		}	
		else {
			C = linkCap;
		}
		
		n	=	vertexNumber;
		b	=	B / 5;
		c	=	C / 5;
		
		pmax = 2*n*(1 + B/C);
		
		double tmp = Math.log(1 + 3*pmax);
		
		lh = (int) (6*tmp)/(5*c);
		lv = (int) (6*tmp)/(5*b);
		
		
		vertex  = new ArrayList<Vertex>();
		for(int i=0; i<n; i++) {
			vertex.add(new Vertex());
		}
		request = new PriorityQueue<Request>(100, new RequestComparator());
		
		tilings = new ArrayList<Tiling>();
		
		tilings.add(new Tiling(n, lv, lh, -lv/2, -lh/2));
		tilings.add(new Tiling(n, lv, lh, -lv/2,     0));
		tilings.add(new Tiling(n, lv, lh,     0, -lh/2));
		tilings.add(new Tiling(n, lv, lh,     0,     0));
		
		
		for(int i=0; i<pmax/lh+50; i++) {
			for(int j=0; j<tilings.size(); j++) {
				tilings.get(j).addColumn();
			}
		}
		
		
		for(Tiling t : tilings) {
			System.out.println(t.s.getX() + " - " + t.s.getY());
		}
		
		
		
		for(int i=0; i<tilings.size(); i++) {
			if(tilings.get(i).getSWTile(new Request(3, 100, 3)) != null) {
				System.out.println("FOUND IN " + i);
			}
		}
		
		writeOutConfiguration();
	}
	
	// Write out configuration of the algorithm
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
	
	// Adds request to the list, sorted by source-destination distance
	public void addRequest(Request req) {
		request.add(req);
	}
	public void addRequest(int source, int destination, int time) {
		request.add(new Request(source, destination, time));
	}
	
	// Filter request, containing only the first b+c for each vertex
	public void filter() {
		// List for checking if source capacity is overloaded
		int tmp[] = new int[n];
		for(int i=0; i<n; tmp[i++]=0);
		
		// A temporary list that contains the filtered request
		ArrayList<Request> filtered = new ArrayList<Request>();
		
		// Go through all the requests
		// If capacity is overloaded then drop the request, else keep it 
		while(!request.isEmpty()) {
			if(tmp[request.peek().getSource()] <= b+c) {
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
	
	public void loop() {
		// A bunch of new requests arrive
		
		filter();
		requestMigration();
		
		for(Request req: request) {
			if(req.distance() <= lv) {
				routeNear(req);
			}
			else {
				System.out.println("Far request -> Using IPP and INIT algorithm");
			}
		}
	}
	
	public void requestMigration() {
		for(int i=0; i<accepted.size(); i++) {
			accepted.get(i).move();
		}
	}
	
	// Packet routing for NEAR type requests
	private boolean routeNear(Request req) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		for(int i=0; i<req.distance(); i++) {
			if(vertex.get(req.getSource()+i).getNearC() < c) {
				tmp.add(1);
			}
			else {
				// Couldn't route packet
				return false;
			}
		}
		
		for(int i=0; i<req.distance(); i++) {
			vertex.get(req.getSource()+i).incNearC();
		}
		
		req.setRoute(tmp);
		accepted.add(req);
		
		return true;
	}
	
	// Initial packet routing for FAR type requests
	
	
///////////////////////////////////////////////////////////////////////////	
	public static void main(String[] args) {
		
		PacketRouting tmp = new PacketRouting(500, 10, 10);
		System.out.println("Test");
	}
}