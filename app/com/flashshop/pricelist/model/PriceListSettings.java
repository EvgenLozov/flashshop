package com.flashshop.pricelist.model;


import com.flashshop.model.Category;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 27.01.13
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
public class PriceListSettings {
    private Integer id;
    private String name;
    private Integer interval;
    private ArrayList<Category> selectedCategory = new ArrayList<Category>();

    public void add(Category category){
        selectedCategory.add(category);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public ArrayList<Category> getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ArrayList<Category> selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
