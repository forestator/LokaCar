package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.Entities.Client;

@Dao
public interface ClientDAO extends GenericDAO<Client> {

    String SELECT_ALL = "SELECT * FROM client";

    @Query("DELETE FROM Client")
    void deleteAll();

    @Insert
    long insert(Client client);

    @Query(SELECT_ALL)
    List<Client> getAll();

    @Query("Select * from client WHERE lastName LIKE :search")
    List<Client> searchClient(String search);
}
