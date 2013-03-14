package by.gsu.epamlab.run.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import by.gsu.epamlab.constants.Consts;

public final class LoadConfig {
	private static Logger logger = Logger.getLogger(LoadConfig.class);
	private final static int MAX_STOREYS = 9;
	private final static int MAX_CAPACITY = 5;
	private final static int MAX_PASSENGERS = 100;

	public final static ConfigBuilding getDefaultConfig;

	static {
		ConfigBuilding cb = null;
		try {

			cb = LoadConfig.getConfig(Consts.CONFIG_APLICATION_PROPERTY);

		} catch (Exception e) {
			logger.error(
					"can't load settings data: aplication will be finalized", e);
			System.exit(0);
		}
		getDefaultConfig = cb;
	}
	private final static String EXT_XML = ".xml";
	private final static String EXT_PROPERTY = ".property";

	public static ConfigBuilding getConfig(String file) throws IOException {

		Properties properties = new Properties();
		InputStream is = LoadConfig.class.getResourceAsStream(file);
		if (is == null) {
			throw new FileNotFoundException("file not found:" + file);
		}
		try {
			if (file.endsWith(EXT_PROPERTY)) {
				properties.load(is);
			} else if (file.endsWith(EXT_XML)) {
				properties.loadFromXML(is);
			} else {
				throw new IllegalArgumentException(
						"this is file is not correct extensions");
			}

			int storeysNumber = Integer.parseInt(properties
					.getProperty(Consts.KEY_STOREYS_NUMBER));
			int elevatorCapacity = Integer.parseInt(properties
					.getProperty(Consts.KEY_ELEVATOR_CAPACITY));
			int passengerNumber = Integer.parseInt(properties
					.getProperty(Consts.KEY_PASSENGER_NUMBER));
			int animationBoost = Integer.parseInt(properties
					.getProperty(Consts.KEY_ANIMATION_BOOST));
			if (storeysNumber < 2 || storeysNumber > MAX_STOREYS
					|| elevatorCapacity < 1 || elevatorCapacity > MAX_CAPACITY
					|| passengerNumber < 1 || passengerNumber > MAX_PASSENGERS
					|| animationBoost < 0 || animationBoost > 30) {

				throw new IllegalArgumentException();
			}

			return new ConfigBuilding(storeysNumber, elevatorCapacity,
					passengerNumber, animationBoost);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("errors parsing settings");
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
					"not correct settings or not correct extension(only xml or property)");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("not config file");
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static ConfigBuilding loadConfig(String[] args) {
		ConfigBuilding configBuilding;
		if (args.length == 0) {
			return getDefaultConfig;
		}else{
			try {
				configBuilding = LoadConfig.getConfig(args[0]);
			} catch (IOException e) {
				if (logger.isInfoEnabled()) {
					logger.info(e.getMessage()
							+ "\n will be load default configuration");
				}
				configBuilding = getDefaultConfig;
			}
		}
		return configBuilding;
	}

}
