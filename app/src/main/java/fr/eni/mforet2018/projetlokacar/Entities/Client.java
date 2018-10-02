package fr.eni.mforet2018.projetlokacar.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(indices = {@Index(value = {"clientId"}, unique = true)})
public class Client implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int clientId;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;
    private String mailAddress;

    public Client() {
    }

    protected Client(Parcel in) {
        clientId = in.readInt();
        lastName = in.readString();
        firstName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        mailAddress = in.readString();
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firtName) {
        this.firstName = firtName;
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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAdresse) {
        this.mailAddress = mailAdresse;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + clientId +
                ", lastName='" + lastName + '\'' +
                ", firtName='" + firstName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", mailAdresse='" + mailAddress + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(clientId);
        parcel.writeString(lastName);
        parcel.writeString(firstName);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(mailAddress);
    }
}
