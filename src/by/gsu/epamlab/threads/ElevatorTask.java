package by.gsu.epamlab.threads;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.beans.Elevator;
import by.gsu.epamlab.events.AbstractEvent;
import by.gsu.epamlab.events.ElevatorMovingEvent;
import by.gsu.epamlab.events.EventType;
import by.gsu.epamlab.events.ValidationEvent;
import by.gsu.epamlab.factory.DaoFactory;
import by.gsu.epamlab.run.configuration.TimeWatch;

public class ElevatorTask implements Runnable {
	private static Logger logger = Logger.getLogger(ElevatorTask.class);
	private Elevator elevator;
	private Building building;

	public ElevatorTask(Building building) {
		super();
		this.elevator = building.getElevator();
		this.building = building;

	}

	@Override
	public void run() {
		int from, to;
		try {
		TimeWatch watch = TimeWatch.start();
		while (!building.allArrived() && !Thread.currentThread().isInterrupted()) {
//			if (building.getConfigBuilding().getAnimationBoost() > 0){
//				Thread.sleep(200);
//			}
			// waiting movement passengers
			building.getController().movementPassengers();
			from = elevator.getCurrentStorey();
			elevator.move();
			to = elevator.getCurrentStorey();
			DaoFactory.events.add(new ElevatorMovingEvent(EventType.MOVING_ELEVATOR ,elevator
					.getPassengers().size(), from, to));

		}
		if (!Thread.currentThread().isInterrupted()){
			long timeInMilisec = watch.time(TimeUnit.MILLISECONDS);
			DaoFactory.events.add(new AbstractEvent(EventType.COMPLETION_TRANSPORTATION, "elevator moved all passengers in "+timeInMilisec+" milisec"));
			DaoFactory.events.add(new ValidationEvent(EventType.VALIDATION, building));
		}
		} catch (InterruptedException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(elevator);
			}
		}
	}

}
