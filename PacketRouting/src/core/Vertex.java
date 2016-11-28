package core;
public class Vertex {
	
	int t[][];
	
	public Vertex() {		
		t = new int[5][2];
	}
	
	// Getters
	public int getNearC() {
		return t[4][0];
	}
	
	public int getNearB() {
		return t[4][1];
	}
	
	public int getFarC(int TJ) {
		return t[TJ][0];
	}
	
	public int getFarB(int TJ) {
		return t[TJ][1];
	}	

	// Setters
	public void incNearC() {
		t[4][0]++;
	}
	
	public void incNearB() {
		t[4][1]++;
	}
	
	public void incFarC(int TJ) {
		t[TJ][0]++;
	}
	
	public void incFarB(int TJ) {
		t[TJ][1]++;
	}
}