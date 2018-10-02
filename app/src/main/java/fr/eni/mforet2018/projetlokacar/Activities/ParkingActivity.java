package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Adapters.CarAdapter;
import fr.eni.mforet2018.projetlokacar.Adapters.ClickCarListener;
import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class ParkingActivity extends AppCompatActivity implements ClickCarListener{

    private List<Car> carsList;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        appDatabase = Connexion.getConnexion(this);
        getCarDatas();
    }

    private void getCarDatas() {
        new ParkingActivity.AsyncGetCars().execute();
    }

    @Override
    public void onClickCar(Car car) {
        Intent detailsIntent = new Intent(this, CarDetailsActivity.class);
        detailsIntent.putExtra("car",car);
        startActivity(detailsIntent);
    }

    private class AsyncGetCars extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            carsList = appDatabase.carDAO().getAllCars();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback();
        }
    }

    private void callback(){
        recyclerView = this.findViewById(R.id.AllCarsList);
        recyclerView.setHasFixedSize(true);
        CarAdapter cAdapter = new CarAdapter(carsList, this);
        recyclerView.setAdapter(cAdapter);
    }
}
