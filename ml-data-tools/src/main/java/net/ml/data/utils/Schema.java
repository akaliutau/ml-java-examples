package net.ml.data.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Schema {
    private final String[] columns;
    private final SQLTypes[] types;

    public int size() {
        return columns.length;
    }
}
