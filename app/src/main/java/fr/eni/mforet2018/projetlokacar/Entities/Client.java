package fr.eni.mforet2018.projetlokacar.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Client implements Parcelable {

    @PrimaryKey
    private int id;
    private String lastName;
    private String firtName;
    private String phoneNumber;
    private String email;
    private String mailAdresse;

    public Client(int id, String lastName, String firtName, String phoneNumber, String email, String mailAdresse) {
        this.id = id;
        this.lastName = lastName;
        this.firtName = firtName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.mailAdresse = mailAdresse;
    }

    public Client() {
    }

    protected Client(Parcel in) {
        id = in.readInt();
        lastName = in.readString();
        firtName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        mailAdresse = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailAdresse() {
        return mailAdresse;
    }

    public void setMailAdresse(String mailAdresse) {
        this.mailAdresse = mailAdresse;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firtName='" + firtName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", mailAdresse='" + mailAdresse + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(lastName);
        parcel.writeString(firtName);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(mailAdresse);
    }
}
