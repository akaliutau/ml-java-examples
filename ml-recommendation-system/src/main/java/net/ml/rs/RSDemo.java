package net.ml.rs;

import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

import lombok.extern.slf4j.Slf4j;
import net.ml.models.DataModelFactory;
import net.ml.models.ProviderType;
import net.ml.models.StringItemIdFileDataModel;
import net.ml.recommenders.RecommenderType;
import net.ml.recommenders.RecommendersFactory;
import net.ml.utils.DataUtils;

@Slf4j
public class RSDemo {
    static String bxbooks = "./../data/books.zip";

    static HashMap<String, String> books;

    public static void main(String[] args) throws Exception {
        books = DataUtils.loadBooksFromZip(bxbooks);
        
        StringItemIdFileDataModel dataModel = (StringItemIdFileDataModel) DataModelFactory.get(ProviderType.FILEBASED).getModel();
        ItemBasedRecommender recommender1 = (ItemBasedRecommender) RecommendersFactory.getRecommender(RecommenderType.ITEM_BASED).buildRecommender(dataModel);
        String itemISBN = "042513976X";
        long itemID = dataModel.readItemIDFromString(itemISBN);
        int noItems = 10;
        
        log.info("====== ItemBased Recommender ======");
        log.info("Recommendations for item: {}", books.get(itemISBN));

        log.info("Most similar items:");
        List<RecommendedItem> recommendations = recommender1.mostSimilarItems(itemID, noItems);
        for (RecommendedItem item : recommendations) {
            itemISBN = dataModel.getItemIDAsString(item.getItemID());
            log.info("Item: {}  | Item id: {}  | Value: {}", books.get(itemISBN), itemISBN, item.getValue());
        }

        log.info("====== UserBased Recommender ======");
        UserBasedRecommender recommender2 = (UserBasedRecommender) RecommendersFactory.getRecommender(RecommenderType.USER_BASED).buildRecommender(dataModel);
        long userID = 276704;// 276704;//212124;//277157;

        log.info("Rated items:");
        for (Preference preference : dataModel.getPreferencesFromUser(userID)) {
            itemISBN = dataModel.getItemIDAsString(preference.getItemID());
            log.info("Item: {}  | Item id: {}  | Value: {}", books.get(itemISBN), itemISBN, preference.getValue());
        }

        log.info("Recommended items:");
        recommendations = recommender2.recommend(userID, noItems);
        for (RecommendedItem item : recommendations) {
            itemISBN = dataModel.getItemIDAsString(item.getItemID());
            log.info("Item: {}  | Item id: {}  | Value: {}", books.get(itemISBN), itemISBN, item.getValue());
        }
        
        log.info("====== Evaluation of UserBased Recommender ======");

        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = RecommendersFactory.getRecommender(RecommenderType.ITEM_BASED);
        double result = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
        log.info("evalutaion result: {}", result);
        
        
    }


}
