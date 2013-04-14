package com.flashshop.actualizator.model;

import com.flashshop.hotprice.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 22.01.13
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */

public class ActualizatorSettings {
    private Integer settingId;
    private Integer period;
    private ArrayList<CategoryWithMargin> categories = new ArrayList<CategoryWithMargin>();
    private ArrayList<Category> selectedCategory = new ArrayList<Category>();

    public ArrayList<Category> getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ArrayList<Category> selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void add(CategoryWithMargin category){
        categories.add(category);
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public ArrayList<CategoryWithMargin> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryWithMargin> categories) {
        this.categories = categories;
    }

    public Integer getMargin(String categoryName)
    {
        for(CategoryWithMargin category: categories)
        {
            if(category.getCategoryName().equals(categoryName))
                return category.getMargin();
        }

        return null;
    }
}
