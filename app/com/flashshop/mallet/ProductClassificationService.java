package com.flashshop.mallet;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.pipe.*;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Labeling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 23.04.13
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
public class ProductClassificationService {

    private Pipe pipe;
    private Classifier classifier;

    public ProductClassificationService() {
        pipe = buildPipe();
        trainClassifier(getInstanceList());
    }

    public void classify(String text)
    {
        ArrayList<Instance> instances = new ArrayList<>();
        instances.add(new Instance(new StringBuffer(text), null, "unknown", text));

        Iterator iterator = classifier.getInstancePipe().newIteratorFrom(instances.iterator());

        Labeling labeling = classifier.classify(iterator.next()).getLabeling();

        // print the labels with their weights in descending order (ie best first)

        for (int rank = 0; rank < labeling.numLocations(); rank++){
            System.out.print(labeling.getLabelAtRank(rank) + ":" +
                    labeling.getValueAtRank(rank) + " ");
        }
        System.out.println();

    }

    private Pipe buildPipe() {
        ArrayList pipeList = new ArrayList();

        // Read data from File objects
        pipeList.add(new Input2CharSequence("UTF-8"));

        // Regular expression for what constitutes a token.
        //  This pattern includes Unicode letters, Unicode numbers,
        //   and the underscore character. Alternatives:
        //    "\\S+"   (anything not whitespace)
        //    "\\w+"    ( A-Z, a-z, 0-9, _ )
        //    "[\\p{L}\\p{N}_]+|[\\p{P}]+"   (a group of only letters and numbers OR
        //                                    a group of only punctuation marks)
        Pattern tokenPattern =
                Pattern.compile("[\\p{L}\\p{N}_]+");

        // Tokenize raw strings
        pipeList.add(new CharSequence2TokenSequence(tokenPattern));

        // Normalize all tokens to all lowercase
        pipeList.add(new TokenSequenceLowercase());

        // Remove stopwords from a standard English stoplist.
        //  options: [case sensitive] [mark deletions]
        pipeList.add(new TokenSequenceRemoveStopwords(false, false));

        // Rather than storing tokens as strings, convert
        //  them to integers by looking them up in an alphabet.
        pipeList.add(new TokenSequence2FeatureSequence());

        // Do the same thing for the "target" field:
        //  convert a class label string to a Label object,
        //  which has an index in a Label alphabet.
        pipeList.add(new Target2Label());

        // Now convert the sequence of features to a sparse vector,
        //  mapping feature IDs to counts.
        pipeList.add(new FeatureSequence2FeatureVector());

        // Print out the features and the label
        pipeList.add(new PrintInputAndTarget());

        return new SerialPipes(pipeList);
    }

    private InstanceList getInstanceList(){
        ProductIterator iterator = new ProductIterator();

        // Construct a new instance list, passing it the pipe
        //  we want to use to process instances.
        InstanceList instances = new InstanceList(pipe);

        // Now process each instance provided by the iterator.
        instances.addThruPipe(iterator);

        return instances;
    }

    private void trainClassifier(InstanceList trainingInstances) {

        // Here we use a maximum entropy (ie polytomous logistic regression)
        //  classifier. Mallet includes a wide variety of classification
        //  algorithms, see the JavaDoc API for details.

        ClassifierTrainer trainer = new MaxEntTrainer();
        classifier = trainer.train(trainingInstances);
    }

}
