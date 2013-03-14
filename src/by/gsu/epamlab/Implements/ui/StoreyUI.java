package by.gsu.epamlab.Implements.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import by.gsu.epamlab.Interface.Graphics2DUI;
import by.gsu.epamlab.beans.Storey;

public class StoreyUI implements Graphics2DUI {

	public static final int HEIGHT = 35;
	public static final int WIDTH = 25;
	public static final int CONTAINERS_WIDTH = 400;
	public static final int GAP = 3;
	private static int OFFSET_Y = GAP;
	private static final Font FONT = new Font("Serif", Font.BOLD, HEIGHT);
	private Storey storey;

	public final int offset_y;
	private ContainerUI dispatch ;
	private ContainerUI destination;
	

	public StoreyUI(Storey storey, int widthElevator) {
		super();
		this.storey = storey;
		this.offset_y = OFFSET_Y;
		OFFSET_Y += HEIGHT+GAP;
		Point pDispath = new Point(2*GAP+WIDTH, offset_y);
		this.dispatch = new ContainerUI(storey.getDispatchContainer(), pDispath, CONTAINERS_WIDTH);
		Point pDest= new Point(4*GAP+WIDTH+CONTAINERS_WIDTH+widthElevator, offset_y);
		this.destination = new ContainerUI(storey.getDestinationContainer(), pDest, CONTAINERS_WIDTH);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.setFont(FONT);
		g.drawRect(GAP, offset_y, WIDTH, HEIGHT);
		g.drawString(String.valueOf(storey.getId()), GAP*3, offset_y + HEIGHT -GAP );
		dispatch.draw(g);
		destination.draw(g);

	}

	public ContainerUI getDispatch() {
		return dispatch;
	}

	public ContainerUI getDestination() {
		return destination;
	}

	public Storey getStorey() {
		return storey;
	}
	
}