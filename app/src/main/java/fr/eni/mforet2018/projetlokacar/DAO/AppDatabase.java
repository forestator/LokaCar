package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import fr.eni.mforet2018.projetlokacar.Entities.Agency;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.Entities.Converters.Converters;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;

@Database(entities = {Agency.class,Car.class,Client.class,LocationFile.class,},version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AgencyDAO agencyDAO();
    public abstract CarDAO carDAO();
    public abstract ClientDAO clientDAO();
    public abstract LocationFileDAO locationFileDAO();
}

