package by.gsu.epamlab.events;

public class ElevatorMovingEvent extends AbstractEvent {

	public ElevatorMovingEvent(EventType eventType,int countPassengers,int from, int to) {
		super(eventType, "(from story-"+from+" to story-"+to+")"+" contain:"+countPassengers);
	}

}
