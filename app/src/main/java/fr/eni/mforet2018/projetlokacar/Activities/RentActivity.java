package fr.eni.mforet2018.projetlokacar.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;
import fr.eni.mforet2018.projetlokacar.R;

public class RentActivity extends AppCompatActivity {

    private Car currentCar;
    private ListView listView;
    private AppDatabase appDatabase;
    private Calendar myCalendar = Calendar.getInstance();
    private LocationFile locationFile;
    private List<Client> searchedClientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        locationFile = new LocationFile();
        currentCar = this.getIntent().getParcelableExtra("car");
        locationFile.setCarPlateNumber(currentCar.getPlateNumber());
        appDatabase = Connexion.getConnexion(this);
        TextView tv = findViewById(R.id.tvRentCarBrand);
        tv.setText("Louer : "+currentCar.getBrand()+" -- "+currentCar.getPlateNumber());
        listView = findViewById(R.id.listViewClient);
        listView.setOnItemClickListener(new onClickClientListener());
    }

    public void onClickSearchClient(View view) {
        EditText editSearchClient = findViewById(R.id.seekForClient);
        String search = editSearchClient.getText().toString();
        searchedClientList = appDatabase.clientDAO().searchClient(search);
        List<String> displayClientsNames = new ArrayList<>();
        //Affichage liste clients
        for(Client client : searchedClientList){
            displayClientsNames.add(client.getFirstName()+" "+client.getLastName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, displayClientsNames);
        listView.setAdapter(adapter);
    }

    public DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //TODO:A ameliorer +1 ? ???
            locationFile.setStartOfRentDate(LocalDate.of(year,monthOfYear+1,dayOfMonth));
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        TextView tv = findViewById(R.id.showDate);
        tv.setText("Début de la location : "+sdf.format(myCalendar.getTime()));
    }

    public void datePicker(View view) {
        new DatePickerDialog(RentActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private class onClickClientListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          TextView tv = findViewById(R.id.nomClient);
            tv.setText(String.valueOf(listView.getItemAtPosition(i)));
            locationFile.setClientId(searchedClientList.get(i).getClientId());
        }
    }

    public void confirmRent(View view) {
        EditText et = findViewById(R.id.numberOfDays);
        int numberOfDays = Integer.parseInt(String.valueOf(et.getText()));
        if (numberOfDays == 0 || locationFile.getStartOfRentDate() == null || locationFile.getClientId() == 0) {
            Toast.makeText(this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            locationFile.setEndOfRentDate(locationFile.getStartOfRentDate());
            locationFile.setEndOfRentDate(locationFile.getEndOfRentDate().plusDays(numberOfDays));
            int totalCost = (int) (numberOfDays * currentCar.getDailyPrice());
            locationFile.setTotalCost(totalCost);
            appDatabase.locationFileDAO().insert(locationFile);
            Car carToUpdate = appDatabase.carDAO().getCarFromPlate(locationFile.getCarPlateNumber());
            carToUpdate.setRented(true);
            appDatabase.carDAO().update(carToUpdate);
            Toast.makeText(this, "Location enregistrée !", Toast.LENGTH_SHORT).show();
            Intent searchIntent = new Intent(this, HomeActivity.class);
            startActivity(searchIntent);
        }
    }
}
