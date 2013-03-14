package by.gsu.epamlab.Implements.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import by.gsu.epamlab.Interface.Graphics2DUI;
import by.gsu.epamlab.beans.Passenger;

public class PassengerUI implements Graphics2DUI{
	
	private static final Font FONT = new Font("Serif", Font.ITALIC, 16);
	private static final Font FONT2 = new Font("Serif", Font.PLAIN, 12);
	public static final int WIDTH = 20;
	public static final int HEIGTH= 29;

	private int id;
	private int destination;
	private Point point;
	private Passenger passenger;
	
	public PassengerUI(Passenger passenger, Point point) {
		super();
		this.passenger = passenger;
		this.id = passenger.getId();
		this.destination = passenger.getStoreyDestination();
		this.point = point;
	}
	
	public PassengerUI(Passenger passenger) {
		this(passenger,null);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(point.x, point.y, WIDTH, HEIGTH);
		g.setColor(Color.RED);
		g.setFont(FONT);
		g.drawString(String.valueOf(id), point.x+2, point.y+StoreyUI.HEIGHT-3*StoreyUI.GAP );
		g.setColor(Color.BLUE);
		g.setFont(FONT2);
		g.drawString(String.valueOf(destination), point.x+2, point.y+StoreyUI.HEIGHT/2-2*StoreyUI.GAP );
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
}
