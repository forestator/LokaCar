package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.eni.mforet2018.projetlokacar.Adapters.ClickClientListener;
import fr.eni.mforet2018.projetlokacar.Adapters.ClientAdapter;
import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.R;

public class ClientListActivity extends AppCompatActivity implements ClickClientListener {

    private List<Client> clients;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private List<Client> searchedClientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        appDatabase = Connexion.getConnexion(this);
        getClientDatas();
    }

    private void getClientDatas() {
        new AsyncGetClients().execute();
    }

    @Override
    public void onClickClient(Client client) {
        String infos = client.getClientId() + " " + client.getLastName() + " " + client.getFirstName();
        Toast.makeText(this, infos, Toast.LENGTH_SHORT).show();

        Intent cliDetailsIntent = new Intent(this, ClientDetailsActivity.class);
        cliDetailsIntent.putExtra("client", client);
        startActivity(cliDetailsIntent);
    }

    private void callback() {
        recyclerView = this.findViewById(R.id.ClientList);
        recyclerView.setHasFixedSize(true);
        ClientAdapter cAdapter = new ClientAdapter(clients, this);
        recyclerView.setAdapter(cAdapter);
    }

    public void redirNewClient(View view) {
        Intent newClientIntent = new Intent(this, ClientAddActivity.class);
        startActivity(newClientIntent);
    }

    public void onClickSearchClient(View view) {
        EditText editSearchClient = findViewById(R.id.seekForClient);
        String search = editSearchClient.getText().toString();
        clients = appDatabase.clientDAO().searchClient(search);
        callback();
    }

    private class AsyncGetClients extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            clients = appDatabase.clientDAO().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback();
        }
    }


}
