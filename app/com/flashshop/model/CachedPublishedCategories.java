package com.flashshop.model;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 24.01.13
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class CachedPublishedCategories implements PublishedCategoriesService {
    private PublishedCategoriesService service;

    volatile private List<Category> categories;

    public CachedPublishedCategories(PublishedCategoriesService publishedCategoriesService) {
        service = publishedCategoriesService;
    }

    @Override
    public List<Category> getPublishedCategories() throws SQLException {
        if (categories == null){
            categories = service.getPublishedCategories();
        }

        return categories;
    }
}
