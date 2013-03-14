package by.gsu.epamlab.Implements.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import by.gsu.epamlab.Interface.Graphics2DUI;
import by.gsu.epamlab.beans.Passenger;

public class ContainerUI implements Graphics2DUI{

	public static final int GAP = 3;
	public final int width;
	public static final int HEIGHT = StoreyUI.HEIGHT;	
	private int offsetX;
	private Point point;
	private List<PassengerUI> listUI = new ArrayList<PassengerUI>(); 
	private List<Place> points = new LinkedList<Place>();
	
	public ContainerUI(List<Passenger> contents, Point position,int width) {
		super();
		point = position;
		offsetX =point.x+GAP;
		this.width = width; 

		int countPoint = width/(PassengerUI.WIDTH +GAP);
		for(int j=1; j <= countPoint;j++){
			Place place = new Place(new Point(getOffsetX(), point.y+GAP));
			points.add(place);
		}
		Passenger passenger;
		for(int i=0;i<contents.size();i++){
			passenger = contents.get(i);
//			PassengerUI pUI = new PassengerUI(passenger);
			addPassengerUI(new PassengerUI(passenger));
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.drawRect(point.x, point.y, width, HEIGHT);
		for (PassengerUI pUI : listUI) {
			pUI.draw(g);
		}
	}
	
	private int getOffsetX() {
		int thisOffset = offsetX;
		offsetX+=GAP+PassengerUI.WIDTH;
		return thisOffset;
	}

	public PassengerUI getPassengerUI(Passenger passenger){
		for (PassengerUI pasUI : listUI) {
			if (pasUI.getPassenger().equals(passenger)){
				return pasUI;
			}
		}
		return null;
	}
	
	public void addPassengerUI(PassengerUI pUI){
			
		listUI.add(pUI);
		Point p=null;
		for (Place place : points) {
			if (place.getPassengerUI() == null){
				p = place.getPoint();
				pUI.setPoint(new Point(p));
				place.setPassengerUI(pUI);
				break;
			}
			
		}
		if (p==null){
			p = points.get(0).getPoint();
			p.x = 24;
			pUI.setPoint(new Point(p));
		}
	}

	public void removePassengerUI(PassengerUI pUI){
		listUI.remove(pUI);
		for (Place place : points) {
			if (place.getPassengerUI() == pUI){
				place.clear();
			}
			
		}
	}
	
	public void changeY(int y){
		for(PassengerUI pUI: listUI){
			pUI.getPoint().y = y+GAP;
		}
		point.y = y;
	}

}
