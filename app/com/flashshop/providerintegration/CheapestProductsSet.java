package com.flashshop.providerintegration;

import com.flashshop.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 14.04.13
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class CheapestProductsSet {
    private Map<String,Product> products = new HashMap<String,Product>();

    public void add(Product product){
        Product existProduct = products.get(product.getModel());

        if(existProduct == null || better(existProduct, product) ) {
            products.put(product.getModel(), product);
        }
    }

    public Collection<Product> getProducts(){
        return products.values();
    }

    private boolean better(Product p1, Product p2){
        return p1.getPrice() < p2.getPrice();
    }

}
