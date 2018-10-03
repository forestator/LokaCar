package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.Agency;
import fr.eni.mforet2018.projetlokacar.Entities.Car;

@Dao
public interface CarDAO extends GenericDAO<Car> {

    String SELECT_ALL_NOT_RENTED = "SELECT * FROM car WHERE isRented = 0";
    String SELECT_ALL_RENTED = "SELECT * FROM car WHERE isRented = 1";
    String SELECT_ALL = "SELECT * FROM car";
    String SELECT_CAR_BY_PLATENUMBER = "SELECT * FROM car WHERE plateNumber LIKE :carPlateNumber";

    @Query("DELETE FROM Car")
    void deleteAll();

    @Query(SELECT_ALL_NOT_RENTED)
    List<Car> getAllNotRentedCars();

    @Query(SELECT_ALL_RENTED)
    List<Car> getAllRentedCars();

    @Query(SELECT_ALL)
    List<Car> getAllCars();

    @Query(SELECT_CAR_BY_PLATENUMBER)
    Car getCarFromPlate(String carPlateNumber);

    @Query("SELECT COUNT(*) from car")
    int getNumberOfCars();
}
