public class Vertex {
	
	int t[][];
	
	
	public Vertex() {		
		// Matrix to contain parameters regarding load for each class
		// There are five classes: NEAR and Tj where j=1..4
		t = new int[5][2];
		
		for(int i=0; i<5; i++) {
			for(int j=0; j<2; j++) {
				t[i][j] = 0;
			}
		}
	}
	
	public int getNearC() {
		return t[0][0];
	}
	
	public int getNearB() {
		return t[0][1];
	}
	
	public int getFarC(int TJ) {
		if(TJ<1 && TJ>4) {
			return -1;
		}
		else {
			return t[TJ][0];
		}
	}
	
	public int getFarB(int TJ) {
		if(TJ<1 && TJ>4) {
			return -1;
		}
		else {
			return t[TJ][1];
		}
	}	

	public void incNearC() {
		t[0][0]++;
	}
	
	public void incNearB() {
		t[0][1]++;
	}
	
	public void incFarC(int TJ) {
		if(TJ<1 && TJ>4) {
			System.out.println("Invalid Far index");
		}
		else {
			t[TJ][0]++;
		}
	}
	
	public void incFarB(int TJ) {
		if(TJ<1 && TJ>4) {
			System.out.println("Invalid Far index");
		}
		else {
			t[TJ][1]++;
		}
	}
	
}