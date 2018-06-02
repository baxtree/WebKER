package demo;

//Import packages dealing with XML parsing and representation
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.tidy.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XMLHelper is a class designed to provide some generic utility functions for
 * working with HTML, XHTML, XML, and XSL. All methods contained herein are
 * static, so no instantiation of this class is ever necessary. The methods deal
 * with parsing, input/output, retrieving files from the network, and
 * transformation and clean-up of documents.
 * 
 * @author Jared Jackson, Email: <a
 *         mailto="jjared@almaden.ibm.com">jjared@almaden.ibm.com</a>
 * @see XMLHelperException
 */

public class XMLHelper {

	/**
	 * This method creates a default XML document. The document is empty except
	 * for a single root element, with tag name as specified by the parameter.
	 * 
	 * @param rootName
	 *            The name of the root element of the XML document. If <CODE>null</CODE>
	 *            or empty, no root element is added to the document.
	 * @return An empty XML document, save possibly a single root node.
	 */

	public static Document createXml(String rootName) {
		Document doc = new DocumentImpl();
		if (rootName == null || rootName.trim().equals(""))
			return doc;
		doc.appendChild(doc.createElement(rootName));
		return doc;
	}

	/**
	 * Given an <CODE>URL</CODE> as a <CODE>String</CODE>, this method
	 * retrieves the file located at that URL, and attempts to parse it as XML.
	 * 
	 * @param url
	 *            A URL encoding such as "http://www.ibm.com/someXML.xml" of the
	 *            target document
	 * @return A parsed XML document found at the given URL
	 * @exception XMLHelperException
	 *                Thrown if the URL is malformed, the file at the given URL
	 *                can not be obtained, or the file found is not valid XML.
	 */

	public static Document parseXMLFromURLString(String url)
			throws XMLHelperException {
		return parseXMLFromURL(convertStringToURL(url));
	}

	/**
	 * Given an <CODE>URL</CODE>, this method retrieves the file located at
	 * that URL, and attempts to parse it as XML.
	 * 
	 * @param url
	 *            A <CODE>URL</CODE> java class instantiation of the target
	 *            document
	 * @return A parsed XML document found at the given URL
	 * @exception XMLHelperException
	 *                Thrown if the URL is malformed, the file at the given URL
	 *                can not be obtained, or the file found is not valid XML.
	 */

	public static Document parseXMLFromURL(URL url) throws XMLHelperException {
		try {
			URLConnection inConnection = url.openConnection();
			InputSource is = new InputSource(inConnection.getInputStream());
			return parseXMLFromInputSource(is);
		} catch (IOException ioe) {
			throw new XMLHelperException("Unable to read from source string",
					ioe);
		}
	}

	/**
	 * Given an XML document currently unparsed in the form of a <CODE>String</CODE>,
	 * this method attempts to parse the content of that <CODE>String</CODE>
	 * as XML.
	 * 
	 * @param source
	 *            A <CODE>String</CODE> encoding of a XML document.
	 * @return A parsed XML document
	 * @exception XMLHelperException
	 *                Thrown if the string given is not valid XML.
	 */

	public static Document parseXMLFromString(String source)
			throws XMLHelperException {
		InputSource is = new InputSource(new StringReader(source));
		return parseXMLFromInputSource(is);
	}

	/**
	 * Given an XML document pointed to by a <CODE>File</CODE> object, this
	 * method attemps to read the file and parse it as XML.
	 * 
	 * @param sourceFile
	 *            A <CODE>File</CODE> object referencing an XML file.
	 * @return A parsed XML document
	 * @exception XMLHelperException
	 *                Thrown if the file is unreadable or the file does not
	 *                contain a valid XML document
	 */

	public static Document parseXMLFromFile(File sourceFile)
			throws XMLHelperException {
		InputSource is = null;
		try {
			is = new InputSource(new FileInputStream(sourceFile));
		} catch (IOException ioe) {
			throw new XMLHelperException("The XML file could not be retrieved",
					ioe);
		}
		return parseXMLFromInputSource(is);
	}

	/**
	 * Given an XML document pointed to by a file path expression, this method
	 * attemps to read the file and parse it as XML.
	 * 
	 * @param sourceFile
	 *            An absolute or relative file path expression.
	 * @return A parsed XML document
	 * @exception XMLHelperException
	 *                Thrown if the file is unreadable or the file does not
	 *                contain a valid XML document
	 */

	public static Document parseXMLFromFile(String sourceFile)
			throws XMLHelperException {
		FileInputStream is = null;
		InputSource newis = null;
		try {
			// System.out.println("dfdfsdf");
			File f = new File(sourceFile);
			is = new FileInputStream(f);

			// is = new InputSource(new FileInputStream(sourceFile));
			InputStreamReader isr = new InputStreamReader(is, "UTF8");
			StringBuffer s = new StringBuffer();
			int b;
			while ((b = isr.read()) != -1) {
				s.append((char) b);
			}
			// System.out.print(s.toString());
			// out.close();

			// String outContent = out.toString();

			InputStream in = new ByteArrayInputStream(s.toString().getBytes(
					"UTF8"));

			newis = new InputSource(in);

			isr.close();
		} catch (IOException ioe) {
			throw new XMLHelperException("The XML file could not be retrieved",
					ioe);
		}
		return parseXMLFromInputSource(newis);
	}

	// This is the real work horse around XML parsing, the public methods each
	// attempt to
	// create InputSource objects, then call this method for parsing

	private static Document parseXMLFromInputSource(InputSource is)
			throws XMLHelperException {
		Document doc = null;

		try {
			DOMParser parser = new DOMParser();
			parser.parse(is);
			doc = parser.getDocument();
		} catch (IOException ioe) {
			throw new XMLHelperException("Unable to read from source string",
					ioe);
		} catch (SAXException saxe) {
			throw new XMLHelperException("Unable to parse the given string",
					saxe);
		}
		return doc;
	}

	/**
	 * Given two XML documents, one the target XML file and one an XSL file,
	 * this method applies an XSL transform defined by the XSL file on the XML
	 * file and returns the resulting document.
	 * 
	 * @param xmlDoc
	 *            The source XML file
	 * @param xslDoc
	 *            An XML file that also follows the XSL transformation language
	 *            specification
	 * @return The document resulting from applying xslDoc to xmlDoc.
	 * @exception XMLHelperException
	 *                Thrown if the XSL document is either poorly formed as XSL
	 *                or if it encounters an error during transformation.
	 */

	/*
	 * public static Document transformXML(Document xmlDoc, Document xslDoc)
	 * throws XMLHelperException { try { XSLTInputSource xmlIn = new
	 * XSLTInputSource(xmlDoc); XSLTInputSource xslIn = new
	 * XSLTInputSource(xslDoc);
	 * 
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * XSLTResultTarget xmlOut = new XSLTResultTarget(baos);
	 * 
	 * XSLTProcessor processor = XSLTProcessorFactory.getProcessor();
	 * processor.process(xmlIn, xslIn, xmlOut); baos.close(); String result =
	 * baos.toString();
	 * 
	 * return parseXMLFromString(result); } catch (SAXException saxe) { throw
	 * new XMLHelperException("Unable to perform transform", saxe); } catch
	 * (IOException ioe) { throw new XMLHelperException("Unable to perform
	 * transform", ioe); } }
	 */

	public static Document transformXML(Document xmlDoc, Document xslDoc)
			throws Exception {
		// try{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// OutputStream baos = new OutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF8");
		StringBuffer s = new StringBuffer();

		// out.close();
		// String outContent = out.toString();
		// System.out.println(xmlDoc.getFirstChild().getLocalName());
		Source xmlIn = new DOMSource(xmlDoc);
		Source xslIn = new DOMSource(xslDoc);
		Result xmlOut = new StreamResult(osw);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer(xslIn);
		// TransformerImpl t = (TransformerImpl)transformer;
		// final SerializationHandler toHandler = t.getOutputHandler(xmlOut);
		// System.out.println(transformer.getOutputProperties());
		// transformer.setOutputProperty("_encoding", "UTF8");
		transformer.transform(xmlIn, xmlOut);
		// }catch(Exception e){}
		baos.close();
		String result = baos.toString("utf8");
		// System.out.println(result);
		osw.close();
		return parseXMLFromString(result);

	}

	/**
	 * Given an XML document, a pretty (tab delimited and with line breaks)
	 * representation is sent to the specified <CODE>PrintStream</CODE>
	 * object. This is the most convenient way to output an XML document to
	 * standard out.
	 * 
	 * @param doc
	 *            The XML document to output
	 * @param stream
	 *            The stream to send the result to. (e.g. <CODE>System.out</CODE>
	 *            or <CODE>System.err</CODE>)
	 * @exception XMLHelperException
	 *                Thrown in the event of an I/O error.
	 */

	public static void outputXML(Document doc, PrintStream stream)
			throws XMLHelperException {
		try {
			OutputFormat of = new OutputFormat(doc);
			of.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(stream, of);
			serializer.serialize(doc);
		} catch (IOException ioe) {
			throw new XMLHelperException(
					"Unable to write to the given print stream", ioe);
		}
	}

	/**
	 * Given an XML document and a relative or absolute path name for a file,
	 * writes the XML document to that file location. The format of the written
	 * XML document will be tab delimited and line breaked. The file name will
	 * need to use the system dependent separator character(s) for directory
	 * navigation.
	 * 
	 * @param doc
	 *            The XML document to output.
	 * @param fileName
	 *            A file name either relative to the running Java virtual
	 *            machine, or absolute.
	 * @exception XMLHelperException
	 *                Thrown if an I/O error occurs.
	 */

	public static void outputXMLToFile(Document doc, String fileName)
			throws XMLHelperException {
		try {
			OutputFormat of = new OutputFormat(doc);
			of.setIndenting(true);
			File f = new File(fileName);
			FileOutputStream fos = new FileOutputStream(f);
			// System.out.println(fos.toString());
			XMLSerializer serializer = new XMLSerializer(fos, of);
			serializer.serialize(doc);
			fos.close();
		} catch (IOException ioe) {
			throw new XMLHelperException("Unable to write to the given file",
					ioe);
		}
	}

	/**
	 * A utility method for converting an XML document to a <CODE>String</CODE>
	 * object. This method is included in case the user would like to do their
	 * own I/O in a way not specified in this class.
	 * 
	 * @param doc
	 *            The XML document to be encoded as a <CODE>String</CODE>.
	 * @return The XML document as text in a <CODE>String</CODE>.
	 */

	public static String convertXMLToString(Document doc)
			throws XMLHelperException {
		try {
			OutputFormat of = new OutputFormat(doc);
			of.setIndenting(true);
			StringWriter sw = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(sw, of);
			serializer.serialize(doc);
			return sw.toString();
		} catch (IOException ioe) {
			throw new XMLHelperException("Unable to write to the string", ioe);
		}
	}

	/**
	 * <P>
	 * Copies the content of one XML <CODE>Element</CODE> to another. By
	 * setting the <CODE>childrenOnly</CODE> parameter to <CODE>false</CODE>,
	 * the element is simply imported as a child to the original element. If the
	 * parameter is instead <CODE>true</CODE>, then the children of the
	 * element are imported as children to the original element.
	 * </P>
	 * 
	 * <P>
	 * For instance, let the <CODE>mergeToXML</CODE> parameter be the XML:
	 * </P>
	 * <BLOCKQUOTE>
	 * 
	 * <PRE>
	 * 
	 * &lt;Original&gt &lt;/Original&gt;
	 * 
	 * </PRE>
	 * 
	 * </BLOCKQUOTE>
	 * <P>
	 * And let the <CODE>mergeFromXML</CODE> parameter be the XML:
	 * </P>
	 * <BLOCKQUOTE>
	 * 
	 * <PRE>
	 * 
	 * &lt;Target&gt; &lt;Child&gt;Child 1&lt;/Child&gt; &lt;Child&gt;Child
	 * 2&lt;/Child&gt; &lt;/Target&gt;
	 * 
	 * </PRE>
	 * 
	 * </BLOCKQUOTE>
	 * <P>
	 * If <CODE>childrenOnly</CODE> is set to <CODE>false</CODE>, the
	 * result is:
	 * </P>
	 * <BLOCKQUOTE>
	 * 
	 * <PRE>
	 * 
	 * &lt;Original&gt &lt;Target&gt; &lt;Child&gt;Child 1&lt;/Child&gt;
	 * &lt;Child&gt;Child 2&lt;/Child&gt; &lt;/Target&gt; &lt;/Original&gt
	 * 
	 * </PRE>
	 * 
	 * </BLOCKQUOTE>
	 * <P>
	 * Otherwise, if <CODE>childrenOnly</CODE> is set to <CODE>true</CODE>,
	 * the result is:
	 * </P>
	 * <BLOCKQUOTE>
	 * 
	 * <PRE>
	 * 
	 * &lt;Original&gt &lt;Child&gt;Child 1&lt;/Child&gt; &lt;Child&gt;Child
	 * 2&lt;/Child&gt; &lt;/Original&gt
	 * 
	 * </PRE>
	 * 
	 * </BLOCKQUOTE>
	 * 
	 * @param mergeToXML
	 *            The element into which the XML will be inserted as children.
	 * @param mergeFromXML
	 *            The element from which the XML will be copied and imported.
	 * @param childrenOnly
	 *            If <CODE>true</CODE> grab the children and ignore the
	 *            parent. If <CODE>false</CODE> grab everything.
	 */

	public static void mergeXML(Element mergeToXML, Element mergeFromXML,
			boolean childrenOnly) {
		Document toDoc = mergeToXML.getOwnerDocument();
		Element copyElem = (Element) (toDoc.importNode(mergeFromXML, true));
		if (childrenOnly) {
			NodeList nlist = copyElem.getChildNodes();
			for (int i = 0; i < nlist.getLength(); i++) {
				org.w3c.dom.Node n = nlist.item(i);
				mergeToXML.appendChild(n);
			}
			return;
		} else {
			mergeToXML.appendChild(copyElem);
		}
	}

	/**
	 * Retrieves an HTML page from a URL encoded as a <CODE>String</CODE> and
	 * attempts to clean up the source of that HTML to remove author errors. If
	 * successful, the resulting document is converted to XHTML and returned as
	 * an XML document.
	 * 
	 * @param url
	 *            A <CODE>String</CODE> encoding of a URL (e.g.
	 *            "http://www.ibm.com/index.html").
	 * @return an XML document representing the XHTML of the source of the HTML
	 *         file.
	 * @exception XMLHelperException
	 *                Thrown if the URL is malformed, the HTML source can not be
	 *                obtained, or the tool is unable to convert the source to
	 *                XML.
	 */

	public static Document tidyHTML(String url) throws XMLHelperException {
		return tidyHTML(convertStringToURL(url));
	}

	/**
	 * Retrieves an HTML page from a java <CODE>URL</CODE> object and attempts
	 * to clean up the source of that HTML to remove author errors. If
	 * successful, the resulting document is converted to XHTML and returned as
	 * an XML document.
	 * 
	 * @param url
	 *            A <CODE>URL</CODE> object hopefully pointing to an HTML
	 *            file.
	 * @return an XML document representing the XHTML of the source of the HTML
	 *         file.
	 * @exception XMLHelperException
	 *                Thrown if the HTML source can not be obtained or the tool
	 *                is unable to convert the source to XML.
	 */
	public static Document tidyHTML(URL url) throws XMLHelperException {
		try {
			URLConnection inConnection = url.openConnection();
			if (inConnection.getContentType().startsWith("text/xml")
					|| inConnection.getContentType().startsWith("text/xhtml")) {
				// All ready an XML source
				return parseXMLFromURL(url);
			} else if (inConnection.getContentType().startsWith("text/html")) {
				// An HTML source
				InputStream is = inConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF8");
				// Clean the input stream
				// ByteArrayOutputStream out = new ByteArrayOutputStream();

				// int totalBytes = 0;
				// byte[] buffer = new byte[16384];
				//				
				// while (true) {
				// int bytesRead = is.read(buffer, 0, buffer.length);
				// if (bytesRead < 0) break;
				//					
				// // Remove binary below space except tab and newline
				// for (int i=0; i < bytesRead; i++) {
				// byte b = buffer[i];
				// if (b < 32 && b!= 10 && b != 13 && b != 9) b = 32;
				// buffer[i] = b;
				// }
				// out.write(buffer, 0, bytesRead);
				// totalBytes += bytesRead;
				// }
				StringBuffer s = new StringBuffer();
				int b;
				while ((b = isr.read()) != -1) {
					s.append((char) b);
				}
				is.close();
				// out.close();
				isr.close();
				// String outContent = out.toString();

				InputStream in = new ByteArrayInputStream(s.toString()
						.getBytes("gbk"));

				org.w3c.tidy.TagTable tags = org.w3c.tidy.TagTable
						.getDefaultTagTable();
				tags.defineBlockTag("script");

				Tidy tidy = new Tidy();

				tidy.setShowWarnings(false);
				tidy.setXmlOut(true);
				tidy.setXmlPi(false);
				tidy.setDocType("omit");
				tidy.setXHTML(false);
				tidy.setRawOut(true);
				tidy.setNumEntities(true);
				tidy.setQuiet(true);
				tidy.setFixComments(true);
				tidy.setIndentContent(true);
				tidy.setCharEncoding(org.w3c.tidy.Configuration.RAW);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				org.w3c.tidy.Node tNode = tidy.parse(in, baos);
				String result = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
						+ baos.toString();

				// System.out.print(result);
				// Strip the DOCTYPE and script elements - This is an optional
				// step
				int startIndex = 0;
				int endIndex = 0;
				if ((startIndex = result.indexOf("<!DOCTYPE")) >= 0) {
					endIndex = result.indexOf(">", startIndex);
					result = result.substring(0, startIndex)
							+ result.substring(endIndex + 1, result.length());
				}
				while ((startIndex = result.indexOf("<script")) >= 0) {
					endIndex = result.indexOf("</script>");
					result = result.substring(0, startIndex)
							+ result.substring(endIndex + 9, result.length());
				}

				in.close();
				baos.close();

				return parseXMLFromString(result);

			} else {
				throw new XMLHelperException("Unable to tidy content type: "
						+ inConnection.getContentType());
			}
		} catch (IOException ioe) {
			throw new XMLHelperException("Unable to perform input/output", ioe);
		}
	}

	// A utility method for converting a String encoding of a URL to a URL

	private static URL convertStringToURL(String url) throws XMLHelperException {
		try {
			return new URL(url);
		} catch (MalformedURLException murle) {
			throw new XMLHelperException(url + " is not a well formed URL",
					murle);
		}
	}
}
