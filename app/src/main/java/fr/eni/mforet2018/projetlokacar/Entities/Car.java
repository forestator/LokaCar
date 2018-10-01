package fr.eni.mforet2018.projetlokacar.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
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
    @Ignore
    private File picture;

    public Car() {
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

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

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

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
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
                ", picture=" + picture +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(plateNumber);
        parcel.writeFloat(dailyPrice);
        parcel.writeByte((byte) (isRented ? 1 : 0));
        parcel.writeString(type);
        parcel.writeString(brand);
        parcel.writeString(model);
        parcel.writeString(fuel);
        parcel.writeInt(seatsNumber);
    }
}
