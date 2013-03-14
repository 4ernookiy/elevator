package by.gsu.epamlab.beans;

public final class Direction {
	public final static Direction UP = new Direction(1, "MOVE_UP");
	public final static Direction DOWN = new Direction(-1, "MOVE_DOWN");
	
	private int nextStorey;
	private String directionStr;

	private Direction(int nextStorey, String directionStr) {
		super();
		this.nextStorey = nextStorey;
		this.directionStr = directionStr;
	}

	public int getNextStorey() {
		return nextStorey;
	}

	@Override
	public String toString() {
		return directionStr;
	}
	
	public final static Direction whatDirection(int current, int destination){
		int direction = destination-current ;
		
		if ( direction>0 ) {
			return Direction.UP;
		}
		if ( direction<0 ){
			return Direction.DOWN;
		}
		return null;
	}
	
	public final static Direction whatDirection(Passenger passenger) {
		return Direction.whatDirection(passenger.getCurrentStorey(), passenger.getStoreyDestination());
	}
	
}
