package net.ml.recommenders.impl;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import net.ml.rescorers.BookRescorer;

public class ItemBased implements RecommenderBuilder {

    @Override
    public Recommender buildRecommender(DataModel dataModel) throws TasteException {
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        IDRescorer rescorer = new BookRescorer();
        return recommender;
    }

}
