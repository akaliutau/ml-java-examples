package net.ml.rs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousConcurrentUserDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.mortbay.log.Log;

import lombok.extern.slf4j.Slf4j;
import net.ml.models.DataModelFactory;
import net.ml.models.ProviderType;
import net.ml.models.StringItemIdFileDataModel;
import net.ml.recommenders.RecommenderType;
import net.ml.recommenders.RecommendersFactory;
import net.ml.utils.DataUtils;

@Slf4j
public class OnlineRecommendationService {
    private static String bxbooks = "./../data/books.zip";

    private static HashMap<String, String> books;

    private Recommender recommender;
    private int concurrentUsers = 100;
    private int noItems = 10;
    private int totalUsers; 
    private Set<Long> users = new HashSet<>();
    

    public OnlineRecommendationService() throws Exception {

        books = DataUtils.loadBooksFromZip(bxbooks);
        
        StringItemIdFileDataModel dataModel = (StringItemIdFileDataModel) DataModelFactory.get(ProviderType.FILEBASED).getModel();
        recommender = (UserBasedRecommender) RecommendersFactory.getRecommender(RecommenderType.USER_BASED).buildRecommender(dataModel);
        PlusAnonymousConcurrentUserDataModel plusModel = new PlusAnonymousConcurrentUserDataModel(dataModel, concurrentUsers);
        totalUsers = plusModel.getNumUsers();
        LongPrimitiveIterator iter = plusModel.getUserIDs(); 
        while (iter.hasNext()) {
            users.add(iter.next());
        }
        Log.info("totalUsers: {}",totalUsers);
    }

    public List<RecommendedItem> recommend(long userId, PreferenceArray preferences) throws TasteException {

        if (userExistsInDataModel(userId)) {
            return recommender.recommend(userId, noItems);
        } else {
            PlusAnonymousConcurrentUserDataModel plusModel = (PlusAnonymousConcurrentUserDataModel) recommender.getDataModel();

            // Take an available anonymous user form the poll
            Long anonymousUserID = plusModel.takeAvailableUser();

            // Set temporary preferences
            PreferenceArray tempPrefs = preferences;
            tempPrefs.setUserID(0, anonymousUserID);
            // tempPrefs.setItemID(0, itemID);
            plusModel.setTempPrefs(tempPrefs, anonymousUserID);

            List<RecommendedItem> results = recommender.recommend(anonymousUserID, noItems);

            // Release the user back to the poll
            plusModel.releaseUser(anonymousUserID);

            return results;

        }
    }

    private boolean userExistsInDataModel(long userId) {
        return users.contains(userId);
    }
}
