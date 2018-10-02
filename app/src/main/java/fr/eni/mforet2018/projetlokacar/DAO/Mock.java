package fr.eni.mforet2018.projetlokacar.DAO;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.time.LocalDate;

import fr.eni.mforet2018.projetlokacar.Activities.OnFinishedDBListener;
import fr.eni.mforet2018.projetlokacar.Entities.Agency;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;

public class Mock extends AsyncTask<Void, Void, Void> {

    private Context myContext;
    private OnFinishedDBListener listener;

    public Mock(Context myContext, OnFinishedDBListener listener) {
        this.myContext = myContext;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDatabase db = Room
                .databaseBuilder(myContext, AppDatabase.class, "AgencyDB")
                .fallbackToDestructiveMigration()
                .build();

        Log.e("INITDB", "Start Mock");

        db.agencyDAO().deleteAll();
        db.carDAO().deleteAll();
        db.clientDAO().deleteAll();
        db.agencyDAO().deleteAll();

        // Create Agency;
        Agency agency = new Agency();
        agency.setName("Michel Locations");
        agency.setManagerName("Roger");
        agency.setPassword("ananas");
        agency.setId((int) db.agencyDAO().insert(agency));


        // Create clients;
        Client pierre = new Client();
        pierre.setFirstName("Pierre");
        pierre.setLastName("Cormier");
        pierre.setMailAddress("12 rue de la défaite 44000 Nantes");
        pierre.setClientId((int) db.clientDAO().insert(pierre));

        // Create clients;
        Client lucio = new Client();
        lucio.setFirstName("Lucio");
        lucio.setLastName("Bukowski");
        lucio.setMailAddress("21, Rue de la plume 56000 Vannes");
        lucio.setClientId((int) db.clientDAO().insert(lucio));

        // Create clients;
        Client jean = new Client();
        jean.setFirstName("Jean-Claude");
        jean.setLastName("Dus");
        jean.setMailAddress("55, rue des tombeurs 33000 Bordeaux");
        jean.setClientId((int) db.clientDAO().insert(jean));

        // Create clients;
        Client dooz = new Client();
        dooz.setFirstName("Dooz");
        dooz.setLastName("Kawa");
        dooz.setMailAddress("45, rue des étoiles du sol 67000 Strasbourg");
        dooz.setClientId((int) db.clientDAO().insert(dooz));

        // Create vehicules
        Car pijot = new Car();
        pijot.setRented(true);
        pijot.setFuel("diesel");
        pijot.setSeatsNumber(4);
        pijot.setPlateNumber("CA-789-BB");
        pijot.setBrand("Peugeot");
        pijot.setModel("106");
        pijot.setDailyPrice(25);
        db.carDAO().insert(pijot);

        Car golf = new Car();
        golf.setFuel("diesel");
        golf.setSeatsNumber(4);
        golf.setPlateNumber("GH-488-FD");
        golf.setBrand("Volkswagen");
        golf.setModel("Golf");
        golf.setDailyPrice(50);
        db.carDAO().insert(golf);

        golf.setPlateNumber("FV-556-GT");
        golf.setFuel("essence");
        db.carDAO().insert(golf);

        golf.setPlateNumber("GF-478-GK");
        db.carDAO().insert(golf);

        // Create loc
        LocationFile loc = new LocationFile();
        loc.setClientId(pierre.getClientId());
        loc.setCarPlateNumber(pijot.getPlateNumber());
        loc.setStartOfRentDate(LocalDate.of(2018, 10,01));
        loc.setEndOfRentDate(LocalDate.of(2018, 10,06));

        loc.setId((int) db.locationFileDAO().insert(loc));

        Log.e("INITDB", "end of mock");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.goToHomeActivity();
    }
}
