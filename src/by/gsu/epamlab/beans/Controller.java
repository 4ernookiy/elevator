package by.gsu.epamlab.beans;

import java.util.List;

public class Controller {
	private Building building;

	public Controller(Building building) {
		super();
		this.building = building;
	}

	public void movementPassengers() throws InterruptedException {

		List<Passenger> passengersElevator = building.getElevator()
				.getPassengers();
		int curStorey = building.getElevator().getCurrentStorey();
		List<Passenger> passengersStorey = building.getStoreys().get(curStorey)
				.getDispatchContainer();
		Elevator elevator = building.getElevator();
			synchronized (passengersElevator) {
				while (hasArrived(passengersElevator)) {
					passengersElevator.notifyAll();
					passengersElevator.wait();
				}
			}
			synchronized (passengersStorey) {
				while (hasRightDirection(passengersStorey)
						&& elevator.isAvailablePlaces()) {
					passengersStorey.notifyAll();
					passengersStorey.wait();
				}
			}
	}

	public void leaveElevator(Passenger passenger) {
		int storeyElevator = building.getElevator().getCurrentStorey();
		building.getStoreys().get(storeyElevator).getDestinationContainer()
				.add(passenger);
		building.getElevator().getPassengers().remove(passenger);
		building.arrived();
	}

	public void enterElevator(Passenger passenger) {
		int storeyElevator = building.getElevator().getCurrentStorey();
		building.getElevator().getPassengers().add(passenger);
		building.getStoreys().get(storeyElevator).getDispatchContainer()
				.remove(passenger);
	}

	public boolean hasRightDirection(List<Passenger> passengers) {
		boolean hasDirection = false;
		for (Passenger p : passengers) {
			if (Direction.whatDirection(p) == building.getElevator()
					.getDirection()) {
				hasDirection = true;
				break;
			}
		}
		return hasDirection;
	}

	public boolean hasArrived(List<Passenger> passengers) {
		boolean hasArrived = false;
		for (Passenger p : passengers) {
			if (p.getStoreyDestination() == building.getElevator()
					.getCurrentStorey()) {
				hasArrived = true;
				break;
			}
		}
		return hasArrived;
	}

}
