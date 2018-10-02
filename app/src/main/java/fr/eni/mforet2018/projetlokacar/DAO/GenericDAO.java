package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GenericDAO<T> {

    @Insert
    long insert(T entity);

    @Insert
    long[] insert(List<T> entities);

    @Delete
    void delete(T entity);

    @Update
    void update(T entity);
}
