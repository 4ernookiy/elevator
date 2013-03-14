package by.gsu.epamlab.threads;

import java.util.List;

import org.apache.log4j.Logger;

import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.beans.Elevator;
import by.gsu.epamlab.beans.Passenger;
import by.gsu.epamlab.beans.State;
import by.gsu.epamlab.beans.Storey;
import by.gsu.epamlab.events.EventType;
import by.gsu.epamlab.events.MovingPassengerEvent;
import by.gsu.epamlab.factory.DaoFactory;

public class TransporationTask implements Runnable {
	private static Logger logger = Logger.getLogger(TransporationTask.class);

	private Passenger passenger;
	private Building building;

	public TransporationTask(Passenger passenger, Building building) {
		super();
		this.passenger = passenger;
		this.building = building;
	}

	@Override
	public void run() {
		passenger.setState(State.IN_PROGRESS);
		Storey currentStorey = building.getStoreys().get(
				passenger.getCurrentStorey());
		List<Passenger> passengersStorey = currentStorey
				.getDispatchContainer();
		Elevator elevator = building.getElevator();
		List<Passenger> passengersElevator = elevator.getPassengers();

		try {
			synchronized (passengersStorey) {
				while (elevator.getCurrentStorey() != passenger.getCurrentStorey()
						|| !elevator.isDirectionCoincides(passenger)
						|| !elevator.isAvailablePlaces()) {
					passengersStorey.wait();
				}
				building.getController().enterElevator(passenger);

				DaoFactory.events.add(new MovingPassengerEvent(EventType.BOADING_OF_PASSENGER, passenger, elevator.getCurrentStorey()));
				// weak up elevator
				passengersStorey.notifyAll();
			}

			synchronized (passengersElevator) {
				while (elevator.getCurrentStorey() != passenger
						.getStoreyDestination()) {
					passengersElevator.wait();
				}
				building.getController().leaveElevator(passenger);

				DaoFactory.events.add(new MovingPassengerEvent(EventType.DEBOADING_OF_PASSENGER, passenger, elevator.getCurrentStorey()));
				
				passenger.setState(State.COMPLETED);
				// weak up elevator
				passengersElevator.notifyAll();
			}
		} catch (InterruptedException e) {
			passenger.setState(State.ABORTED);
			if (logger.isDebugEnabled()) {
				logger.debug(passenger + ":task - " + State.ABORTED);
			}
		}
	}
}
