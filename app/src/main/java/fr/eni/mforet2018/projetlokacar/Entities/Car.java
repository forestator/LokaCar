package fr.eni.mforet2018.projetlokacar.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.File;
import java.util.Arrays;

@Entity
public class Car {

    @PrimaryKey
    private String numberPlate;
    private float dailyPrice;
    private boolean isRented;
    private String[] type;
    private String brand;
    private String model;
    @Ignore
    private File picture;

    public Car(String numberPlate, float dailyPrice, boolean isRented, String brand, String model) {
        this.numberPlate = numberPlate;
        this.dailyPrice = dailyPrice;
        this.isRented = isRented;
        this.type = new String[]{"Berline", "Coupé","Familiale","Cabriolet","Roadster","Pickup","Crossover","Sport"};
        this.brand = brand;
        this.model = model;
    }

    public Car() {
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public float getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(float dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        this.isRented = rented;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Car{" +
                "numberPlate='" + numberPlate + '\'' +
                ", dailyPrice=" + dailyPrice +
                ", isRented=" + isRented +
                ", type=" + Arrays.toString(type) +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
