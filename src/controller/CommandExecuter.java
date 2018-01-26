package controller;

public class CommandExecuter {

	static LogManager log = new LogManager();

	public static void run(String args[]) {
		try {
			String[] cmd = new String[3];

			String osName = System.getProperty("os.name");

			if (osName.equals("Windows NT")) {
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
			}
			else if (osName.equals("Windows 95")) {
				log.error("Unsupported OS.");
				cmd[0] = "command.com";
				cmd[1] = "/C";
				return;
			}
			cmd[2] = args[0]; // putty execution

			log.info("Executing " + cmd);

			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(cmd);

			//error handler
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "error");

			//output handler
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "info");

			errorGobbler.start();
			outputGobbler.start();

			log.info("Exit value: " + proc.waitFor());

		}
		catch (Throwable t) {
			log.error(t.getStackTrace().toString());
			t.printStackTrace();
		}
	}
}
