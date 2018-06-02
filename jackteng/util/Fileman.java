package jackteng.util;

import java.io.File;
import java.io.FilenameFilter;
import java.text.NumberFormat;

/**
 * Filter for text samples (elements)
 */
class SamplesFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        if (name.length() == 9 && name.endsWith(".txt"))
            return true;
        return false;
    }
}


/**
 * Class to manage simple operations on the archive directory.
 */

public class Fileman {

    public String dir;

    File f;

    NumberFormat filenoFormat;

    FilenameFilter filter;

    public Fileman(String workdir) {
        dir = workdir;
        if (!dir.endsWith("/") && !dir.endsWith("\\"))
            dir = dir + "/"; // File.separator;
        f = new File(dir);
        filenoFormat = NumberFormat.getInstance();
        filenoFormat.setMinimumIntegerDigits(5);
        filenoFormat.setGroupingUsed(false);
        filter = new SamplesFilter();
    }

    public String name(short pageno) {
        return dir + filenoFormat.format(pageno) + ".txt";
    }

    public String sname(short pageno) {
        return filenoFormat.format(pageno) + ".txt";
    }

    public short no(String filename) {
        try {
            return Short.parseShort(filename
                    .substring(0, filename.length() - 4));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * The managed directory.
     */
    public String dir() {
        return dir;
    }

    public String path(String filename) {
        return dir + filename;
    }

    public boolean exists(String filename) {
        File f = new File(dir + filename);
        return f.exists() && f.length() > 0;
    }

    public String optionalPath(String filename) {
        if (new File(dir + filename).exists())
            return dir + filename;
        else
            return "kolokacje" + File.separator + filename;
    }

    public String[] list() {
        return f.list(filter);
    }

    public void delall() {
        File[] l = f.listFiles(filter);
        for (int i = 0; i < l.length; i++)
            l[i].delete();
    }

    public void delete(String filename) {
        File d = new File(dir + filename);
        d.delete();
    }
}

