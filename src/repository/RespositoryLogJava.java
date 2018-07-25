package repository;

import java.util.logging.Logger;

public class RespositoryLogJava {

	private final static String LOGGER_NAME = "SortMp3";
	private final static Logger logger = Logger.getLogger(LOGGER_NAME);

	
	public static Logger getLogger() {

		return logger;
	}

}
