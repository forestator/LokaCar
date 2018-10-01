package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;

@Dao
public interface LocationFileDAO extends GenericDAO<LocationFile>{

    @Query("DELETE FROM LocationFile")
    void deleteAll();
}
