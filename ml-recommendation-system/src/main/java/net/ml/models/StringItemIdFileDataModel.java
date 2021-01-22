package net.ml.models;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

import net.ml.migrators.ItemMemIDMigrator;

public class StringItemIdFileDataModel extends FileDataModel {
    private static final long serialVersionUID = -7414494521318872292L;

    public ItemMemIDMigrator memIdMigtr;

    public StringItemIdFileDataModel(File dataFile, String regex) throws IOException {
        super(dataFile, regex);
    }

    @Override
    public long readItemIDFromString(String value) {

        if (memIdMigtr == null) {
            memIdMigtr = new ItemMemIDMigrator();
        }

        long retValue = memIdMigtr.toLongID(value);
        if (null == memIdMigtr.toStringID(retValue)) {
            try {
                memIdMigtr.singleInit(value);
            } catch (TasteException e) {
                e.printStackTrace();
            }
        }
        return retValue;
    }

    public String getItemIDAsString(long itemId) {
        return memIdMigtr.toStringID(itemId);
    }
}
