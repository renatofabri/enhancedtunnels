package controller;

public class LogManager {
	
	/**
	 * 4 = error
	 * 3 = warning
	 * 2 = info
	 * 1 = debug
	 */
	private static final int LOG_LEVEL = 1;

	private void logMessage(int level, String msg) {
		if (level >= LOG_LEVEL)
			System.out.println(msg);
	}

	public void error(String msg) {
		logMessage(4, "ERROR: " + msg);
	}

	public void warning(String msg) {
		logMessage(3, "WARN: " + msg);
	}
	
	public void info(String msg) {
		logMessage(2, "INFO: " + msg);
	}
	
	public void debug(String msg) {
		logMessage(1, "DEBUG: " + msg);
	}
}
