package demo;

import java.io.*;

/**
 * An Exception class used in conjuntion with the XMLHelper class.
 * @see java.lang.Exception
 * @see XMLHelper
 * @author Jared Jackson, Email: <a mailto="jjared@almaden.ibm.com">jjared@almaden.ibm.com</a>
 */

public class XMLHelperException extends Exception {
	private Exception innerException;
	
	public XMLHelperException() { super(); innerException = null; }
	public XMLHelperException(String message) { super(message); innerException = null; }
	public XMLHelperException(String message, Exception innerException) {
		super(message);
		this.innerException = innerException;
	}
	
	public String getMessage() {
		String message = super.getMessage() + "\n" + 
			   (innerException == null ? "" : innerException.getMessage());
		return message;
	}
	
	public void printStackTrace(PrintStream ps) {
		super.printStackTrace(ps);
		innerException.printStackTrace(ps);
	}
}
