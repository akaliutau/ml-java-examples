package net.ml.recommenders;

import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

public interface BookRecommender {
    Recommender get(DataModel model);
}
