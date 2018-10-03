package fr.eni.mforet2018.projetlokacar.util;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.widget.Toast;

import fr.eni.mforet2018.projetlokacar.Entities.Client;

public class SMSUtil {

    public void envoyerSMS(Client client, String message, Context context)
    {
        new Worker(client,message, context).execute();
    }

    class Worker extends AsyncTask<Void,Void,Boolean>
    {
        private Client client;
        private String message;
        private Context context;

        public Worker(Client client, String message, Context context) {
            this.client = client;
            this.message = message;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(client.getPhoneNumber(),null,message,null,null);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            String messageConfirmation = "Envoyé avec Succès";
            if(!aBoolean)
            {
                messageConfirmation="Echec de l'envoi";
            }
            Toast.makeText(context, messageConfirmation, Toast.LENGTH_SHORT).show();
        }
    }
}
