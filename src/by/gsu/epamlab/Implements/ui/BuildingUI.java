package by.gsu.epamlab.Implements.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import by.gsu.epamlab.Interface.Graphics2DUI;
import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.beans.Passenger;
import by.gsu.epamlab.beans.State;
import by.gsu.epamlab.beans.Storey;
import by.gsu.epamlab.events.MovingPassengerEvent;

public class BuildingUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 666L;
	private Building building;
	private ContainerUI elevatorUI; 
	private List<StoreyUI> storeysUI = new ArrayList<StoreyUI>(); // copy links for graphList
	private List<Graphics2DUI> graphList = new ArrayList<Graphics2DUI>();
	private BufferedImage buffer;
	public BuildingUI(Building building) {
		super();
		this.building = building;
		init();
	}
	
	private void init(){
		graphList.clear();
		int width = building.getConfigBuilding().getElevatorCapacity()*(PassengerUI.WIDTH+ContainerUI.GAP)+ContainerUI.GAP;
		Storey storey;
		for (int i = building.FINAL_STOREY; i >= building.FIRST_STOREY; i--) {
			storey = building.getStoreys().get(i);
			StoreyUI storeyUI = new StoreyUI(storey, width);
			graphList.add(storeyUI);
			storeysUI.add(storeyUI);
		}
		Point pe = new Point(StoreyUI.GAP*3+StoreyUI.WIDTH+StoreyUI.CONTAINERS_WIDTH,0);
		elevatorUI = new ContainerUI(building.getElevator().getPassengers(), pe, width);
//		graphList.add(elevatorUI); 
	}

	private void rebuildBuffer() {
		int w = getWidth();
		int h = getHeight();
		buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = buffer.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
    		for (Graphics2DUI graph : graphList) {
    			graph.draw(g2d);
    		}
    		elevatorUI.changeY(getPositionElevator());
    		elevatorUI.draw(g2d);
    		g2d.dispose();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		rebuildBuffer();
		g.drawImage(buffer, 0, 0, this);
	}
	
	private int getPositionElevator(){
		int currentStorey = building.getElevator().getCurrentStorey();
		int y=0;
			StoreyUI sUI = getStoreyUI(currentStorey);
			if (sUI!=null){
				y = sUI.offset_y;
			}
		return y;
	}

	
	private StoreyUI getStoreyUI( int storey){
		for (StoreyUI sUI: storeysUI) {
			if (sUI.getStorey().getId() == storey){
				return sUI;
			}
		}
		return null;
	}

	public ContainerUI getElevatorUI() {
		return elevatorUI;
	}

	public List<StoreyUI> getStoreysUI() {
		return storeysUI;
	}

	public void animationPassengerMoving(MovingPassengerEvent event){
		ContainerUI from;
		ContainerUI to;
		int storey = event.getStorey();
		Passenger passenger = event.getPassenger();
		Point fromPoint = null;
		Point toPoint = null ;
		switch (event.getEventType()) {
		case BOADING_OF_PASSENGER:
			from = getStoreyUI(storey).getDispatch();
			to = elevatorUI;
			break;
		case DEBOADING_OF_PASSENGER:
			from = elevatorUI;
			to = getStoreyUI(storey).getDestination();
			break;
		default:
			throw new IllegalArgumentException();
		}
		PassengerUI pUI = from.getPassengerUI(passenger);
		fromPoint = pUI.getPoint();
		from.removePassengerUI(pUI);
		to.addPassengerUI(pUI);
		toPoint = pUI.getPoint();
		pUI.setPoint(fromPoint);

		while (fromPoint.x <= toPoint.x && building.getState()!=State.ABORTED){
			fromPoint.x++;
			try {
				Thread.sleep(building.getAnimation()/13);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}
