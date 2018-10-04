package fr.eni.mforet2018.projetlokacar.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Adapters.ClickLocFilesListener;
import fr.eni.mforet2018.projetlokacar.Adapters.LocationFileAdapter;
import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.LocFilCarClient;
import fr.eni.mforet2018.projetlokacar.R;

public class LocationFileListActivity extends AppCompatActivity implements ClickLocFilesListener, Observer<List<LocFilCarClient>> {

    private LiveData<List<LocFilCarClient>> liveData;
    private List<LocFilCarClient> locationFiles;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_file_list);
        appDatabase = Connexion.getConnexion(this);

        getLocationFilesData();

    }

    private void getLocationFilesData() {
        new AsyncGetLocFiles().execute();
    }

    private void callback() {
        liveData.observeForever(this);
    }

    @Override
    public void onClickLocFile(LocFilCarClient locationFile) {
        Intent locFileDetail = new Intent(this, ReturnActivity.class);
        locFileDetail.putExtra("plateNb", locationFile.getPlateNb());
        startActivity(locFileDetail);
    }

    @Override
    public void onChanged(@Nullable List<LocFilCarClient> locFilCarClients) {

        locationFiles = locFilCarClients;

        recyclerView = this.findViewById(R.id.LocationFileList);
        recyclerView.setHasFixedSize(true);
        LocationFileAdapter lAdapter = new LocationFileAdapter(locationFiles, this);
        recyclerView.setAdapter(lAdapter);
    }


    private class AsyncGetLocFiles extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            liveData = appDatabase.locationFileDAO().getLocFilesWithCliNCar();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback();
        }

    }


}
