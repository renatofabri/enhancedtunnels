package controller.files.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileBase {

	/**
	 * Go to the given path and retrieve the file with the given name
	 * @param pathToParentFolder = Path where the file is located
	 * @param filename = Name of the file
	 * @return File instance
	 */
	public static File getFile(String pathToParentFolder, String filename) {

		File parentFolder = null;
		File file = null;

		try {

			parentFolder = new File(pathToParentFolder);
			
			if (Files.notExists(Paths.get(pathToParentFolder))) parentFolder.mkdirs();
			
			file = new File(pathToParentFolder, filename);

			if (!(file.exists() && !file.isDirectory())) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * Check if the provided file has any content or not
	 * @param file = File instance
	 * @return true if there is any content in the file, else false
	 */
	public static boolean isFileEmpty(File file) {

		boolean isEmpty = false;

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			if (br.readLine() == null) {
			    isEmpty = true;
			}

			br.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return isEmpty;
	}
}
