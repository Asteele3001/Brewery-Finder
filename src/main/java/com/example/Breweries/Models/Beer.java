package com.example.Breweries.Models;

public class Beer {
    private int id;
    private String name;
    private String style;
    private String description;
    private int brewery_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBrewery_id() {
        return brewery_id;
    }

    public void setBrewery_id(int brewery_id) {
        this.brewery_id = brewery_id;
    }

    public Beer(int id, String name, String style, String description, int brewery_id) {
        this.id = id;
        this.name = name;
        this.style = style;
        this.description = description;
        this.brewery_id = brewery_id;
    }

    public Beer() {

    }
}