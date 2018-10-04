package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.LocFilCarClient;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;

@Dao
public interface LocationFileDAO extends GenericDAO<LocationFile> {

    String SELECT_ALL = "SELECT * FROM LocationFile";

    @Query("DELETE FROM LocationFile")

    void deleteAll();

    @Query(SELECT_ALL)
    List<LocationFile> getAll();

    @Query("SELECT * FROM locationFile WHERE carPlateNumber LIKE :plateNumber")
    LocationFile getLocationFileByCarPlateNumber(String plateNumber);

    @Query("SELECT LocationFile.startOfRentDate AS dateStart, " +
            "LocationFile.endOfRentDate AS dateEnd, " +
            "LocationFile.id, " +
            "Client.lastName AS clientLastName, " +
            "Client.firstName AS clientFirstName, " +
            "Car.brand AS carLocBrand, " +
            "Car.model AS carLocModel, " +
            "Car.plateNumber AS plateNb " +
            "FROM LocationFile, Client, Car " +
            "WHERE LocationFile.clientId = Client.clientId " +
            "AND LocationFile.carPlateNumber = Car.plateNumber")
    LiveData<List<LocFilCarClient>> getLocFilesWithCliNCar();

}
