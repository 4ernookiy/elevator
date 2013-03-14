package by.gsu.epamlab.Implements;

import by.gsu.epamlab.events.AbstractEvent;

public class Synchronizator {

	private AbstractEvent event;
	private boolean waitRepaint = true;
	private boolean empty = true;
	public synchronized AbstractEvent take() throws InterruptedException{
		while(empty){
			wait();
		}
		empty = true;
		return event;
	}

	public synchronized void add(AbstractEvent event) throws InterruptedException  {
		while(!empty){
			wait();
		}
		empty = false;
		this.event = event;
		notify();
		while(waitRepaint){
			wait();
		}
		waitRepaint = true;
	}

	public synchronized void goDo(){
		waitRepaint = false;
		notify();
	}
}
