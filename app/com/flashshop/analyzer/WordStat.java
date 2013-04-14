package com.flashshop.analyzer;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 20.12.12
 * Time: 22:50
 * To change this template use File | Settings | File Templates.
 */
public class WordStat {
    private HashMap<String,Integer> wordStat = new HashMap<String, Integer>();

    public void handle(String text){
        String[] words = text.split(" ");
        for (String word: words) {
            if (wordStat.containsKey(word)){
                int count = wordStat.get(word)+1;
                wordStat.put(word,count);
            }
            else {
                wordStat.put(word,1);
            }
        }
    }

    public HashMap<String, Integer> getWordStat() {
        return wordStat;
    }
}
