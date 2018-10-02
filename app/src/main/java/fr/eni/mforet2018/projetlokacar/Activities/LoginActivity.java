package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.eni.mforet2018.projetlokacar.DAO.Mock;
import fr.eni.mforet2018.projetlokacar.R;

public class LoginActivity extends AppCompatActivity implements OnFinishedDBListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //TODO:Remove for prod
        new Mock(this,this).execute();
    }

    public void onLoginClick(View view) {
        TextView password = findViewById(R.id.edPassword);
        if (isValidPassword(password.getText().toString())) {
            new Mock(this,this).execute();
        } else {
            Toast.makeText(this, "Erreur de mot de passe, Veuillez reessayer", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidPassword(String pass) {
        return "ananas".equals(pass);
    }

    @Override
    public void goToHomeActivity() {
        Intent intent= new Intent(this, HomeActivity.class);
        Toast.makeText(this, "Bonjour Michel !", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}
