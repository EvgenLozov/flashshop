package com.flashshop.bayes;

import com.flashshop.managers.DataBaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DatabaseSampleProvider implements SamplesProvider {

    private DataBaseManager dataBaseManager = DataBaseManager.getInstance();

    @Override
    public List<Sample> getSamples() {
        try {
            List<String> categories = dataBaseManager.getCategories();
            List<Sample> samples = new ArrayList<Sample>();

            for (String category : categories) {
                List<String> productNames = dataBaseManager.getProductNameForCategory(category);
                samples.addAll(createSamples(category, productNames));
            }

            return samples;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Sample> createSamples(String category, List<String> names){
        List<Sample> samples = new ArrayList<Sample>();
        for (String name : names) {
            samples.add(new Sample(category, new HashSet<String>(Arrays.asList(name.split(" "))) ));
        }

        return samples;
    }
}
