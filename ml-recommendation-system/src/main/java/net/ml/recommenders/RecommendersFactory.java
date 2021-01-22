package net.ml.recommenders;

import java.util.HashMap;
import java.util.Map;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;

import net.ml.recommenders.impl.ItemBased;
import net.ml.recommenders.impl.UserBased;

public class RecommendersFactory {
    
    private static final Map<RecommenderType, RecommenderBuilder> recommenderMap = new HashMap<>(); 
    
    static {
        recommenderMap.put(RecommenderType.ITEM_BASED, new ItemBased());
        recommenderMap.put(RecommenderType.USER_BASED, new UserBased());
    }
    
    public static RecommenderBuilder getRecommender(RecommenderType rType) {
        if (!recommenderMap.containsKey(rType)) {
            throw new IllegalArgumentException("Cannot figure out the type " + rType);
        }
        return recommenderMap.get(rType);
    }
}
