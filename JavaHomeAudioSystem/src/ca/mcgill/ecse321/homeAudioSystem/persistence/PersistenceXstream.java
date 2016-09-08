package ca.mcgill.ecse321.homeAudioSystem.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

/**
 * Reads and writes the model using the Xstream library
 */
public class PersistenceXstream
{	
	private static XStream xstream = new XStream();
	private static String filename = "HASsetting.xml";
	
	/**
	 * Writes an object to a file on storage.
	 * @param obj the object to be written as xml and saved
	 * @return true if the write was successful
	 */
	public static boolean saveToXMLwithXStream(Object obj) 
	{
		xstream.setMode(XStream.ID_REFERENCES);
		String xml = xstream.toXML(obj); // save our xml file
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(xml);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Loads an object from a file on storage
	 * @return The loaded object if successful, otherwise null
	 */
	public static Object loadFromXMLwithXStream() 
	{
		xstream.setMode(XStream.ID_REFERENCES);
		try {
			FileReader fileReader = new FileReader(filename); // load our xml file
			return xstream.fromXML(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Pairs an xml tag with a class so objects can be identified and loaded as the proper class.
	 * @param xmlTagName The name of the class's tag in the xml hierarchy
	 * @param className The class that is represented by the xml tag
	 */
	public static void setAlias(String xmlTagName, Class<?> className)
	{
		xstream.alias(xmlTagName, className);
	}
	
	/**
	 * Sets the name of the file to be saved to on storage
	 * @param fn The file name with extention
	 */
	public static void setFilename(String fn)
	{
			filename = fn;
	}
}
