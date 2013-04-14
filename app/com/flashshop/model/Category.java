package com.flashshop.model;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 23.01.13
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    private String categoryName;
    private Integer categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
