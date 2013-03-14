package by.gsu.epamlab.run.configuration;

public class ConfigBuilding {
	private int storeysNumber;
	private int elevatorCapacity;
	private int passengersNumber;
	private int animationBoost;
	public ConfigBuilding(int storeysNumber, int elevatorCapacity,
			int passengersNumber, int animationBoost) {
		super();
		this.storeysNumber = storeysNumber;
		this.elevatorCapacity = elevatorCapacity;
		this.passengersNumber = passengersNumber;
		this.animationBoost = animationBoost;
	}
	
	public int getStoreysNumber() {
		return storeysNumber;
	}
	public int getElevatorCapacity() {
		return elevatorCapacity;
	}
	public int getPassengersNumber() {
		return passengersNumber;
	}
	public int getAnimationBoost() {
		return animationBoost;
	}
	
}
