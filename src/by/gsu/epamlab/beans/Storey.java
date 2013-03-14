package by.gsu.epamlab.beans;

import java.util.ArrayList;
import java.util.List;

public class Storey {
	private int id;
	private List<Passenger> dispatchContainer = new ArrayList<Passenger>();
	private List<Passenger> destinationContainer = new ArrayList<Passenger>();

	public Storey(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}


	public List<Passenger> getDispatchContainer() {
		return dispatchContainer;
	}

	public List<Passenger> getDestinationContainer() {
		return destinationContainer;
	}

	@Override
	public String toString() {
		if (id==0) return "";
		StringBuilder sb = new StringBuilder();
		for(Passenger p: dispatchContainer){
			sb.append(p);
			sb.append("\n");
		}
		
		return "Storey id=" + id + ", dispatchContainer:\n" + sb.toString() + "\n";
	}
	
}
