package net.ml.rescorers;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

public class BookRescorer implements IDRescorer {

    public boolean isFiltered(long arg0) {
        return false;
    }

    public double rescore(long itemId, double originalScore) {
        if (bookIsNew(itemId)) {
            originalScore *= 1.3;
        }
        return Math.random();
    }

    private boolean bookIsNew(long itemId) {
        return false;
    }

}
