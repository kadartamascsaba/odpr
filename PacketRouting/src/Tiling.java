public class Tiling {

	int n;					// Vertex number
	int lv, lh;				// Tile length and width
	int offsetX, offsetY; 	// Offset parameters for Tiling j
	
	Tile s;					// The most bottom-left tile of tiling
	
	
	public Tiling(int vertexNumber, int lv, int lh, int offsetX, int offsetY) {
		
		this.n = vertexNumber;
		
		this.lv = lv;
		this.lh = lh;
		
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}	
	
	// Adds a new column of tiles to the tiling 
	public void addColumn() {
		Tile tmp;
		
		if(s == null) {
			s = new Tile(offsetX, offsetY);
			tmp = s;
			for(int i = 1; i<(n/lv)+1; i++) {
				tmp.setN(new Tile(offsetX + i*lv, offsetY));
				tmp = tmp.getN();
			}
		}
		else {
			tmp = s;
			while(tmp.getW() != null) {
				tmp = tmp.getW();
			}
			tmp.setW(new Tile(offsetX, offsetY + getTilingWidth()*lh));
			tmp = tmp.getW();
			for(int i = 1; i<(n/lv)+1; i++) {
				tmp.setN(new Tile(offsetX + i*lv, offsetY + getTilingWidth()*lh));
				tmp = tmp.getN();
			};			
		}
	}
	
	// Slides the tiling window by one tile column
	public void slide() {
		addColumn();
		removeFirstColumn();
	}
	
	// Removes first column of tiles
	public void removeFirstColumn() {
		Tile tmp, tmp2;
		tmp = s;
		s = s.getW();
		
		while(tmp != null) {
			tmp2= tmp;
			tmp = tmp.getN();
			
			tmp2.setN(null);
			tmp2.setW(null);
		}
	}
	
	// Returns the tiling height
	public int getTilingHeight() {
		int height = 0;
		Tile tmp = s;
		while(tmp != null) {
			tmp = tmp.getN();
			height++;
		}
		
		return height;
	}
	
	// Return the tiling width
	public int getTilingWidth() {
		int width = 0;
		Tile tmp = s;
		while(tmp != null) {
			tmp = tmp.getW();
			width++;
		}
		
		return width;
	}

	// Returns the tile in which the request is in the SW quadrant 
	public Tile getSWTile(Request req) {
		int x, y;
		
		x = req.getSource();
		y = req.getTime() - req.getSource();
		
		// Request is in the SW quadrant
		if(y >= s.getY() && y < s.getY() + lh/2) {
			Tile tmp = s;
			boolean found = false;
			while(!found && tmp != null) {
				if(x >= tmp.getX() && x<tmp.getX() + lv/2) {
					found = true;
				}
				else {
					tmp = tmp.getN();
				}
			}
			
			return tmp;
		}
		else {
			return null;
		}
	}
}