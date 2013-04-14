package com.flashshop.actualizator.model;

import com.flashshop.model.Category;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 22.01.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class CategoryWithMargin extends Category {
    private Integer margin;

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    @Override
    public String toString() {
        return "CategoryWithMargin{" +
                "margin=" + margin +
                '}';
    }
}
