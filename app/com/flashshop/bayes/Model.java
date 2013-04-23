package com.flashshop.bayes;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 21.04.13
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class Model {
    private Map<String,Cause> causes = new HashMap<String,Cause>();

    public String getCause(String text){
        HashSet<String> words = new HashSet<String>(Arrays.asList(text.split(" ")));

        TreeMap<Double,Cause> sortedCauses = new TreeMap<>();
        for(Cause cause: causes.values())
        {
            double likelihood = cause.likelihood(words);
            sortedCauses.put(likelihood, cause);
        }

        return sortedCauses.lastEntry().getValue().getName();
    }

    private void showSample(String causeName, Set<String> words)
    {
        if(!causes.containsKey(causeName))
            causes.put(causeName, new Cause(causeName));

        Cause cause = causes.get(causeName);
        cause.showWords(words);
    }

    public void train(SamplesProvider provider)
    {
        train(provider.getSamples());
    }

    public void train(List<Sample> samples){
        for (Sample sample : samples) {
            showSample(sample.cause, sample.words);
        }
    }
}
