package fr.eni.mforet2018.projetlokacar.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.R;

public class ClientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        Client client = this.getIntent().getParcelableExtra("client");
//        Toast.makeText(this, client.toString(), Toast.LENGTH_SHORT).show();

        TextView cliIdTitle = this.findViewById(R.id.tvdClientId);
        String titleId = "Fiche du Client #" + client.getClientId();
        cliIdTitle.setText(titleId);

        TextView cliLastName = this.findViewById(R.id.tvdClientLastName);
        cliLastName.setText(client.getLastName());

        TextView cliFirstName = this.findViewById(R.id.tvdClientFirstName);
        cliFirstName.setText(client.getFirstName());

        TextView cliAddress = this.findViewById(R.id.tvdClientAddress);
        cliAddress.setText(client.getMailAddress());

        TextView cliEmail = this.findViewById(R.id.tvdClientEmail);
        cliEmail.setText(client.getEmail());

        TextView cliPhone = this.findViewById(R.id.tvdClientPhone);
        cliPhone.setText(client.getPhoneNumber());
    }
}
