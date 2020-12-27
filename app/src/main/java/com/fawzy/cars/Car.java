package com.fawzy.cars;

public class Car {

    private String color;
    private double dpl;
    private String model , description ;
    private String image ;
    private int id;

    public Car() {
    }

    public Car(String color, double dpl, String model, String description, String image, int id) {
        this.color = color;
        this.dpl = dpl;
        this.model = model;
        this.description = description;
        this.image = image;
        this.id = id;
    }

    public Car(String color, double dpl, String model, String description, String image) {
        this.color = color;
        this.dpl = dpl;
        this.model = model;
        this.description = description;
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getDpl() {
        return dpl;
    }

    public void setDpl(double dpl) {
        this.dpl = dpl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }





}
