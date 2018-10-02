package fr.eni.mforet2018.projetlokacar.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.eni.mforet2018.projetlokacar.Entities.Client;
import fr.eni.mforet2018.projetlokacar.R;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private List<Client> clients;
    private ClickClientListener clickClientListener;

    public ClientAdapter(List<Client> clients, ClickClientListener clickClientListener) {
        this.clients = clients;
        this.clickClientListener = clickClientListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Client client = this.clients.get(position);
        String clientFullName = client.getFirstName() + " " + client.getLastName();
        holder.tvClientFullName.setText(clientFullName);
        holder.tvMailAddress.setText(client.getMailAddress());
        holder.tvPhone.setText(client.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return this.clients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvClientFullName;
        private TextView tvMailAddress;
        private TextView tvPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvClientFullName = itemView.findViewById(R.id.tvClientFullName);
            this.tvMailAddress = itemView.findViewById(R.id.tvMailAddress);
            this.tvPhone = itemView.findViewById(R.id.tvPhone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickClientListener.onClickClient(clients.get(ClientAdapter.ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }
}
