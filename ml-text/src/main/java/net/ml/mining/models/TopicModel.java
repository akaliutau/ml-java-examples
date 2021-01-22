package net.ml.mining.models;

import java.io.IOException;

import cc.mallet.pipe.SerialPipes;
import cc.mallet.topics.ParallelTopicModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.ml.mining.utils.ModelIOUtils;


@Slf4j
@Data
public class TopicModel {

    private SerialPipes pipelines;
    private ParallelTopicModel model;
    private final String name;

    public TopicModel(String name) {
        this.name = name;
    }
    
    public void save() throws IOException {
        ModelIOUtils.save(model, name + ".model");
        ModelIOUtils.save(model, pipelines + ".pipeline");
        log.info("Model saved.");
    }
     
    public void load() throws ClassNotFoundException, IOException {
        model = ModelIOUtils.load(name + ".model");
        pipelines = ModelIOUtils.load(pipelines + ".pipeline");
    }
}
