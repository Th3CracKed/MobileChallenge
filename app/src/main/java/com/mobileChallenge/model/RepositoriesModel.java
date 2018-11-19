package com.mobileChallenge.model;

import java.util.List;

/**
 * A POJO class used by the converter of Retrofit
 * will create list of items to be used by viewModel
 */
public class RepositoriesModel {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
}
