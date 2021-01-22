package net.ml.data.utils;

import java.sql.Types;

public enum SQLTypes {

    BIGINT(Types.BIGINT), 
    INT(Types.INTEGER), 
    VARCHAR(Types.VARCHAR), 
    REAL(Types.FLOAT), 
    DATE(Types.DATE), 
    ISBN_INT(Types.BIGINT), 
    ISBN(Types.VARCHAR), 
    DEFAULT(Types.VARCHAR);

    private int type;

    private SQLTypes(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
