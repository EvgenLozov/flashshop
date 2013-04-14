package com.flashshop.providerintegration;

import excel.KpiServiceProductProvider;
import excel.TDBProductProvider;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 14.04.13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public enum ProductProviderEnum {
    KPI_SERVICE(KpiServiceProductProvider.getInstance(), 1), TDB(TDBProductProvider.getInstance(), 1);

    private ProductProvider provider;
    private int id;

    private ProductProviderEnum(ProductProvider provider, int id) {
        this.provider = provider;
        this.id = id;
    }

    public ProductProvider getProvider() {
        return provider;
    }

    public int getId() {
        return id;
    }
}
