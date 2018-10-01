package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.Agency;

@Dao
public interface AgencyDAO extends GenericDAO<Agency> {

    String LOGIN = "SELECT * FROM agency WHERE password in (:password)";

    @Query(LOGIN)
    Agency loginAgency(String password);

    @Query("DELETE FROM Agency")
    void deleteAll();

}
