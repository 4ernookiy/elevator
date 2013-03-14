package by.gsu.epamlab.events;

import org.apache.log4j.Logger;

import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.beans.Passenger;
import by.gsu.epamlab.beans.State;
import by.gsu.epamlab.beans.Storey;

public class ValidationEvent extends AbstractEvent {
	private static Logger logger = Logger.getLogger(ValidationEvent.class);

	private Building building;

	public ValidationEvent(EventType eventType, Building building) {
		super(eventType, "");
		this.building = building;
	}

//	@Override
//	public void toConsole() {
//		validation();
//	}
//
//	@Override
//	public void toFrame() {
//		validation();
//	}
//
	
	public StringBuilder validation() {
		boolean validation = true;
		StringBuilder sb = new StringBuilder();
		sb.append("Starting validation!\n");
		int count = 0;
		for (Storey storey : building.getStoreys()) {
			if (storey.getId() < building.FIRST_STOREY){
				continue;
			}
			if (storey.getDispatchContainer().size() != 0) {
				validation = false;
				sb.append("storey:" + storey.getId());
				sb.append(" dispatch container not empty - false\n");
			} else {
				sb.append("storey:" + storey.getId());
				sb.append(" dispatch container empty - ok\n");
			}
			if (storey.getDestinationContainer().size() > 0) {
				count += storey.getDestinationContainer().size();
				sb.append("         destin.. container ");
				for (Passenger passenger : storey.getDestinationContainer()) {
					if (passenger.getState() != State.COMPLETED
							|| passenger.getStoreyDestination() != storey.getId()) {
						validation = false;
						sb.append("P:" + passenger.getId() + "-false, ");
					} else {
						sb.append("P" + passenger.getId() + "-ok, ");
					}
				}
				sb.append("\n");
			}

		}
		if (building.getElevator().getPassengers().size() != 0) {
			validation = false;
			sb.append("elevator is not empty - false!!!\n");
		} else {
			sb.append("elevator is empty - ok\n");
		}
		if (building.getConfigBuilding().getPassengersNumber() != count) {
			validation = false;
			sb.append("All passengers not arrived - false\n");
		} else {
			sb.append("All("+count+") passengers arrived - ok\n");
		}
		if (validation) {
			sb.append("task validation:" + validation);
		}
		if (logger.isInfoEnabled()) {
			logger.info(sb.toString());
		}
		return sb;
	}

}
