public class Tile {
	
	Tile N, W;			// Northern and western neighbors
	
	double xn, xw;		// Edge weight for northern and western neighbor tile
	
	int x, y;			// The bottom left point on the tile
	
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		
		xn = 0;
		xw = 0;
		
		N = null;
		W = null;
	}


	public Tile getN() {
		return N;
	}


	public void setN(Tile n) {
		N = n;
	}


	public Tile getW() {
		return W;
	}


	public void setW(Tile w) {
		W = w;
	}


	public double getXn() {
		return xn;
	}


	public void setXn(double xn) {
		this.xn = xn;
	}


	public double getXw() {
		return xw;
	}


	public void setXw(double xw) {
		this.xw = xw;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
}