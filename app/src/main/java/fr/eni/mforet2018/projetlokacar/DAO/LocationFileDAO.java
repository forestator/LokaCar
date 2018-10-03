package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;

@Dao
public interface LocationFileDAO extends GenericDAO<LocationFile>{

    String SELECT_ALL = "SELECT * FROM LocationFile";

    @Query("DELETE FROM LocationFile")
    void deleteAll();

    @Query(SELECT_ALL)
    List<LocationFile> getAll();
}
