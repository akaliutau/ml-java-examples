package net.ml.models.impl;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import net.ml.models.DataModelProvider;

public class DataModelInMem implements DataModelProvider {

    @Override
    public DataModel getModel() {
        // In-memory DataModel - GenericDataModels
        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();

        PreferenceArray prefsForUser1 = new GenericUserPreferenceArray(10);
        prefsForUser1.setUserID(0, 1L);
        prefsForUser1.setItemID(0, 101L);
        prefsForUser1.setValue(0, 3.0f);
        prefsForUser1.setItemID(1, 102L);
        prefsForUser1.setValue(1, 4.5F);
        preferences.put(1L, prefsForUser1); // use userID as the key

        // Return preferences as new data model
        DataModel dataModel = new GenericDataModel(preferences);

        return dataModel;
    }

}
