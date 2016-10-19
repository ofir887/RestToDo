package com.example.ofirmonis.resttodo.objects;

/**
 * Created by OfirMonis on 20/02/2016.
 */
public class Restaurant {

    private String name;
    private String url;

    public Restaurant() {
    }

    public Restaurant(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


}
