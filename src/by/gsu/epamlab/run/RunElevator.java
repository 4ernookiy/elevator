package by.gsu.epamlab.run;

import by.gsu.epamlab.beans.Building;
import by.gsu.epamlab.factory.DaoFactory;
import by.gsu.epamlab.run.configuration.ConfigBuilding;
import by.gsu.epamlab.run.configuration.LoadConfig;

public class RunElevator {

	public static void main(String[] args) {

		ConfigBuilding configBuilding = LoadConfig.loadConfig(args);
		DaoFactory.defaultConfig = configBuilding;
		final Building building = new Building(configBuilding);
		DaoFactory.initViewer(building);
		
	}
}
