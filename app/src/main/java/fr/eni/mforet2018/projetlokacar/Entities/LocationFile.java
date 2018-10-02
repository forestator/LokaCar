package fr.eni.mforet2018.projetlokacar.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.time.LocalDate;

import fr.eni.mforet2018.projetlokacar.Entities.Converters.Converters;

@Entity(indices = {@Index(value = {"carPlateNumber", "clientId", "id"}, unique = true)}/*,
        foreignKeys = {
                @ForeignKey(entity = Client.class,
                        parentColumns = "clientId",
                        childColumns = "clientId"),
                @ForeignKey(entity = Car.class,
                        parentColumns = "plateNumber",
                        childColumns = "carPlateNumber")
        }*/)
public class LocationFile implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private float totalCost;
    @Ignore
    private File picturesBeforeRent;
    @Ignore
    private File picturesAfterRent;

    // TODO: penser à rajouter cet attribut dans les indexes afin d'avoir des fichiers de loc uniques
    @TypeConverters({Converters.class})
    @Ignore
    private LocalDate startOfRentDate;
    @TypeConverters({Converters.class})
    @Ignore
    private LocalDate endOfRentDate;
    private int clientId;
    private String carPlateNumber;

    public LocationFile() {
    }

    public LocationFile(int id, float totalCost, File picturesBeforeRent, File picturesAfterRent, LocalDate startOfRentDate, LocalDate endOfRentDate) {
        this.id = id;
        this.totalCost = totalCost;
        this.picturesBeforeRent = picturesBeforeRent;
        this.picturesAfterRent = picturesAfterRent;
        this.startOfRentDate = startOfRentDate;
        this.endOfRentDate = endOfRentDate;
    }

    protected LocationFile(Parcel in) {
        id = in.readInt();
        totalCost = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(totalCost);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LocationFile> CREATOR = new Creator<LocationFile>() {
        @Override
        public LocationFile createFromParcel(Parcel in) {
            return new LocationFile(in);
        }

        @Override
        public LocationFile[] newArray(int size) {
            return new LocationFile[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public File getPicturesBeforeRent() {
        return picturesBeforeRent;
    }

    public void setPicturesBeforeRent(File picturesBeforeRent) {
        this.picturesBeforeRent = picturesBeforeRent;
    }

    public File getPicturesAfterRent() {
        return picturesAfterRent;
    }

    public void setPicturesAfterRent(File picturesAfterRent) {
        this.picturesAfterRent = picturesAfterRent;
    }

    public LocalDate getStartOfRentDate() {
        return startOfRentDate;
    }

    public void setStartOfRentDate(LocalDate startOfRentDate) {
        this.startOfRentDate = startOfRentDate;
    }

    public LocalDate getEndOfRentDate() {
        return endOfRentDate;
    }

    public void setEndOfRentDate(LocalDate endOfRentDate) {
        this.endOfRentDate = endOfRentDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    @Override
    public String toString() {
        return "LocationFile{" +
                "id=" + id +
                ", totalCost=" + totalCost +
                ", picturesBeforeRent=" + picturesBeforeRent +
                ", picturesAfterRent=" + picturesAfterRent +
                ", startOfRentDate=" + startOfRentDate +
                ", endOfRentDate=" + endOfRentDate +
                '}';
    }
}
