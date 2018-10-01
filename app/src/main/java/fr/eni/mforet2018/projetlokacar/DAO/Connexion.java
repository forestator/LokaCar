package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Room;
import android.content.Context;

public class Connexion {
    public static AppDatabase getConnexion(Context context)
    {
        return Room.databaseBuilder(context,AppDatabase.class,"AgencyDB").allowMainThreadQueries().build();
    }
}
