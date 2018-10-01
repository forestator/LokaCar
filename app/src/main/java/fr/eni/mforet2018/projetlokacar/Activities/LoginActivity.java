package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.eni.mforet2018.projetlokacar.App;
import fr.eni.mforet2018.projetlokacar.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginClick(View view) {
        TextView password = findViewById(R.id.edPassword);
        if (App.get().isValidPassword(password.getText().toString())) {
            Intent intent= new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erreur de mot de passe, Veuillez reessayer", Toast.LENGTH_LONG).show();
        }
    }
}
