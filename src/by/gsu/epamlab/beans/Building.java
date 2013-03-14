package by.gsu.epamlab.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import by.gsu.epamlab.events.AbstractEvent;
import by.gsu.epamlab.events.EventType;
import by.gsu.epamlab.factory.DaoFactory;
import by.gsu.epamlab.run.RunElevator;
import by.gsu.epamlab.run.configuration.ConfigBuilding;
import by.gsu.epamlab.threads.ElevatorTask;
import by.gsu.epamlab.threads.TransporationTask;

public class Building {
	private static Logger logger = Logger.getLogger(RunElevator.class);

	private List<Storey> storeys=new ArrayList<Storey>();
	private Elevator elevator;
	private Controller controller;
	ConfigBuilding configBuilding;
	public final int FIRST_STOREY = 1;
    public final int FINAL_STOREY;
    public boolean progress=true;
    private int arrived;
	private int animation;
    private List<Thread> listThreads = new ArrayList<Thread>();
    private State state;

	public ConfigBuilding getConfigBuilding() {
		return configBuilding;
	}
	public List<Storey> getStoreys() {
		return storeys;
	}
	public Elevator getElevator() {
		return elevator;
	}
	
	public Building(ConfigBuilding configBuilding) {
		super();
		state = State.NOT_STARTED;
		setAnimation(configBuilding.getAnimationBoost());
		this.configBuilding = configBuilding;
		FINAL_STOREY = configBuilding.getStoreysNumber();
		for(int i=0;i <= configBuilding.getStoreysNumber();i++){
			storeys.add(new Storey(i));
		}
		this.elevator = new Elevator(configBuilding.getElevatorCapacity(), 
				configBuilding.getStoreysNumber(), 1);
		randomFilling(); // randomize filling containers
		controller = new Controller(this);
	}

	
	private void randomFilling(){
		Random random = new Random();
		int storey = storeys.size()-1;
		for(int i=1; i <= configBuilding.getPassengersNumber(); i++){
			int currentStorey = random.nextInt(storey)+1;
			int storeyDestination = random.nextInt(storey)+1;
			int countAttempt=0;
			while (storeys.get(currentStorey).getDispatchContainer().size() != 0 && countAttempt<configBuilding.getStoreysNumber()){
				currentStorey = random.nextInt(storey)+1;
				countAttempt++;
			}
			while (currentStorey == storeyDestination){
				storeyDestination = random.nextInt(storey)+1;
			}
			Passenger passenger = new Passenger(currentStorey, storeyDestination);
			Storey storeyForPassenger =storeys.get(currentStorey); 
			storeyForPassenger.getDispatchContainer().add( passenger ); 
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Storey storey: storeys){
			sb.append(storey);
		}
		return "Building: " + elevator +"\n"+ sb.toString();
	}
	
	public Controller getController() {
		return controller;
	}
	
	public synchronized boolean allArrived() {
		return arrived == configBuilding.getPassengersNumber()?true:false;
	}

	public synchronized void arrived() {
		 arrived++;
	}
	
	public void start(){
		state = State.IN_PROGRESS;
		Thread presentator = new Thread(new Runnable() {
			@Override
			public void run() {
				// show events for looking ....
				boolean progress=true;
				AbstractEvent event = null;
				try {
				while (progress) {
						event = DaoFactory.events.take();
						if (event.getEventType()==EventType.VALIDATION 
								|| event.getEventType()==EventType.ABORTING_TRANSPORTATION) {
							progress = false;
						}				
					DaoFactory.viewer.show(event);
					if (configBuilding.getAnimationBoost() > 0 && animation !=0 ){
						Thread.sleep(animation);
					}
					DaoFactory.events.goDo();
				}
				} catch (InterruptedException e) {
					logger.error(e+":omg , me interrupted ");
				}
				state = State.COMPLETED;
			}
		});
		presentator.setName("Presentator");
		presentator.start();

		try {
			DaoFactory.events.add(new AbstractEvent(EventType.STARTING_TRANSPORTATION));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Building building = this;
		// start All task-Threads in other thread ....
		Thread startTask = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int storey = building.FIRST_STOREY; storey <= building.FINAL_STOREY; storey++) {
					List<Passenger> passengers = building.getStoreys()
							.get(storey).getDispatchContainer();
					synchronized (passengers) {
						if (!passengers.isEmpty()) {
							for (Passenger passenger : passengers) {
								TransporationTask tt = new TransporationTask(
										passenger, building);

								Thread t = new Thread(tt);
								listThreads.add(t);
								t.setName("Pas:" + passenger.getId());
								t.start();
							}
						}
					}
				}
				ElevatorTask et = new ElevatorTask(building);
				Thread el = new Thread(et);
				listThreads.add(el);
				el.setName("Elevator");
				el.start();
			}

		});
		startTask.setName("StartWorking");
		startTask.start();
		

	}
	
	public State getState() {
		return state;
	}

	public synchronized void cancel(){
		state = State.ABORTED;
		try {
			DaoFactory.events.add(new AbstractEvent(EventType.ABORTING_TRANSPORTATION));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Thread thread : listThreads) {
			thread.interrupt();
		}
	}

	public void setAnimation(int value){
		int delay = 400;

		if (value > 0 ){
			animation = delay / value;
		}
		if (value ==0 ){
			animation = 3000;
		}
	}
	
	public int getAnimation(){
		return animation;
	}
}
