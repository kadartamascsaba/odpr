package core;
import java.util.Comparator;

public class RequestComparator implements Comparator<Request> {

	@Override
	public int compare(Request arg0, Request arg1) {
		
		if(arg0.distance() < arg1.distance()) {
			return -1;
		}
		else if(arg0.distance() > arg1.distance()) {
			return  1;
		}
		
		return 0;
	}
}