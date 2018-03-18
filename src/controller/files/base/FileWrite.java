package controller.files.base;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class FileWrite {

	/**
	 * Parses Object to XML format and writes to given File
	 * @param object to be converted
	 * @param file to be written
	 */
	public static void convertToXML(Object object, File file) {

		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(object, file);

		} catch (JAXBException e) {
			//TODO: log error
			e.printStackTrace();
		}
	}
}
