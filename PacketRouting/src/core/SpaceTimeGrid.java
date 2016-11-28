package core;
import java.util.ArrayList;

public class SpaceTimeGrid {

	int n;              // Vertex number
	
	int firstColumn;    // Index of the first column
	
	int b, c;           // Buffer and link capacity for virtual tracks
	
	ArrayList<ArrayList<Vertex>> vertexes;
	
	public SpaceTimeGrid(int n, int b, int c, int firstColumn, int lastColumn) {
		this.n = n;
		this.b = b;
		this.c = c;
		
		this.firstColumn = firstColumn;
		
		vertexes = new ArrayList<ArrayList<Vertex>>();
		for(int i=firstColumn; i<lastColumn; i++) {
			vertexes.add(getNewColumn());
		}
	}
	
	public void slide() {
		vertexes.remove(0);
		vertexes.add(getNewColumn());
		firstColumn++;
	}
	
	public Vertex getVertex(int row, int column) {
		return vertexes.get(column - firstColumn).get(row);
	}
	
	private ArrayList<Vertex> getNewColumn() {
		ArrayList<Vertex> tmp = new ArrayList<Vertex>();
		for(int i=0; i<n; i++) {
			tmp.add(new Vertex());
		}
		
		return tmp;
	}
}