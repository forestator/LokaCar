package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import fr.eni.mforet2018.projetlokacar.Entities.Client;

@Dao
public interface ClientDAO extends GenericDAO<Client> {

    @Query("DELETE FROM Client")
    void deleteAll();

    @Insert
    long insert(Client client);
}
