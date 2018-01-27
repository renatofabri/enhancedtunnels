package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.Settings;

public class LogManager {
	
	/**
	 * 5 = fatal
	 * 4 = error
	 * 3 = warning
	 * 2 = info
	 * 1 = debug
	 */

	public static final Path ROOT_FOLDER = Paths.get(new File("").getAbsolutePath(), "log");

	private void logToFile(String msg) {

		File file = new File(ROOT_FOLDER.toString());
		if (Files.notExists(ROOT_FOLDER)) file.mkdirs();

		try {
			String currDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

			FileWriter fw = new FileWriter(ROOT_FOLDER.toString() + "\\" + currDate + ".log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			String currTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			System.out.println(currTime + " :: " + msg);
		    out.println(currTime + " :: " + msg);

		    out.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
	}

	private void logMessage(int level, String msg) {
		if (level >= Settings.getInstance().getLogLevel())
			logToFile(msg);
	}

	public void fatal(String msg) {
		logMessage(5, "FATAL :: " + msg);
	}

	public void error(String msg) {
		logMessage(4, "ERROR :: " + msg);
	}

	public void warning(String msg) {
		logMessage(3, "WARNING :: " + msg);
	}
	
	public void info(String msg) {
		logMessage(2, "INFO :: " + msg);
	}
	
	public void debug(String msg) {
		logMessage(1, "DEBUG :: " + msg);
	}
}
