package com.rest.xml;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.rest.bean.CheckoutResponse;

/**
 * 
 * @author Rania
 * 
 */
public class XmlUtil {
	/**
	 * 
	 * @param <type>
	 * @return 
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readXml(String xml, Class<T> type) throws JAXBException {
		// Create context class
		JAXBContext jaxbContext = JAXBContext.newInstance(type);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		T object = (T) jaxbUnmarshaller.unmarshal(new StringReader(
				xml));
		return object;
	}
}
