package jackteng.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides localized Strings for whole Kolokacje
 */
public class KStrings {

    /** Resource bundle that contains all the strings */
    private static ResourceBundle bundle;

    /**
     * Reads set locale from config file
     * @return String locale or null if no locale entry in config.ini
     */
    private static String getLocaleFromConfig() {
        String rv;
        BufferedReader cf;

        try {
            String f = Config.configLocation + Config.configFilename;

            cf = new BufferedReader(new InputStreamReader(new
                    FileInputStream(f), "windows-1250"));

            String inputLine;
            while ((inputLine = cf.readLine()) != null) {
                int i;

                if (inputLine.length() == 0) {
                    continue;
                }
                // comment
                if (inputLine.charAt(0) == '#' || inputLine.charAt(0) == ';') {
                    continue;
                }
                // find =
                for (i = 1; i < inputLine.length(); i++) {
                    if (inputLine.charAt(i) == '=') {
                        break;
                    }
                }
                // line is not an assignment
                if (i == inputLine.length()) {
                    continue;
                }

                // locale ?
                if (inputLine.substring(0, i).trim().equals("locale")) {
                    rv = inputLine.substring(i + 1).trim();
                    return rv;
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * Parses <code>loc</code> string into Locale. Fields may be separated by "_" or "-"
     * @param loc String string with locale
     * @return Locale parsed locale
     */
    public static Locale parseLocale(String loc) {

        // we have to check replace all "_" with "-" first
        loc = loc.replaceAll("-", "_");

        // then parse locale
        String rr[] = loc.split("_", 3);
        if (rr.length == 1) {
            return new Locale(rr[0]);
        }
        if (rr.length == 2) {
            return new Locale(rr[0], rr[1]);
        }
        //  if (rr.length == 3)
        return new Locale(rr[0], rr[1], rr[2]);

    }

    /**
     * Creates ResourceBundle to provide localized strings.
     * Locale is taken from config file.
     * If information is not available, default system locale is used.
     */
    public static void generateBundle() {
        Locale locale;

        String locString = getLocaleFromConfig();

        if (locString != null) {
            locale = parseLocale(locString);
        } else {
            locale = Locale.getDefault();
        }

        bundle = ResourceBundle.getBundle("kolokacje.Bundle", locale);
    }

    /**
     * Creates ResourceBundle to provide localized strings.
     * @param locale Locale to use
     */
    public static void generateBundle(Locale locale) {
        bundle = ResourceBundle.getBundle(
                "kolokacje.Bundle", locale);
    }

    /**
     * Returns a localized String for a key.
     * If ResourceBundle is not initialized yet,
     * it's generated basing on config file if available.
     * If not, default system locale is used.
     */
    public synchronized static String getString(String key) {
        if (bundle == null) {
            generateBundle();
        }

        return bundle.getString(key);
    }
    /**
     * Gets localized string for WHAT and writes it to System.out
     * @param what String to write
     */
    public static void log(String what, boolean writeln) {
    	String msg = KStrings.getString(what);
    	if (writeln) System.out.println(msg); else System.out.print(msg);
    	
    }
    /**
     * Gets localized string for WHAT, formats it with TXT and writes 
     * it to System.out.
     * @param what String to write
     * @param txt Message parameters
     */
    public static void log(String what, Object txt[], boolean writeln) {
    	String msg = MessageFormat.format(
    			KStrings.getString(what),
    			txt);
    	if (writeln) System.out.println(msg); else System.out.print(msg);
    }
    
    /**
     * Gets localized (or not) string for WHAT, formats it with TXT and writes 
     * it to System.err.
     * @param what String to write
     * @param txt Message parameters
     */
    public static void error(String what, Object txt[], 
    		boolean localize) {
    	String msg;
    	if (localize) {
    		msg = KStrings.getString(what);
    	} else {
    		msg = what;
    	}
    		
    	if (txt != null) {
    		msg = MessageFormat.format(msg,txt);
    	}
    	
    	System.err.println(msg);
    }
}
