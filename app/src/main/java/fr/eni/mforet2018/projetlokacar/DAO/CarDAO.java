package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import fr.eni.mforet2018.projetlokacar.Entities.Car;

@Dao
public interface CarDAO extends GenericDAO<Car> {

    @Query("DELETE FROM Car")
    void deleteAll();


}
