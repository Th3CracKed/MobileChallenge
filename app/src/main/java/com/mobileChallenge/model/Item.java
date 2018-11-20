package com.mobileChallenge.model;

/**
 * Main Model used for the view and for retrieving data from Github API
 * Contains the repo :  name - description - watchers (number of stars) - owner (sub Model) - html_url (repo url)
 */
public class Item {
    private String name;
    private String description;
    private String watchers;
    private Owner owner;
    private String html_url;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWatchers() {
        return watchers;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getHtml_url() {
        return html_url;
    }
}
