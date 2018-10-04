package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class CarDetailsActivity extends AppCompatActivity {
    private StorageReference stockageCloud;
    private Car currentCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        currentCar = this.getIntent().getParcelableExtra("car");
        this.stockageCloud = FirebaseStorage.getInstance().getReference();
        initView();
    }

    private void initView() {
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
        tv = findViewById(R.id.dailyPrice);
        tv.setText(String.valueOf(currentCar.getDailyPrice() + " €/jour"));
        ImageView img = findViewById(R.id.CarFullPicture);

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
        } else {
            img.setImageResource(R.drawable.lokacar_ic_car_90);
        }

        ToggleButton rentToggle = findViewById(R.id.swRent);
        if (currentCar.isRented()) {
            rentToggle.setChecked(true);
        } else {
            rentToggle.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    public void rentOrUnrentCar(View view) {
        if (currentCar.isRented()) {
            Intent returnIntent = new Intent(this, ReturnActivity.class);
            returnIntent.putExtra("car", currentCar);
            startActivity(returnIntent);
        } else {
            Intent rentIntent = new Intent(this, RentActivity.class);
            rentIntent.putExtra("car", currentCar);
            startActivity(rentIntent);
        }
    }
}
