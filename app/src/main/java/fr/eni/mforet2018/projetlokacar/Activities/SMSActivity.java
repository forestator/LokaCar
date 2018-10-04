package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;
import fr.eni.mforet2018.projetlokacar.R;
import fr.eni.mforet2018.projetlokacar.util.SMSUtil;

public class SMSActivity extends AppCompatActivity {

    private Client client;
    private LocationFile locationFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        Bundle extras = getIntent().getExtras();
        this.client = extras.getParcelable("client");
        Log.i("ALED", client.toString());
        this.locationFile = extras.getParcelable("currentLocFile");
        Log.i("ALED", locationFile.toString());

        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        String start = sdf.format(locationFile.getStartOfRentDate());
        String end = sdf.format(locationFile.getEndOfRentDate());

        String message = "Bonjour Madame/Monsieur " + client.getLastName() + " voici le résumé de votre commande :"
                + "\nDate de début de la location : " + start
                + "\nDate de fin de la location : " + end
                + "\nCoût total de la location : " + locationFile.getTotalCost() + "€"
                + "\nImmatriculation de la voiture : " + locationFile.getCarPlateNumber()
                + "\nToute l'équipe vous souhaite une excellente location.";

        new SMSUtil().envoyerSMS(client, "Voici le résumé de votre location : " + message, this);
        TextView tvClientName = findViewById(R.id.tvdClientName);
        TextView tvdClientPhone = findViewById(R.id.tvdClientPhone);
        TextView tvContenuMessage = findViewById(R.id.tvContenuMessage);
        tvClientName.setText(client.getFirstName() + " " + client.getLastName());
        tvdClientPhone.setText(String.valueOf(client.getPhoneNumber()));
        tvContenuMessage.setText(message);
    }

    public void returnToHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
