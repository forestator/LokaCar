package fr.eni.mforet2018.projetlokacar;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Mock;


public class App extends Application {

    private static App instance;
    private AppDatabase database;

    public static App get() {
        return instance;
    }

    public AppDatabase getDB() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MAXCRETEDB","BONSOIR");
        database = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "AgencyDB")
                .fallbackToDestructiveMigration()
                .build();
        new Mock().execute();
        instance = this;
    }
}
