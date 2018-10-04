package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.eni.mforet2018.projetlokacar.DAO.AppDatabase;
import fr.eni.mforet2018.projetlokacar.DAO.Connexion;
import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.Entities.LocationFile;
import fr.eni.mforet2018.projetlokacar.R;

import static fr.eni.mforet2018.projetlokacar.Activities.RentActivity.REQUEST_IMAGE_CAPTURE;

public class ReturnActivity extends AppCompatActivity {
    private StorageReference stockageCloud;
    private Car currentCar;
    private LocationFile currentLocationFile;
    private Client currentClient;
    private AppDatabase appDatabase;
    Uri downloadUrl=null;
    String localImageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        ImageView img = findViewById(R.id.CarFullPicture);
        this.stockageCloud = FirebaseStorage.getInstance().getReference();/*
        this.stockageCloud = FirebaseStorage.getInstance().getReference();
        if (currentCar.getPicture() != null) {
            StorageReference httpsReference = this.stockageCloud.getStorage().getReferenceFromUrl(currentCar.getPicture().toString());
            try {
                final File localFile = File.createTempFile("images", "jpg");
                httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        ImageView iv = findViewById(R.id.CarFullPicture);
                        try {
                            iv.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(localFile)));
                            Log.i("THIERRY", "Chargement OK");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("THIERRY", "Chargement KO : " + exception.toString());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {*/
            img.setImageResource(R.drawable.lokacar_ic_car_90);
       // }
        appDatabase = Connexion.getConnexion(this);

        if (this.getIntent().hasExtra("car")) {
            currentCar = this.getIntent().getParcelableExtra("car");
        } else if (this.getIntent().hasExtra("plateNb")) {
            currentCar = appDatabase.carDAO().getCarFromPlate(this.getIntent().getStringExtra("plateNb"));
        }

        currentLocationFile = appDatabase.locationFileDAO().getLocationFileByCarPlateNumber(currentCar.getPlateNumber());
        currentClient = appDatabase.clientDAO().searchClientById(currentLocationFile.getClientId());
        Log.i("Return_CAR", currentCar.toString());
        Log.i("Return_CAR", currentLocationFile.toString());
        Log.i("Return_CAR", currentClient.toString());

        initClientInformations();
        initCarDatas();
    }

    private void initCarDatas() {
        TextView tv = findViewById(R.id.carBrand);
        tv.setText(currentCar.getBrand());
        tv = findViewById(R.id.carModel);
        tv.setText(currentCar.getModel());
        tv = findViewById(R.id.carFuel);
        tv.setText(currentCar.getFuel());
        tv = findViewById(R.id.seatNumber);
        tv.setText(String.valueOf(currentCar.getSeatsNumber()));
        tv = findViewById(R.id.plateNumber);
        tv.setText(currentCar.getPlateNumber());
    }

    private void initClientInformations() {
        TextView cliLastName = this.findViewById(R.id.tvdClientLastName);
        cliLastName.setText(currentClient.getLastName());

        TextView cliFirstName = this.findViewById(R.id.tvdClientFirstName);
        cliFirstName.setText(currentClient.getFirstName());

        TextView cliAddress = this.findViewById(R.id.tvdClientAddress);
        cliAddress.setText(currentClient.getMailAddress());

        TextView cliEmail = this.findViewById(R.id.tvdClientEmail);
        cliEmail.setText(currentClient.getEmail());

        TextView cliPhone = this.findViewById(R.id.tvdClientPhone);
        cliPhone.setText(currentClient.getPhoneNumber());
    }

    public void onClickPreviousPictures(View view) {
        if (currentLocationFile.getPicturesBeforeRent() != null){
            ImageView i = findViewById(R.id.ivBeforePicture);
            StorageReference httpsReference = this.stockageCloud.getStorage().getReferenceFromUrl(currentLocationFile.getPicturesBeforeRent().toString());
            try {
                final File localFile = File.createTempFile("images", "jpg");
                httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        ImageView iv = findViewById(R.id.ivBeforePicture);
                        try {
                            iv.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(localFile)));
                            Log.i("THIERRY", "Chargement OK");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("THIERRY", "Chargement KO : " + exception.toString());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Pas de photos avant ! (pas malin)", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAfterPictures(View view) {  Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                ImageView iv = findViewById(R.id.ivAfterPicture);
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
                        currentLocationFile.setPicturesAfterRent(downloadUrl.toString());
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

    public void onClickConfirmReturn(View view) {
        currentCar.setRented(false);
        appDatabase.carDAO().update(currentCar);
        Toast.makeText(this, "Retour Validé !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
