package com.flashshop.mallet;

import cc.mallet.types.Instance;
import com.flashshop.managers.DataBaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 23.04.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class ProductIterator implements Iterator<Instance> {

    private Iterator<Instance> iterator;

    public ProductIterator() {
        DataBaseManager manager = DataBaseManager.getInstance();

        try {
            List<String> categories = manager.getCategories();
            List<Instance> samples = new ArrayList<Instance>();

            for (String category : categories) {
                List<String> productNames = manager.getProductNameForCategory(category);

                for (String productName : productNames) {
                    samples.add(new Instance(new StringBuffer(productName), category, "database", productName));
                }
            }

            iterator = samples.iterator();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Instance next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new IllegalStateException ("This Iterator<Instance> does not support remove().");
    }
}
