package by.gsu.epamlab.events;

public class AbstractEvent {
	private EventType eventType;
	private String string;
	
	public AbstractEvent(EventType eventType, String string) {
		super();
		this.string = string;
		this.eventType = eventType;
	}
	
	public AbstractEvent(EventType eventType) {
		this(eventType, "");
	}

	public EventType getEventType() {
		return eventType;
	}

	public String getString() {
		return string;
	}

}

