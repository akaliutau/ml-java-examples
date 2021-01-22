package net.ml.data.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLMapper {
    void map(PreparedStatement ps, String value, int index) throws SQLException;
}
