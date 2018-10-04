package fr.eni.mforet2018.projetlokacar.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.LocFilCarClient;
import fr.eni.mforet2018.projetlokacar.R;

public class LocationFileAdapter extends RecyclerView.Adapter<LocationFileAdapter.ViewHolder> {

    private List<LocFilCarClient> locationFiles;
    private ClickLocFilesListener clickLocFilesListener;

    public LocationFileAdapter(List<LocFilCarClient> locationFiles, ClickLocFilesListener clickLocFilesListener) {
        this.locationFiles = locationFiles;
        this.clickLocFilesListener = clickLocFilesListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.locfile_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat sDF = new SimpleDateFormat("dd/MM/yyyy");

        LocFilCarClient locationFile = this.locationFiles.get(position);

        Date rawStartDate = locationFile.getDateStart();
        String startDate = sDF.format(rawStartDate);
        holder.tvStartDate.setText(startDate);

        Date rawEndDate = locationFile.getDateEnd();
        String endDate = sDF.format(rawEndDate);
        holder.tvStartDate.setText(endDate);

        String brandNModel = locationFile.getCarLocBrand() + " - " + locationFile.getCarLocModel();
        holder.tvCarBrandModel.setText(brandNModel);

        String clientFullName = locationFile.getClientFirstName() + " " + locationFile.getClientLastName();
        holder.tvClientName.setText(clientFullName);
    }

    @Override
    public int getItemCount() {
        return locationFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStartDate;
        private TextView tvEndDate;
        private TextView tvClientName;
        private TextView tvCarBrandModel;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvStartDate = itemView.findViewById(R.id.tvLLStartRentDate);
            this.tvEndDate = itemView.findViewById(R.id.tvLLEndRentDate);
            this.tvClientName = itemView.findViewById(R.id.tvLLClientFullName);
            this.tvCarBrandModel = itemView.findViewById(R.id.tvLLCarBrandNModel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickLocFilesListener.onClickLocFile(locationFiles.get(LocationFileAdapter.ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }
}
