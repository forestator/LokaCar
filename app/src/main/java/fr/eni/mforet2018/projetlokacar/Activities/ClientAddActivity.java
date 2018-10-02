package fr.eni.mforet2018.projetlokacar.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.R;

public class ClientAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add);
    }

    public void emptyForm(View view) {
        EditText edLastName = this.findViewById(R.id.edNCLastName);
        edLastName.setText("");
        EditText edFristName = this.findViewById(R.id.edNCFirstName);
        edFristName.setText("");
        EditText edEmail = this.findViewById(R.id.edNCEmail);
        edEmail.setText("");
        EditText edAddress = this.findViewById(R.id.edNCAddress);
        edAddress.setText("");
        EditText edPhoneNb = this.findViewById(R.id.edNCPhone);
        edPhoneNb.setText("");
    }

    public void createClient(View view) {
        EditText edLastName = this.findViewById(R.id.edNCLastName);
        String lastName = String.valueOf(edLastName.getText());
        EditText edFristName = this.findViewById(R.id.edNCFirstName);
        String firstName = String.valueOf(edFristName.getText());
        EditText edEmail = this.findViewById(R.id.edNCEmail);
        String email = String.valueOf(edEmail.getText());
        EditText edAddress = this.findViewById(R.id.edNCAddress);
        String addresss = String.valueOf(edAddress.getText());
        EditText edPhoneNb = this.findViewById(R.id.edNCPhone);
        String phoneNb = String.valueOf(edPhoneNb.getText());

        Client newClient = new Client();
        newClient.setFirstName(firstName);
        newClient.setLastName(lastName);
        newClient.setEmail(email);
        newClient.setMailAddress(addresss);
        newClient.setPhoneNumber(phoneNb);

        Toast.makeText(this, newClient.toString(), Toast.LENGTH_SHORT).show();
    }
}
