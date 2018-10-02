package fr.eni.mforet2018.projetlokacar.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class CarDetailsActivity extends AppCompatActivity {

    private Car currentCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        currentCar = this.getIntent().getParcelableExtra("car");
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
        tv.setText(String.valueOf(currentCar.getDailyPrice()+" €/jour"));
        ImageView img = findViewById(R.id.CarFullPicture);
        img.setImageResource(R.drawable.lokacar_ic_car_90);
        ToggleButton rentToggle = findViewById(R.id.swRent);
        if (currentCar.isRented()){
            rentToggle.setChecked(true);
        } else {
            rentToggle.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToggleButton rentToggle = findViewById(R.id.swRent);
        if (currentCar.isRented()){
            rentToggle.setChecked(true);
        } else {
            rentToggle.setChecked(false);
        }
    }

    public void rentOrUnrentCar(View view) {
        if (currentCar.isRented()){
            Intent returnIntent = new Intent(this, ReturnActivity.class);
            returnIntent.putExtra("car",currentCar);
            startActivity(returnIntent);
        } else {
            Intent rentIntent = new Intent(this, RentActivity.class);
            rentIntent.putExtra("car",currentCar);
            startActivity(rentIntent);
        }
    }
}
