package net.ml.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import net.ml.data.tools.ZCSVFile;

@Slf4j
public class DataUtils {
    
    public static HashMap<String, String> loadBooksFromZip(String zipfile) throws Exception {
        ZCSVFile zip = new ZCSVFile(zipfile);
        File f = zip.getFile("books.csv");
        return loadBooks(f.getAbsolutePath());
    }

    public static HashMap<String, String> loadBooks(String filename) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String line = "";
        while ((line = in.readLine()) != null) {
            String parts[] = line.replace("\"", "").split(";");
            map.put(parts[0], parts[1]);
        }
        in.close();
        log.info("Total items: {}", map.size());
        return map;
    }

}
