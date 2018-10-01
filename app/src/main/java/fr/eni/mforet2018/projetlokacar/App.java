package fr.eni.mforet2018.projetlokacar;

import android.app.Application;
import android.arch.persistence.room.Room;
import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Mock;


public class App extends Application {

    private static App instance;
    private AppDatabase database;
    private String password = "ananas";

    public static App get() {
        return instance;
    }

    public AppDatabase getDB() {
        return database;
    }

    public boolean isValidPassword(String pass) {
        return this.password.equals(pass);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "AgencyDB")
                .fallbackToDestructiveMigration()
                .build();
        new Mock().execute();

        instance = this;
    }
}
