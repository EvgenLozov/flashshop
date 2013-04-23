package com.flashshop.bayes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 21.04.13
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public class Cause {
    private String name;
    private Map<String,Integer> investigations = new HashMap<String,Integer>(); // P(investigation|cause)
    private int totalTexts;

    public Cause(String name) {
        this.name = name;
    }

    public double likelihood(Set<String> evidences){
        double likelihood = 1;
        for(String evidence: investigations.keySet()){
            double probability = 0;
            if(investigations.containsKey(evidence))
                probability = evidences.contains(evidence) ? investigations.get(evidence)/(double)totalTexts : (totalTexts - investigations.get(evidence))/(double)totalTexts;

            likelihood *= probability;
        }

        return likelihood;
    }

    public void showWords(Set<String> words)
    {
        totalTexts++;
        for(String word: words){
            if(!investigations.containsKey(word))
                investigations.put(word, 0);

            investigations.put(word, investigations.get(word) + 1);
        }
    }

    public String getName() {
        return name;
    }

}
