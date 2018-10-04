package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;
import fr.eni.mforet2018.projetlokacar.R;

public class ReturnActivity extends AppCompatActivity {

    private Car currentCar;
    private LocationFile currentLocationFile;
    private Client currentClient;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        ImageView img = findViewById(R.id.CarFullPicture);
        img.setImageResource(R.drawable.lokacar_ic_car_90);

        appDatabase = Connexion.getConnexion(this);

        if (this.getIntent().hasExtra("car")) {
            currentCar = this.getIntent().getParcelableExtra("car");
        } else if (this.getIntent().hasExtra("plateNb")) {
            currentCar = appDatabase.carDAO().getCarFromPlate(this.getIntent().getStringExtra("plateNb"));
        }

        currentLocationFile = appDatabase.locationFileDAO().getLocationFileByCarPlateNumber(currentCar.getPlateNumber());
        currentClient = appDatabase.clientDAO().searchClientById(currentLocationFile.getClientId());
        Log.i("Return_CAR", currentCar.toString());
        Log.i("Return_CAR", currentLocationFile.toString());
        Log.i("Return_CAR", currentClient.toString());

        initClientInformations();
        initCarDatas();
    }

    private void initCarDatas() {
        TextView tv = findViewById(R.id.carBrand);
        tv.setText(currentCar.getBrand());
        tv = findViewById(R.id.carModel);
        tv.setText(currentCar.getModel());
        tv = findViewById(R.id.carFuel);
        tv.setText(currentCar.getFuel());
        tv = findViewById(R.id.seatNumber);
        tv.setText(String.valueOf(currentCar.getSeatsNumber()));
        tv = findViewById(R.id.plateNumber);
        tv.setText(currentCar.getPlateNumber());
    }

    private void initClientInformations() {
        TextView cliLastName = this.findViewById(R.id.tvdClientLastName);
        cliLastName.setText(currentClient.getLastName());

        TextView cliFirstName = this.findViewById(R.id.tvdClientFirstName);
        cliFirstName.setText(currentClient.getFirstName());

        TextView cliAddress = this.findViewById(R.id.tvdClientAddress);
        cliAddress.setText(currentClient.getMailAddress());

        TextView cliEmail = this.findViewById(R.id.tvdClientEmail);
        cliEmail.setText(currentClient.getEmail());

        TextView cliPhone = this.findViewById(R.id.tvdClientPhone);
        cliPhone.setText(currentClient.getPhoneNumber());
    }

    public void onClickPreviousPictures(View view) {
    }

    public void onClickAfterPictures(View view) {
    }

    public void onClickConfirmReturn(View view) {
        currentCar.setRented(false);
        appDatabase.carDAO().update(currentCar);
        Toast.makeText(this, "Retour Validé !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
