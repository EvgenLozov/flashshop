package com.flashshop.model;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 24.01.13
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
public interface PublishedCategoriesService {
    public List<Category> getPublishedCategories() throws SQLException;
}
