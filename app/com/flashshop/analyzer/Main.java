package com.flashshop.analyzer;

import com.flashshop.managers.DataBaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 20.12.12
 * Time: 22:48
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        WordStat wordStat = new WordStat();
        DataBaseManager dataBaseManager =  DataBaseManager.getInstance();
        List<String> words = dataBaseManager.getProductNameForCategory("Мышки");
        for (String word: words){
            wordStat.handle(word);
        }

        for(String word: wordStat.getWordStat().keySet())
            System.out.println(word+": "+wordStat.getWordStat().get(word));


    }


}
