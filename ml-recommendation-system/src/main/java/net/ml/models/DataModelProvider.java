package net.ml.models;

import org.apache.mahout.cf.taste.model.DataModel;

public interface DataModelProvider {
    DataModel getModel() throws Exception;
}
