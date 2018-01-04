package controller;

public class LogManager {
	
	/**
	 * 4 = error
	 * 3 = warning
	 * 2 = info
	 * 1 = debug
	 */

	private void logMessage(int level, String msg) {
		if (level >= Settings.getInstance().getLogLevel())
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
