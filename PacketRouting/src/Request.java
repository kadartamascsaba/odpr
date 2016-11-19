import java.util.ArrayList;

public class Request {
	
	private int source, destination;	// Source and destination vertex
	private int current;				// Current vertex
	
	private boolean over;				// Indicates if the packet has reached its destination
	
	// Arrays containing the init, sketch and final route for the package
	private ArrayList<Integer> sketch, init, route;
	
	
	public Request(int source, int destination) {
		this.source = source;
		this.destination = destination;
		
		current = source;
		
		over = false;
		
		init   = new ArrayList<Integer>();
		route  = new ArrayList<Integer>();
		sketch = new ArrayList<Integer>();
	}
	
	public int distance() {
		return Math.abs(source - destination);
	}
	
	public int getSource() {
		return this.source;
	}
	
	public int getDestination() {
		return this.destination;
	}
	
	public void move() {
		int tmp = route.get(0);
		
		route.remove(0);
		
		if(tmp == 1) {
			current++;
			
			
			if(current == destination) {
				over = true;
			}
		}
		
	}

	public boolean isOver() {
		return over;
	}
	
	public void setInit(ArrayList<Integer> init) {
		this.init = init;
	}
	
	public void setSketch(ArrayList<Integer> sketch) {
		this.sketch = sketch;
	}
	
	public void setRoute(ArrayList<Integer> route) {
		this.route = route;
	}	

	public ArrayList<Integer> getInit() {
		return this.init;
	}
	
	public ArrayList<Integer> getSketch() {
		return this.sketch;
	}
	
	public ArrayList<Integer> getRoute() {
		return this.route;
	}

	
}