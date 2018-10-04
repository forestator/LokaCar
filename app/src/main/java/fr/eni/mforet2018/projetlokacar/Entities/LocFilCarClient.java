package fr.eni.mforet2018.projetlokacar.Entities;

import java.util.Date;

public class LocFilCarClient {
    private int id;
    private Date dateStart;
    private Date dateEnd;
    private String clientLastName;
    private String clientFirstName;
    private String carLocBrand;
    private String carLocModel;
    private String plateNb;

    public LocFilCarClient() {
    }

    public String getPlateNb() {
        return plateNb;
    }

    public void setPlateNb(String plateNb) {
        this.plateNb = plateNb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getCarLocBrand() {
        return carLocBrand;
    }

    public void setCarLocBrand(String carLocBrand) {
        this.carLocBrand = carLocBrand;
    }

    public String getCarLocModel() {
        return carLocModel;
    }

    public void setCarLocModel(String carLocModel) {
        this.carLocModel = carLocModel;
    }
}
