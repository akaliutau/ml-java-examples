package net.ml.models.impl;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import net.ml.models.DataModelProvider;

public class EmptyProvider implements DataModelProvider {

    @Override
    public DataModel getModel() throws Exception {
        // In-memory DataModel - GenericDataModels
        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();
         // Return preferences as new data model
        DataModel dataModel = new GenericDataModel(preferences);

        return dataModel;
    }

}
