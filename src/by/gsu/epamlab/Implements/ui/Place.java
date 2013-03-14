package by.gsu.epamlab.Implements.ui;

import java.awt.Point;

public class Place {

	private Point point;
	private PassengerUI passengerUI=null;
	
	public Place(Point point, PassengerUI passengerUI) {
		super();
		this.point = point;
		this.passengerUI = passengerUI;
	}
	
	public Place(Point point) {
		super();
		this.point = point;
	}



	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public PassengerUI getPassengerUI() {
		return passengerUI;
	}

	public void setPassengerUI(PassengerUI passengerUI) {
		this.passengerUI = passengerUI;
	}
	
	public void clear(){
		this.passengerUI = null;
	}
}
