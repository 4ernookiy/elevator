package by.gsu.epamlab.beans;

public final class State {
	public final static State NOT_STARTED = new State(100, "NOT_STARTED","this is task is not run");
	public final static State IN_PROGRESS = new State(200, "IN_PROGRESS","this is task is running");
	public final static State COMPLETED = new State(300, "COMPLETED","this is task is complete");
	public final static State ABORTED = new State(400, "ABORTED","this is task is aborted");
	
	private int state;
	private String stateStr;
	private String stateDescription;
	
	private State(int state, String stateStr, String stateDescription) {
		super();
		this.state = state;
		this.stateStr = stateStr;
		this.stateDescription = stateDescription;
	}
	
	public int getState() {
		return state;
	}
	public String getStateStr() {
		return stateStr;
	}
	public String getStateDescription() {
		return stateDescription;
	}

	@Override
	public String toString() {
		return stateStr;
	}
	
}
