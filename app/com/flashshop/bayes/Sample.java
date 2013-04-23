package com.flashshop.bayes;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 21.04.13
 * Time: 11:24
 * To change this template use File | Settings | File Templates.
 */
public class Sample {
    public String cause;
    public Set<String> words;

    public Sample(String cause, Set<String> words) {
        this.cause = cause;
        this.words = words;
    }
}
