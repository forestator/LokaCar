package fr.eni.mforet2018.projetlokacar.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;
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
        String textLocation = "Louer : " + currentCar.getBrand() + " -- " + currentCar.getPlateNumber();
        tv.setText(textLocation);
        listView = findViewById(R.id.listViewClient);
        listView.setOnItemClickListener(new onClickClientListener());
    }

    public void onClickSearchClient(View view) {
        EditText editSearchClient = findViewById(R.id.seekForClient);
        String search = editSearchClient.getText().toString();
        searchedClientList = appDatabase.clientDAO().searchClient(search);
        List<String> displayClientsNames = new ArrayList<>();
        //Affichage liste clients
        for (Client client : searchedClientList) {
            displayClientsNames.add(client.getFirstName() + " " + client.getLastName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayClientsNames);
        listView.setAdapter(adapter);
    }

    public void takePictureForRent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Toast.makeText(this, "Photo enregistrée", Toast.LENGTH_SHORT).show();
/*
            //create a file to write bitmap data
            File f = new File(this.getCacheDir(), "PHOTO1");
            try {
                f.createNewFile();
            //Convert bitmap to byte array
            Bitmap bitmap = imageBitmap;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = null;
                fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, f.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
*/
        }

    }

    private class onClickClientListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView tv = findViewById(R.id.nomClient);
            tv.setText(String.valueOf(listView.getItemAtPosition(i)));
            locationFile.setClientId(searchedClientList.get(i).getClientId());
        }
    }


    public DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            locationFile.setStartOfRentDate(myCalendar.getTime());
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        TextView tv = findViewById(R.id.showDate);
        tv.setText("Début de la location : " + sdf.format(myCalendar.getTime()));
    }

    public void datePicker(View view) {
        new DatePickerDialog(RentActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void confirmRent(View view) {
        EditText et = findViewById(R.id.numberOfDays);
        int numberOfDays = Integer.parseInt(String.valueOf(et.getText()));
        if (numberOfDays == 0 || locationFile.getStartOfRentDate() == null || locationFile.getClientId() == 0) {
            Toast.makeText(this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            locationFile.setEndOfRentDate(locationFile.getStartOfRentDate());

            Calendar c = Calendar.getInstance();
            c.setTime(locationFile.getEndOfRentDate());
            c.add(Calendar.DATE, numberOfDays);
            locationFile.setEndOfRentDate(c.getTime());
            int totalCost = (int) (numberOfDays * currentCar.getDailyPrice());
            locationFile.setTotalCost(totalCost);
            Log.i("FILE", locationFile.toString());
            appDatabase.locationFileDAO().insert(locationFile);
            Car carToUpdate = appDatabase.carDAO().getCarFromPlate(locationFile.getCarPlateNumber());
            carToUpdate.setRented(true);
            appDatabase.carDAO().update(carToUpdate);
            Toast.makeText(this, "Location enregistrée !", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS}, 10124);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("REQUEST_SMS", String.valueOf(requestCode));
        Log.i("REQUEST_SMS", String.valueOf(permissions.length));
        Log.i("REQUEST_SMS", String.valueOf(grantResults.length));
        Log.i("FILE", locationFile.toString());
        //TODO: improve searchlcient[O]
        Client currentClient = searchedClientList.get(0);
        switch (requestCode) {
            case 10124:
                if (permissions.length == 2 && grantResults.length == 2 && currentClient.getPhoneNumber() != null) {
                    Log.i("REQUEST_SMS", "J'ai le droit");
                    Intent intentEnvoi = new Intent(this, SMSActivity.class);
                    Bundle extras = new Bundle();
                    extras.putParcelable("client", currentClient);
                    extras.putParcelable("locationFile", locationFile);
                    intentEnvoi.putExtras(extras);
                    startActivity(intentEnvoi);
                } else {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
            case 10128:
                Toast.makeText(this, "CAMERA", Toast.LENGTH_SHORT).show();
                break;
            case 10129:
                Toast.makeText(this, "CAMERA", Toast.LENGTH_SHORT).show();
                break;
            case 10130:
                Toast.makeText(this, "CAMERA", Toast.LENGTH_SHORT).show();
                break;
            default:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
        }

    }

}
