package by.gsu.epamlab.Implements;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import by.gsu.epamlab.Implements.ui.WindowAplication;
import by.gsu.epamlab.Interface.IviewerDAO;
import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.events.AbstractEvent;
import by.gsu.epamlab.events.MovingPassengerEvent;

public class FrameImpl implements IviewerDAO{
	private static Logger logger = Logger.getLogger(FrameImpl.class);
	private WindowAplication wp;
	
	@Override
	public void show(AbstractEvent event) {
		if (event != null) {
			if (logger.isInfoEnabled()){
			logger.info(event.getEventType()+": "+event.getString());
			}

			switch (event.getEventType()) {
			case BOADING_OF_PASSENGER:
				MovingPassengerEvent board = (MovingPassengerEvent) event;
				wp.getBuildingUI().animationPassengerMoving(board);
				break;
				
			case DEBOADING_OF_PASSENGER:
				MovingPassengerEvent deboard = (MovingPassengerEvent) event;
				wp.getBuildingUI().animationPassengerMoving(deboard);
				break;

			default:
				wp.repaint();
				break;
			}
		}
		
	}

	public FrameImpl(final Building building) {
		super();
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	wp = new WindowAplication(building);
		    	wp.setVisible(true);
		    }
		});
	}
	
	
}
