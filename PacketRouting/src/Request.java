import java.util.ArrayList;

public class Request {
	
	private int source, destination;	// Source and destination vertex
	private int current;				// Current vertex
	private int time;					// The time when the request arrived
	
	// Arrays containing the init, sketch and final route for the package
	private ArrayList<Integer> sketch, init, route;
	
	public Request(int source, int destination, int time) {
		this.source = source;
		this.destination = destination;
		
		this.time = time;
		
		current = source;
		
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
	
	public int getNextMove() {
		if(route != null) {
			return route.get(0);
		}
		
		return -1;
	}
	
	public void move() {
		int tmp = route.get(0);
		
		route.remove(0);
		
		if(tmp == 1) {
			current++;
		}
		
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
	
	public boolean hasArrived() {
		return current == destination;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return this.time;
	}
}