package fr.eni.mforet2018.projetlokacar.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.Date;

import fr.eni.mforet2018.projetlokacar.Entities.Converters.Converters;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = {@Index(value = {"carPlateNumber", "clientId", "id"}, unique = true)})
public class LocationFile implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private float totalCost;
    private String picturesBeforeRent;
    private String picturesAfterRent;
    private Date startOfRentDate;
    private Date endOfRentDate;
    private int clientId;
    private String carPlateNumber;

    public LocationFile() {
    }

    public LocationFile(int id, float totalCost, String picturesBeforeRent, String picturesAfterRent, Date startOfRentDate, Date endOfRentDate, int clientId, String carPlateNumber) {
        this.id = id;
        this.totalCost = totalCost;
        this.picturesBeforeRent = picturesBeforeRent;
        this.picturesAfterRent = picturesAfterRent;
        this.startOfRentDate = startOfRentDate;
        this.endOfRentDate = endOfRentDate;
        this.clientId = clientId;
        this.carPlateNumber = carPlateNumber;
    }

    protected LocationFile(Parcel in) {
        id = in.readInt();
        totalCost = in.readFloat();
        picturesBeforeRent = in.readString();
        picturesAfterRent = in.readString();
        clientId = in.readInt();
        carPlateNumber = in.readString();
        startOfRentDate = new Date(in.readLong());
        endOfRentDate = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(totalCost);
        dest.writeString(picturesBeforeRent);
        dest.writeString(picturesAfterRent);
        dest.writeInt(clientId);
        dest.writeString(carPlateNumber);
        dest.writeLong(startOfRentDate.getTime());
        dest.writeLong(endOfRentDate.getTime());
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

    @Override
    public String toString() {
        return "LocationFile{" +
                "id=" + id +
                ", totalCost=" + totalCost +
                ", picturesBeforeRent='" + picturesBeforeRent + '\'' +
                ", picturesAfterRent='" + picturesAfterRent + '\'' +
                ", startOfRentDate=" + startOfRentDate +
                ", endOfRentDate=" + endOfRentDate +
                ", clientId=" + clientId +
                ", carPlateNumber='" + carPlateNumber + '\'' +
                '}';
    }

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

    public String getPicturesBeforeRent() {
        return picturesBeforeRent;
    }

    public void setPicturesBeforeRent(String picturesBeforeRent) {
        this.picturesBeforeRent = picturesBeforeRent;
    }

    public String getPicturesAfterRent() {
        return picturesAfterRent;
    }

    public void setPicturesAfterRent(String picturesAfterRent) {
        this.picturesAfterRent = picturesAfterRent;
    }

    public Date getStartOfRentDate() {
        return startOfRentDate;
    }

    public void setStartOfRentDate(Date startOfRentDate) {
        this.startOfRentDate = startOfRentDate;
    }

    public Date getEndOfRentDate() {
        return endOfRentDate;
    }

    public void setEndOfRentDate(Date endOfRentDate) {
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
}
