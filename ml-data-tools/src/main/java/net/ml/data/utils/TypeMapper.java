package net.ml.data.utils;

import static net.ml.data.utils.SQLTypes.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TypeMapper {

    static Map<SQLTypes, SQLMapper> mapper = new HashMap<>();
    static final String NULLSTR = "NULL";

    static {
        mapper.put(BIGINT, (s, v, i) -> s.setLong(i, Long.valueOf(v)));
        mapper.put(INT, (s, v, i) -> s.setInt(i, Integer.valueOf(v)));
        mapper.put(DATE, (s, v, i) -> s.setDate(i, DateUtil.convertToDate(v)));
        mapper.put(VARCHAR, (s, v, i) -> s.setString(i, v));
        mapper.put(REAL, (s, v, i) -> s.setFloat(i, Float.valueOf(v)));
        mapper.put(ISBN, (s, v, i) -> s.setString(i, v));
        mapper.put(ISBN_INT, (s, v, i) -> s.setLong(i, ISBNUtil.getISBNAsLong(v)));
    }

    public static void prepare(PreparedStatement ps, Schema schema, String[] data) throws SQLException {
        int n = schema.size();
        for (int i = 1; i <= n; i++) {
            SQLTypes type = schema.getTypes()[i - 1];
            if (!mapper.containsKey(type)) {
                throw new IllegalArgumentException("Cannot figure out mapping for type " + type);
            }
            if (data[i - 1] == null || data[i - 1].equals(NULLSTR)) {
                ps.setNull(i, type.getType());
            }else {
                mapper.get(type).map(ps, data[i - 1], i);
            }
        }
    }
}
