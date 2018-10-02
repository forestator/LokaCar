package fr.eni.mforet2018.projetlokacar.Activities;

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
    }

    public void redirRentCar(View view) {
        Toast.makeText(this, "Rent car", Toast.LENGTH_SHORT).show();
        Intent rentCarIntent = new Intent(this, RentCarListActivity.class);
        startActivity(rentCarIntent);
    }

    public void redirReturnCar(View view) {
        Toast.makeText(this, "Return", Toast.LENGTH_SHORT).show();
        Intent returnCarIntent = new Intent(this, ReturnCarListActivity.class);
        startActivity(returnCarIntent);
    }

    public void redirClientMgmt(View view) {
        Toast.makeText(this, "Client Management", Toast.LENGTH_SHORT).show();
        Intent clientListIntent = new Intent(this, ClientListActivity.class);
        startActivity(clientListIntent);
    }

    public void redirAgencyMgmt(View view) {
        Toast.makeText(this, "Agency Management", Toast.LENGTH_SHORT).show();
        Intent agencyMgmtIntent = new Intent(this, AgencyManagementActivity.class);
        startActivity(agencyMgmtIntent);
    }

    public void redirAllParking(View view) {
        Toast.makeText(this, "Show Parking", Toast.LENGTH_SHORT).show();
        Intent showParkingIntent = new Intent(this, ParkingActivity.class);
        startActivity(showParkingIntent);
    }

    public void redirSearch(View view) {
        Toast.makeText(this, "search client or car", Toast.LENGTH_SHORT).show();
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }
}
