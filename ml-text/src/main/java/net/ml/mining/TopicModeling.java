package net.ml.mining;

import cc.mallet.types.*;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;
import cc.mallet.util.Randoms;
import lombok.extern.slf4j.Slf4j;
import net.ml.mining.filters.TextFilter;
import net.ml.mining.models.ModelGenerator;

import java.util.*;
import java.io.*;

@Slf4j
public class TopicModeling {

    static String dataFolderPath = "./../data/bbc";

    public static void main(String[] args) throws Exception {

        FileIterator folderIterator = new FileIterator(new File[] { new File(dataFolderPath) }, new TextFilter(), FileIterator.LAST_DIRECTORY);

        Pipe pipeline = ModelGenerator.getPipeline();
        // Construct a new instance list, passing it the pipe
        // we want to use to process instances.
        InstanceList instances = new InstanceList(pipeline);

        // Now process each instance provided by the iterator.
        instances.addThruPipe(folderIterator);

        // Create a model with 100 topics, alpha_t = 0.01, beta_w = 0.01
        // Note that the first parameter is passed as the sum over topics, while
        // the second is the parameter for a single dimension of the Dirichlet prior.
        int numTopics = 5;
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 0.01, 0.01);
        model.addInstances(instances);

        // Use two parallel samplers, which each look at one half the corpus and combine
        // statistics after every iteration.
        model.setNumThreads(4);

        // Run the model for 50 iterations and stop (this is for testing only,
        // for real applications, use 1000 to 2000 iterations)
        model.setNumIterations(50);
        model.estimate();

        // Show the words and topics in the first instance

        // The data alphabet maps word IDs to strings
        Alphabet dataAlphabet = instances.getDataAlphabet();

        // Estimate the topic distribution of the first instance,
        // given the current Gibbs state.
        double[] topicDistribution = model.getTopicProbabilities(0);

        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();

        // Show top 5 words in topics with proportions for the first document
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();

            log.info("{} ({}) ", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < 5) {
                IDSorter idCountPair = iterator.next();
                log.info("{} ({}) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                rank++;
            }
        }

        /*
         * Testing
         */

        log.info("Evaluation");

        // Split dataset
        InstanceList[] instanceSplit = instances.split(new Randoms(), new double[] { 0.9, 0.1, 0.0 });

        // Use the first 90% for training
        model.addInstances(instanceSplit[0]);
        model.setNumThreads(4);
        model.setNumIterations(50);
        model.estimate();

        // Get estimator
        MarginalProbEstimator estimator = model.getProbEstimator();
        double loglike = estimator.evaluateLeftToRight(instanceSplit[1], 10, false, null);
        log.info("Total log likelihood: {}", loglike);

    }

}
