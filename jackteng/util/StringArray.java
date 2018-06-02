package jackteng.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;

/**
 * Provides methods to conveniently load and save Strings.
 *
 * @author Aleksander Buczynski
 */
public class StringArray {

    public static String[] EMPTYARRAY = new String[0];

    /**
     * Loads an array of Strings, every line treated as a separate
     * String.  Empty strings are ignored.
     *
     * @param filename name of the file to load
     * @return an arrays of Strings representing the given file's content
     */
    public static String[] load(String filename) {
        ArrayList rv = new ArrayList();

        try {
            BufferedReader d = new BufferedReader(new FileReader(filename));
            String inputLine;

            while ((inputLine = d.readLine()) != null) {
                if (inputLine.length() > 0) {
                    rv.add(inputLine);
                }
            }
            d.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new String[0];
        }
        return (String[]) rv.toArray(new String[0]);
    }

    /**
     * Loads an array of Strings into a given Collection.  Every line
     * treated as a separate String, empty strings are ignored.
     *
     * @param filename name of the file to load
     * @param c a collection (set, list) to load the file into
     * @return number of Strings read
     */
    public static int load(String filename, Collection c) {
        int i = 0;
        try {
            BufferedReader d = new BufferedReader(new FileReader(filename));
            String inputLine;

            while ((inputLine = d.readLine()) != null) {
                c.add(inputLine);
                i++;
            }
            d.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return i;
    }
    /**
     * Loads dictionary of strings (keys and values separated
     * by Separator) into a given Dictionary.  Every line
     * treated as a separate String, empty strings are ignored.
     *
     * @param filename name of the file to load
     * @param dict a dictionary (Hashtable, ...) to load the file into
     * @param separator String which separates keys from values
     * @return number of Strings read
     */
    public static int load(String filename, Dictionary dict, String separator) {
        int i = 0;
        try {
            BufferedReader d = new BufferedReader(new FileReader(filename));
            String inputLine;

            while ((inputLine = d.readLine()) != null) {
            	String values[] = inputLine.split(separator);
            	if (values.length>1 ) {
            		dict.put(values[0], values[1]);
            		i++;
            	}
            }
            d.close();
        } catch (IOException e) {
            KStrings.error(e.getMessage(), null, false);
        }
        return i;
    }
    /**
     * Loads the first line from a give file.
     *
     * @param filename name of the file to load
     * @return a String representing the given file's first line
     */
    public static String loadOne(String filename) {
        try {
            BufferedReader d = new BufferedReader(new FileReader(filename));
            return d.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    public static short loadShort(String filename) {
        String inputLine;

        try {
            BufferedReader d = new BufferedReader(new FileReader(filename));
            inputLine = d.readLine();
            d.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return 0;
        }
        if (inputLine == null) {
            return 0;
        }
        return Short.parseShort(inputLine);
    }

    /**
     * Saves an array of Strings, every String in an array in a separate
     * line.
     *
     * @param filename name of the file to save to
     * @param a array of Strings to save
     * @return true if saved successfully, false otherwise
     */
    public static boolean save(String filename, String[] a) {
        try {
            PrintStream d;
            d = new PrintStream(new FileOutputStream(filename));
            for (int i = 0; i < a.length; i++) {
                d.println(a[i]);
            }
            d.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Saves a collection of Strings, every String in an array in a separate
     * line.
     *
     * @param filename name of the file to save to
     * @param a collection of Strings to save
     * @return true if saved successfully, false otherwise
     */
    public static boolean save(String filename, Collection a) {
        return save(filename, (String[]) a.toArray(EMPTYARRAY));
    }

    /**
     * Appends one line to a specified file.
     *
     * @param filename name of the file to save to
     * @param a String to save in the file
     * @return true if saved successfully, false otherwise
     */
    public static boolean writeOne(String filename, String a) {
        try {
            PrintStream d;
            d = new PrintStream(new FileOutputStream(filename, true));
            d.println(a);
            d.close();
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static boolean saveShort(String filename, short a) {
        try {
            PrintStream d;
            d = new PrintStream(new FileOutputStream(filename));
            d.println(a);
            d.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Saves a dictionary of Strings, every key and value in a separate
     * line using Separator.
     *
     * @param filename name of the file to save to
     * @param dict an array of Strings to save
     * @param separator separates keys from values in saved dictionary
     * @return true if saved successfully, false otherwise
     */
    public static boolean save(String filename, Dictionary dict, String separator) {
        try {
            PrintStream d;
            Object key, value;
            d = new PrintStream(new FileOutputStream(filename));
            
            for (Enumeration e = dict.keys() ; e.hasMoreElements() ;) {
                key = e.nextElement();
                value = dict.get(key);
                d.println(key.toString() + separator + value.toString());
            }
            d.close();
        } catch (IOException e) {
            KStrings.error(e.getMessage(),null,false);
            return false;
        }
        
        return true;
    }
}
