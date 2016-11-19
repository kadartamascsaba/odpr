import java.lang.Math;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class PacketRouting {

	int n;				// Vertex number
	int B, C, b, c;		// Buffer and link capacity		
	int lh, lv;			// Tile horizontal and vertical length
	int pmax;			// Maximum path length
	
	PriorityQueue<Request> request;
	
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
		
		
		request = new PriorityQueue<Request>(100, new RequestComparator());
		
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
	}
	
	// Adds request to the list, sorted by source-destination distance
	public void addRequest(Request req) {
		request.add(req);
	}
	public void addRequest(int source, int destination) {
		request.add(new Request(source, destination));
	}
	
	public void filter() {
		int tmp[] = new int[n];
		for(int i=0; i<n; tmp[i++]=0);
		
		
	}
	
	public static void main(String[] args) {
		
		PacketRouting tmp = new PacketRouting(5000, 10, 10);
		
		System.out.println("Test");

	}

}
