package com.rest.client.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper Class to load the properties file needed by this application
 * @author Rania
 *
 */

public class ConfigHelper {
	private Properties prop = new Properties();
	private boolean isLoaded = false;
	String propFileName = "config.properties";
	private static ConfigHelper configHelper;
	
	/**
	 * Prevent creation instances (singleton class)
	 */
	private ConfigHelper() {
	}
	
	/**
	 * Get the single instance form this class
	 * @return
	 */
	public static ConfigHelper getInstance() {
		if(configHelper == null) {
			configHelper = new ConfigHelper();
			configHelper.loadConfigProperties();
			configHelper.isLoaded = true;
		} 
		
		if(!configHelper.isLoaded) {
			configHelper.loadConfigProperties();
		}		
		return configHelper;
	}

	/**
	 * Load config file
	 */
	private void loadConfigProperties() {
		try {
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return host name defined in the config file
	 * @return
	 */
	public String getHostName() {
		return prop.getProperty("serviecHost", "http://localhost:8080/");		
	}
	
	/**
	 * Return checkoutServiceName value
	 * @return
	 */
	public String getCheckoutServiceName() {
		return prop.getProperty("checkoutServiceName", "RESTService/checkoutService/checkout");
	}
}
