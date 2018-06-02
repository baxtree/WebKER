package jackteng.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.TreeMap;
import java.text.MessageFormat;

/**
 * Configuration class, containing a set of variables with values.
 */
public class Config {

    /**
     * The name of the default configuration file.
     */
    public static String configFilename = "config.ini";
    public static String configLocation = "kolokacje"+File.separator;

    protected TreeMap dict;

    /**
     * Creates a new configuration with no variables.
     */
    public Config() {
        dict = new TreeMap();
    }

    /**
     * Creates a new configuration with variables loaded from a given filename.
     */
    public Config(String filename) {
        dict = new TreeMap();
        load(filename, false);
    }

    /**
     * Creates a new configuration with variables loaded from both generic
     * and local configuration file.
     */
    public Config(Fileman fm) {
        dict = new TreeMap();
        load(configLocation + configFilename, false);
        load(fm.path(configFilename), true);
    }

    /**
     * Loads configuration from a specified file.
     *
     * @param filename name of the configuration file
     * @param silent if file missing error should be reported
     */
    public void load(String filename, boolean silent) {
        int i, line = 1;

        try {
            BufferedReader d;
            d = new BufferedReader(new InputStreamReader(new FileInputStream(
                    filename), "windows-1250"));
            String inputLine;

            while ((inputLine = d.readLine()) != null) {
                line++;
                // empty line
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
                if (i == inputLine.length()) {
                    Object txt[] = {filename, new Integer(line)};
                    System.err.println(MessageFormat.format(
                            KStrings.getString("Config_line_is_not_assignment"), txt));
                    continue;
                }
                put(inputLine.substring(0, i).trim(),
                    inputLine.substring(i + 1).trim());
            }
            d.close();
        } catch (IOException e) {
//            System.err.println(KStrings.getString("Config_load_") + e.getMessage());
        }
    }

    /**
     * Sets value of a configuration variable.
     *
     * @param var name of the variable to set
     * @param value value of the variable as String
     */
    public void put(String var, String value) {
        dict.put(var, value);
    }

    /**
     * Sets value of a configuration variable.
     *
     * @param assign assignment in a form of <code>variable=value</code>
     * @return true, if assign is valid assignment, false otherwise
     */
    public boolean putAssignment(String assign) {
        int i;
        if (assign.length() == 0) {
            return false;
        }

        for (i = 1; i < assign.length(); i++) {
            if (assign.charAt(i) == '=') {
                break;
            }
        }

        if (i == assign.length()) {
            Object txt[] = {assign};
            System.err.println(MessageFormat.format(
                    KStrings.getString("Config_is_not_assignment"), txt));
            return false;
        }

        put(assign.substring(0, i).trim(),
            assign.substring(i + 1).trim());

        return true;
    }

    /**
     * Gets the value of a configuration String variable.
     *
     * @param var name of the variable to get
     * @return value of the variable
     */
    public String get(String var) {
        String s = (String) dict.get(var);
        if (s != null) {
            return s;
        } else {
            return notFound(var);
        }
    }

    /**
     * Gets the value of a configuration int variable.
     *
     * @param var name of the variable to get
     * @return value of the variable
     */
    public int getInt(String var) {
        return Integer.parseInt(get(var));
    }

    /**
     * Gets the value of a configuration hexadecimal variable.
     *
     * @param var name of the variable to get
     * @return value of the variable
     */
    public int getHex(String var) {
        return Integer.parseInt(get(var), 16);
    }

    /**
     * Gets the value of a configuration boolean variable.
     *
     * @param var name of the variable to get
     * @return value of the variable
     */
    public boolean getBool(String var) {
        String s = get(var);
        if (s.equalsIgnoreCase("true")) {
            return true;
        }
        if (s.equalsIgnoreCase("yes")) {
            return true;
        }
        if (s.equalsIgnoreCase("on")) {
            return true;
        }
        if (s.equalsIgnoreCase("1")) {
            return true;
        }

        if (s.equalsIgnoreCase("false")) {
            return false;
        }
        if (s.equalsIgnoreCase("no")) {
            return false;
        }
        if (s.equalsIgnoreCase("off")) {
            return false;
        }
        if (s.equalsIgnoreCase("0")) {
            return false;
        }

        Object txt[] = {var};
        System.err.println(MessageFormat.format(
                KStrings.getString("Config_not_a_proper_bool"), txt));
        return false;
    }

    /**
     * Method called when a requested variable can not be found.  Can
     * be overriden by subclasses to allow a "plan B".
     *
     * @param var name of the variable requested
     * @return null
     */
    public String notFound(String var) {
        Object txt[] = {var};
        System.err.println(MessageFormat.format(KStrings.getString("Config_var_not_found"), txt));
        return null;
    }

    /**
     * Deletes all variables
     */
    public void clear() {
        dict = new TreeMap();
    }

}
