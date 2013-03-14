package by.gsu.epamlab.events;

import by.gsu.epamlab.beans.Passenger;

public class MovingPassengerEvent extends AbstractEvent{
	private Passenger passenger;
	private int storey;
	public MovingPassengerEvent(EventType eventType, Passenger passenger, int storey) {
		super(eventType,  "(Pid-"+passenger.getId()+" on story-"+storey+")");
		this.passenger = passenger;
		this.storey = storey;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public int getStorey() {
		return storey;
	}
}
