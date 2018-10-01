package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.Agency;
import fr.eni.mforet2018.projetlokacar.Entities.Car;

@Dao
public interface CarDAO extends GenericDAO<Car> {

    String SELECT_ALL_NOT_RENTED = "SELECT * FROM car WHERE isRented = 0";

    @Query("DELETE FROM Car")
    void deleteAll();

    @Query(SELECT_ALL_NOT_RENTED)
    List<Car> getAllNotRentedCars();

}
