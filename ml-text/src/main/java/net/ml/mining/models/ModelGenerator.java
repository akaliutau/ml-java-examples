package net.ml.mining.models;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;

public class ModelGenerator {

    static String stopListFilePath = "./../data/stoplists/en.txt";

    public static SerialPipes getPipeline() {
        ArrayList<Pipe> pipeList = new ArrayList<>();
        pipeList.add(new Input2CharSequence("UTF-8"));
        Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
        pipeList.add(new CharSequence2TokenSequence(tokenPattern));
        pipeList.add(new TokenSequenceLowercase());
        pipeList.add(new TokenSequenceRemoveStopwords(new File(stopListFilePath), "utf-8", false, false, false));
        pipeList.add(new TokenSequence2FeatureSequence());
        pipeList.add(new Target2Label());
        SerialPipes pipeline = new SerialPipes(pipeList);
        return pipeline;
    }

}
