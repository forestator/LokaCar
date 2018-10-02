package fr.eni.mforet2018.projetlokacar.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.Car;
import fr.eni.mforet2018.projetlokacar.R;

public class CarAdapter  extends RecyclerView.Adapter<CarAdapter.ViewHolder>{

    private List<Car> cars;
    private ClickCarListener clickCarListener;

    public CarAdapter(List<Car> cars,ClickCarListener clickCarListener) {
        this.cars = cars;
        this.clickCarListener = clickCarListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = this.cars.get(position);
        holder.tvBrandName.setText(car.getBrand());
        holder.tvModele.setText(car.getModel());
        holder.tvPlateNumber.setText(car.getPlateNumber());
        if (car.isRented()){
            holder.iCar.setImageResource(R.drawable.lokacar_ic_car_blue_90);
        } else {
            holder.iCar.setImageResource(R.drawable.lokacar_ic_car_yellow_90);
        }
        holder.tvFuel.setText(car.getFuel());
        holder.tvCarType.setText(car.getType());
        String seatsNb = car.getSeatsNumber() + " places";
        holder.tvSeats.setText(seatsNb);
    }

    @Override
    public int getItemCount() {
        return this.cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlateNumber;
        private TextView tvModele;
        private TextView tvBrandName;
        private TextView tvFuel;
        private TextView tvCarType;
        private TextView tvSeats;
        private ImageView iCar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvPlateNumber = itemView.findViewById(R.id.tvPlateNumber);
            this.tvModele = itemView.findViewById(R.id.tvCarModel);
            this.tvBrandName = itemView.findViewById(R.id.tvCarBrand);
            this.iCar = itemView.findViewById(R.id.ImgCar);
            this.tvFuel = itemView.findViewById(R.id.tvFuel);
            this.tvCarType = itemView.findViewById(R.id.tvCarType);
            this.tvSeats = itemView.findViewById(R.id.tvSeats);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCarListener.onClickCar(cars.get(ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }
}
