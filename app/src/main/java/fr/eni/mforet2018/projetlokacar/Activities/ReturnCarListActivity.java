package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Adapters.CarAdapter;
import fr.eni.mforet2018.projetlokacar.Adapters.ClickCarListener;
import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class ReturnCarListActivity extends AppCompatActivity implements ClickCarListener {

    private List<Car> rentedCarsList;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_car);
        appDatabase = Connexion.getConnexion(this);
        getCarDatas();
    }

    private void getCarDatas() {
        new ReturnCarListActivity.AsyncGetRentedCars().execute();
    }

    @Override
    public void onClickCar(Car car) {
        Intent detailsIntent = new Intent(this, CarDetailsActivity.class);
        detailsIntent.putExtra("car",car);
        startActivity(detailsIntent);
    }

    private class AsyncGetRentedCars extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            rentedCarsList = appDatabase.carDAO().getAllRentedCars();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback();
        }
    }

    private void callback(){
        recyclerView = this.findViewById(R.id.RentedCarList);
        recyclerView.setHasFixedSize(true);
        CarAdapter cAdapter = new CarAdapter(rentedCarsList, this);
        recyclerView.setAdapter(cAdapter);
    }
}
