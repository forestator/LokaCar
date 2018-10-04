package fr.eni.mforet2018.projetlokacar.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.File;

@Entity(indices = {@Index(value = {"plateNumber"}, unique = true)})
public class Car implements Parcelable {

    @PrimaryKey
    @NonNull
    private String plateNumber;
    private float dailyPrice;
    private boolean isRented;
    private String type;
    private String brand;
    private String model;
    private String fuel;
    private int seatsNumber;
    private String picture;

    public Car() {
    }

    public Car(@NonNull String plateNumber, float dailyPrice, boolean isRented, String type, String brand, String model, String fuel, int seatsNumber, String picture) {
        this.plateNumber = plateNumber;
        this.dailyPrice = dailyPrice;
        this.isRented = isRented;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.seatsNumber = seatsNumber;
        this.picture = picture;
    }

    protected Car(Parcel in) {
        plateNumber = in.readString();
        dailyPrice = in.readFloat();
        isRented = in.readByte() != 0;
        type = in.readString();
        brand = in.readString();
        model = in.readString();
        fuel = in.readString();
        seatsNumber = in.readInt();
        picture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plateNumber);
        dest.writeFloat(dailyPrice);
        dest.writeByte((byte) (isRented ? 1 : 0));
        dest.writeString(type);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(fuel);
        dest.writeInt(seatsNumber);
        dest.writeString(picture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @NonNull
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(@NonNull String plateNumber) {
        this.plateNumber = plateNumber;
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
        isRented = rented;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Car{" +
                "plateNumber='" + plateNumber + '\'' +
                ", dailyPrice=" + dailyPrice +
                ", isRented=" + isRented +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", fuel='" + fuel + '\'' +
                ", seatsNumber=" + seatsNumber +
                ", picture='" + picture + '\'' +
                '}';
    }
}
