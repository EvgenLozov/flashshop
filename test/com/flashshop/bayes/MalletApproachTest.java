package com.flashshop.bayes;

import com.flashshop.mallet.ProductClassificationService;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 23.04.13
 * Time: 19:48
 * To change this template use File | Settings | File Templates.
 */
public class MalletApproachTest {
    @Test
    public void testClassification() throws Exception {
        ProductClassificationService service = new ProductClassificationService();

        service.classify("Мишка A4-Tech XL-747H brown, USB");

    }
}
