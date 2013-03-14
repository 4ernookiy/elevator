package by.gsu.epamlab.Implements;

import org.apache.log4j.Logger;

import by.gsu.epamlab.Interface.IviewerDAO;
import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.events.AbstractEvent;

public class ConsoleImpl implements IviewerDAO {
	private static Logger logger = Logger.getLogger(ConsoleImpl.class);

	@Override
	public void show(AbstractEvent event) {
		if (event!= null) {
			switch (event.getEventType()) {

			default:
				if (logger.isInfoEnabled()){
				logger.info(event.getEventType()+": "+event.getString());
				}
				break;
			}
		}
	}

	public ConsoleImpl(final Building building) {
		super();
		new Thread(new Runnable() {
			@Override
			public void run() {
				building.start();
			}
		}).start();
	}

}
