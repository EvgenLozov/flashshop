package com.flashshop.bayes;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 21.04.13
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */
public class ModelTest {

    private List<Sample> samples = new ArrayList<Sample>();

    @Before
    public void setUp() throws Exception {
        samples.add(new Sample( "category1", new HashSet<String>(Arrays.asList("A B C".split(" ")) )  ));
        samples.add(new Sample( "category1", new HashSet<String>(Arrays.asList("A B".split(" ")) )  ));
        samples.add(new Sample( "category1", new HashSet<String>(Arrays.asList("A C D".split(" ")) )  ));
        samples.add(new Sample( "category2", new HashSet<String>(Arrays.asList("D K M".split(" ")) )  ));
        samples.add(new Sample( "category2", new HashSet<String>(Arrays.asList("Q K M".split(" ")) )  ));
        samples.add(new Sample( "category2", new HashSet<String>(Arrays.asList("M Q T".split(" ")) )  ));
    }

    @Test
    public void testTrain() throws Exception {
        Model model = new Model();

        model.train(samples);
        String foundedCause = model.getCause("A");
    }

    @Test
    public void testTrainWithDatabaseProvider() throws Exception {
        Model model = new Model();
        model.train(new DatabaseSampleProvider());

        model.getCause("A4Tech G7-300 Wireless Yellow");
    }
}
