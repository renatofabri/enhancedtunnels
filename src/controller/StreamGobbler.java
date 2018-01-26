package controller;

import java.io.*;


class StreamGobbler extends Thread {
	static LogManager log = new LogManager();

    InputStream is;
    String type;
    
    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }
    
    public void run() {

        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null) {
            	if (type.equals("info")) {
            		log.info(line);
            	}
            	else if (type.equals("error")) {
            		log.error(line);
            	}
            	
            }

        } catch (IOException ioe) {
                ioe.printStackTrace();  
        }
    }
}