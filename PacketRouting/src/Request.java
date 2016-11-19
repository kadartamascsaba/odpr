public class Request {
	
	private int source, destination;
	
	public Request(int source, int destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public int distance() {
		return Math.abs(source - destination);
	}
}