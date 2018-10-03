package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class CarAddActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);

        appDatabase = Connexion.getConnexion(this);

        Spinner spCarTypes = findViewById(R.id.spCarType);
        ArrayAdapter<CharSequence> adapterCarTypes = ArrayAdapter.createFromResource(this,
                R.array.car_types, android.R.layout.simple_spinner_item);

        adapterCarTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarTypes.setAdapter(adapterCarTypes);

        Spinner spCarFuel = findViewById(R.id.spCarFuel);
        ArrayAdapter<CharSequence> adapterCarFuel = ArrayAdapter.createFromResource(this,
                R.array.car_fuels, android.R.layout.simple_spinner_item);
        adapterCarFuel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarFuel.setAdapter(adapterCarFuel);


    }

    public void createCar(View view) {
        Log.i("Caro", "createCar: ");
        EditText edCarPlate = findViewById(R.id.edNCaPlate);
        String carPlate = edCarPlate.getText().toString();

        EditText edCarBrand = findViewById(R.id.edNCaBrand);
        String carBrand = edCarBrand.getText().toString();

        EditText edCarSeatsNb = findViewById(R.id.edNCaSeatsNb);
        int carSeatsNb = Integer.parseInt(edCarSeatsNb.getText().toString());

        EditText edCarPrice = findViewById(R.id.edNCaPrice);
        float carPrice = Float.parseFloat(edCarPrice.getText().toString());

        Spinner spCarTypes = findViewById(R.id.spCarType);
        String carType = (String) spCarTypes.getSelectedItem();

        Spinner spCarFuel = findViewById(R.id.spCarFuel);
        String carFuel = (String) spCarFuel.getSelectedItem();

        Car newCar = new Car();
        newCar.setType(carType);
        newCar.setBrand(carBrand);
        newCar.setDailyPrice(carPrice);
        newCar.setFuel(carFuel);
        newCar.setPlateNumber(carPlate);
        newCar.setSeatsNumber(carSeatsNb);
        newCar.setRented(false);

        appDatabase.carDAO().insert(newCar);
        Toast.makeText(this, "Voiture ajoutée au Parking !", Toast.LENGTH_SHORT).show();

        Intent parkingIntent = new Intent(this, ParkingActivity.class);
        startActivity(parkingIntent);
    }

    public void emptyForm(View view) {
    }


}
