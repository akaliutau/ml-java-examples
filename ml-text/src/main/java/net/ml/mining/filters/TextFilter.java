package net.ml.mining.filters;

import java.io.File;
import java.io.FileFilter;

/** 
 * This class illustrates how to build a simple file filter 
 * 
 */
public class TextFilter implements FileFilter {

    /**
     * Test whether the string representation of the file ends with the correct
     * extension. Note that {@ref FileIterator} will only call this filter if the
     * file is not a directory, so we do not need to test that it is a file.
     */
    public boolean accept(File file) {
        return file.toString().endsWith(".txt");
    }
}
