package fr.eni.mforet2018.projetlokacar.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private LocationFile currentLocFile;
    private List<Client> searchedClientList;
    private StorageReference stockageCloud;
    Uri downloadUrl=null;
    String localImageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        this.stockageCloud = FirebaseStorage.getInstance().getReference();
        currentLocFile = new LocationFile();
        currentCar = this.getIntent().getParcelableExtra("car");
        currentLocFile.setCarPlateNumber(currentCar.getPlateNumber());
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

    private class onClickClientListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView tv = findViewById(R.id.nomClient);
            tv.setText(String.valueOf(listView.getItemAtPosition(i)));
            currentLocFile.setClientId(searchedClientList.get(i).getClientId());
        }
    }


    public DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
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
        currentLocFile.setStartOfRentDate(myCalendar.getTime());
        if (numberOfDays == 0 || currentLocFile.getStartOfRentDate() == null || currentLocFile.getClientId() == 0) {
            Toast.makeText(this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            currentLocFile.setEndOfRentDate(currentLocFile.getStartOfRentDate());
            Calendar c = Calendar.getInstance();
            c.setTime(currentLocFile.getEndOfRentDate());
            c.add(Calendar.DATE, numberOfDays);
            currentLocFile.setEndOfRentDate(c.getTime());
            int totalCost = (int) (numberOfDays * currentCar.getDailyPrice());
            currentLocFile.setTotalCost(totalCost);
            Log.i("FILE", currentLocFile.toString());
            appDatabase.locationFileDAO().insert(currentLocFile);
            Car carToUpdate = appDatabase.carDAO().getCarFromPlate(currentLocFile.getCarPlateNumber());
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
        Log.i("FILE", currentLocFile.toString());
        //TODO: improve searchlcient[O]
        Client currentClient = searchedClientList.get(0);
        switch (requestCode) {
            case 10124:
                if (permissions.length == 2 && grantResults.length == 2 && currentClient.getPhoneNumber() != null) {
                    Log.i("REQUEST_SMS", "J'ai le droit");
                    Intent intentEnvoi = new Intent(this, SMSActivity.class);
                    Bundle extras = new Bundle();
                    extras.putParcelable("client", currentClient);
                    extras.putParcelable("currentLocFile", currentLocFile);
                    intentEnvoi.putExtras(extras);
                    startActivity(intentEnvoi);
                } else {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
        }

    }

    public void takePictureForRent(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photo = createImageFile();
            Uri uriToUpload = FileProvider.getUriForFile(this,
                    "fr.eni.projetphoto.provider",
                    photo);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriToUpload);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {
            case REQUEST_IMAGE_CAPTURE:
                Bitmap imageBitmap = BitmapFactory.decodeFile(this.localImageFilePath);
                ImageView iv = findViewById(R.id.ivPhotoPrise);
                iv.setImageBitmap(imageBitmap);
                uploadImage();
                break;
            default:
                super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void uploadImage() {
        StorageReference cloudRef = this.stockageCloud.child(Paths.get(localImageFilePath).getFileName().toString());
        cloudRef.putFile(Uri.fromFile(new File(localImageFilePath)))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUrl = taskSnapshot.getDownloadUrl();
                        currentLocFile.setPicturesBeforeRent(downloadUrl.toString());
                        Log.i("THIERRY", downloadUrl.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // ...
                    }
                });
    }

    private File createImageFile()  {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            localImageFilePath = image.getAbsolutePath();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
