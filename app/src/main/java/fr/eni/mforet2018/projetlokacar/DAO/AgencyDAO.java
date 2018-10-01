package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.Agency;

@Dao
public interface AgencyDAO extends GenericDAO<Agency> {

    String SELECT_ALL = "SELECT * FROM agency";
    String SELECT_BY_ID = "SELECT * FROM agency WHERE id in (:agencyId)";
    String LOGIN = "SELECT * FROM agency WHERE password in (:password)";

    @Query(SELECT_ALL)
    List<Agency> getAllAgency();

    @Query(SELECT_BY_ID)
    Agency getAgencyById(int agencyId);

    @Query(LOGIN)
    Agency loginAgency(String password);

    @Query("DELETE FROM Agency")
    void deleteAll();

}
