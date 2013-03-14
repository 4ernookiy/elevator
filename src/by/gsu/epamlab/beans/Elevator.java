package by.gsu.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
	private int capacity;
	private List<Passenger> passengers = new ArrayList<Passenger>();

	private final int FIRST_STOREY = 1;
    private final int FINAL_STOREY;
    private Direction move = Direction.UP;
    private int currentStorey;

	public Elevator(int capacity, int storeyNumbersBuilding, int currentStorey) {
		super();
		this.capacity = capacity;
		FINAL_STOREY = storeyNumbersBuilding;
		if (currentStorey<1 || currentStorey > storeyNumbersBuilding) {
			currentStorey =1;
		}else{
			this.currentStorey = currentStorey;
		}
			
	}

	public int getCapacity() {
		return capacity;
	}
	
	public List<Passenger> getPassengers() {
		return passengers;
	}

	@Override
	public String toString() {
		return "Elevator [capacity=" + capacity+"]\n   Passengers:"+passengers;
	}
	
	public void move(){
		currentStorey += move.getNextStorey();
		if (currentStorey==FIRST_STOREY){
			move = Direction.UP;
		}
		if (currentStorey==FINAL_STOREY){
			move = Direction.DOWN;
		}
	}
	
	public int getCurrentStorey() {
		return currentStorey;
	}
	public Direction getDirection() {
		return move;
	}

	public boolean isDirectionCoincides(Passenger passenger){
		int destination = passenger.getStoreyDestination();
		Direction direction = Direction.whatDirection(currentStorey, destination);
		return direction == move? true: false;
	}

	public synchronized boolean isAvailablePlaces(){
		return getPassengers().size()<capacity? true: false;
	}

}
