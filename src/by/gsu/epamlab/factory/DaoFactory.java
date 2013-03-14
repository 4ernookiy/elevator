package by.gsu.epamlab.factory;


import by.gsu.epamlab.Implements.ConsoleImpl;
import by.gsu.epamlab.Implements.FrameImpl;
import by.gsu.epamlab.Implements.Synchronizator;
import by.gsu.epamlab.Interface.IviewerDAO;
import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.run.configuration.ConfigBuilding;

public class DaoFactory {
	
	public static ConfigBuilding defaultConfig ;
	public final static Synchronizator events = new Synchronizator();
	
	public static IviewerDAO viewer;

	public static void initViewer(Building building){
		IviewerDAO iviewerDAO;
		int animationBoost = building.getConfigBuilding().getAnimationBoost();
		switch (animationBoost) {
		case 0:
			iviewerDAO = new ConsoleImpl(building); 
			break;

		default:
			iviewerDAO = new FrameImpl(building);
			break;
		}
		
		viewer = iviewerDAO;
		
	}
		  
}