package controller.files.base;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class FileRead {

	/**
	 * Reads the XML content of the file 
	 * @param object = the Class of XML type
	 * @param file = the File with the XML content
	 * @return unmarshalledContent = Object ready to be casted to right type 
	 */
	public static Object convertToClass(Object object, File file) {
		Object unmarshalledContent = null;

		if (FileBase.isFileEmpty(file)) {
			return object;
		}

		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();

			unmarshalledContent = unmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			//TODO: log - are we sure the content is XML?
			e.printStackTrace();
		}

		return unmarshalledContent;
	}
}
