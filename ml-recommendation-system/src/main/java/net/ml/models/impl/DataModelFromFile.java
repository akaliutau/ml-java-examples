package net.ml.models.impl;

import java.io.File;

import org.apache.mahout.cf.taste.model.DataModel;

import net.ml.data.tools.ZCSVFile;
import net.ml.models.DataModelProvider;
import net.ml.models.StringItemIdFileDataModel;

public class DataModelFromFile implements DataModelProvider {
    
    static String bxbooks = "./../data/books.zip";
    static String bxbookrate = "./../data/book-ratings.zip";

    @Override
    public DataModel getModel() throws Exception {
        ZCSVFile zip = new ZCSVFile(bxbookrate);
        File f = zip.getFile("book-ratings.csv");
        return loadFromFile(f.getAbsolutePath(), ";");
    }
     
    private static DataModel loadFromFile(String filePath, String seperator) throws Exception {
        StringItemIdFileDataModel dataModel = new StringItemIdFileDataModel(new File(filePath), seperator);
        return dataModel;
    }

}
