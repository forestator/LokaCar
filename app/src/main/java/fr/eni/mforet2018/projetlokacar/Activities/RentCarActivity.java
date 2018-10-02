package fr.eni.mforet2018.projetlokacar.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.App;
import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class RentCarActivity extends AppCompatActivity {

    private List<Car> notRentedCarList;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        App.get();
        appDatabase = Connexion.getConnexion(this);
        getCarDatas();
    }

    private void getCarDatas() {
        new AsyncGetNorRentedCars().execute();
    }

    private class AsyncGetNorRentedCars extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            notRentedCarList = appDatabase.carDAO().getAllNotRentedCars();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for(Car car : notRentedCarList){
            Toast.makeText(RentCarActivity.this, car.getPlateNumber(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
