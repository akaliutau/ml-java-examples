package net.ml.models;

import java.util.HashMap;
import java.util.Map;

import net.ml.models.impl.DataModelFromDB;
import net.ml.models.impl.DataModelFromFile;
import net.ml.models.impl.DataModelInMem;
import net.ml.models.impl.EmptyProvider;

public class DataModelFactory {
    
    private static final Map<ProviderType, DataModelProvider> providers = new HashMap<>();
    
    static {
        providers.put(ProviderType.DBBASED, new DataModelFromDB());
        providers.put(ProviderType.FILEBASED, new DataModelFromFile());
        providers.put(ProviderType.INMEMORY, new DataModelInMem());
    }


    public static DataModelProvider get(ProviderType providerType) {
        return providers.getOrDefault(providerType, new EmptyProvider());
    }
    


    
}
