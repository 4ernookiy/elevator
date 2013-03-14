package by.gsu.epamlab.beans;

public class Passenger{

	private static int counterId=0;
	
	private int id;
	private State state;
	private int storeyDestination;
	private int currentStorey;
	public Passenger(int currentStorey,int storeyDestination) {
		super();
		counterId++;
		id = counterId;
		this.currentStorey = currentStorey;
		this.storeyDestination = storeyDestination;
		state = State.NOT_STARTED;
	}

	@Override
	public String toString() {
		return "Passenger[id=" + id + ", state=" + state
				+ ", destination=" + storeyDestination + "]";
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public int getStoreyDestination() {
		return storeyDestination;
	}

	public int getCurrentStorey() {
		return currentStorey;
	}

}
