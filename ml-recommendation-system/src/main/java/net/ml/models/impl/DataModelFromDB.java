package net.ml.models.impl;

import org.apache.mahout.cf.taste.impl.model.jdbc.PostgreSQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.postgresql.ds.PGSimpleDataSource;

import net.ml.models.DataModelProvider;

public class DataModelFromDB implements DataModelProvider {

    @Override
    public DataModel getModel() {
        PGSimpleDataSource dbsource = new PGSimpleDataSource();
        dbsource.setUser("postgres");
        dbsource.setPassword("postgres");
        dbsource.setServerName("localhost");
        dbsource.setDatabaseName("postgres");

        DataModel dataModelDB = new PostgreSQLJDBCDataModel(dbsource, "preferences", "user_id", "item_id", "preference", "timestamp");

        return dataModelDB;
    }

}
