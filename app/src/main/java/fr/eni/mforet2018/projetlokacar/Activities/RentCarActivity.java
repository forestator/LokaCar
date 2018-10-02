package fr.eni.mforet2018.projetlokacar.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Adapters.CarAdapter;
import fr.eni.mforet2018.projetlokacar.Adapters.ClickCarListener;
import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class RentCarActivity extends AppCompatActivity implements ClickCarListener {

    private List<Car> notRentedCarList;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        appDatabase = Connexion.getConnexion(this);
        getCarDatas();
    }

    private void getCarDatas() {
        new AsyncGetNorRentedCars().execute();
    }

    @Override
    public void onClickCar(Car car) {
        Toast.makeText(this, car.getPlateNumber(), Toast.LENGTH_SHORT).show();
    }

    private class AsyncGetNorRentedCars extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            notRentedCarList = appDatabase.carDAO().getAllNotRentedCars();
            for(Car car : notRentedCarList){
                if (car.isRented()){
                    notRentedCarList.remove(car);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback();
        }
    }

    private void callback(){
        recyclerView = this.findViewById(R.id.NotRentedCarList);
        recyclerView.setHasFixedSize(true);
        CarAdapter cAdapter = new CarAdapter(notRentedCarList, this);
        recyclerView.setAdapter(cAdapter);
    }
}
