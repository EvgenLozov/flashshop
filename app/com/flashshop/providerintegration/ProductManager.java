package com.flashshop.providerintegration;

import com.flashshop.Product;
import com.flashshop.managers.DataBaseManager;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 14.04.13
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class ProductManager {
    private static ProductManager instance;

    public static ProductManager getInstance() {
        if(instance == null)
            instance = new ProductManager();

        return instance;
    }

    private ProductManager() {
    }

    public void actualize() throws SQLException {
        CheapestProductsSet set = getCheapestProductsSet();
        updateProducts(set);

    }

    public CheapestProductsSet getCheapestProductsSet() {
        CheapestProductsSet set = new CheapestProductsSet();

        for(ProductProviderEnum providerEnum: ProductProviderEnum.values()){
            ProductProvider provider = providerEnum.getProvider();

            while (provider.hasNextProduct()){
                Product product = provider.nextProduct();
                product.providerId = providerEnum.getId();
                set.add(provider.nextProduct());
            }
        }

        return set;
    }

    public void updateProducts(CheapestProductsSet set) throws SQLException {
        DataBaseManager dataBaseManager = DataBaseManager.getInstance();

        for (Product product : set.getProducts()) {
            if (dataBaseManager.isProductExist(product)){
                dataBaseManager.updateProduct(product.productModel,
                                              product.providerId,
                                              product.productPrice,
                                              product.productAvailability ? "5" : "0");
            }
            else {
                dataBaseManager.addTradePosition(product);
            }
        }
    }
}
