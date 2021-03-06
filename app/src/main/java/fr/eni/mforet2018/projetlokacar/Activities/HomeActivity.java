package fr.eni.mforet2018.projetlokacar.Activities;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import fr.eni.mforet2018.projetlokacar.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //TODO: Demande d'autorisations pour accès camera ......
        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE}, 10125);
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, 10128);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 10129);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 10130);
    }

    public void redirRentCar(View view) {
        Intent rentCarIntent = new Intent(this, RentCarListActivity.class);
        startActivity(rentCarIntent);
    }

    public void redirReturnCar(View view) {
        Intent returnCarIntent = new Intent(this, ReturnCarListActivity.class);
        startActivity(returnCarIntent);
    }

    public void redirClientMgmt(View view) {
        Intent clientListIntent = new Intent(this, ClientListActivity.class);
        startActivity(clientListIntent);
    }

    public void redirAgencyMgmt(View view) {
        Intent agencyMgmtIntent = new Intent(this, AgencyManagementActivity.class);
        startActivity(agencyMgmtIntent);
    }

    public void redirAllParking(View view) {
        Intent showParkingIntent = new Intent(this, ParkingActivity.class);
        startActivity(showParkingIntent);
    }

    public void redirSearch(View view) {
        Intent searchIntent = new Intent(this, LocationFileListActivity.class);
        startActivity(searchIntent);
    }
}
