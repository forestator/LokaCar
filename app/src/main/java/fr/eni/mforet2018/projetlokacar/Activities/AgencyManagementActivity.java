package fr.eni.mforet2018.projetlokacar.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;
import fr.eni.mforet2018.projetlokacar.R;

public class AgencyManagementActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    private float turnover = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_management);
        appDatabase = Connexion.getConnexion(this);
        List<LocationFile> llf = appDatabase.locationFileDAO().getAll();
        for (LocationFile lf : llf) {
            turnover = turnover + lf.getTotalCost();
        }
        int numberOfClients = appDatabase.clientDAO().getNumberOfClients();
        int numberOfCars = appDatabase.carDAO().getNumberOfCars();
        int numberOfRentedCars = 0;
        List<Car> rentedCars = appDatabase.carDAO().getAllRentedCars();
        for (Car car: rentedCars){
            numberOfRentedCars++;
        }
        TextView tvNumberOfClients = findViewById(R.id.tvNumberOfClients);
        TextView tvNumberOfCars = findViewById(R.id.tvNumberOfCars);
        TextView tvNumberOfRentedCars = findViewById(R.id.tvNumberOfRentedCars);
        TextView tvTurnover = findViewById(R.id.tvTurnover);
        tvNumberOfClients.setText(String.valueOf(numberOfClients));
        tvNumberOfCars.setText(String.valueOf(numberOfCars));
        tvNumberOfRentedCars.setText(String.valueOf(numberOfRentedCars));
        tvTurnover.setText(String.valueOf(turnover));

    }
}
