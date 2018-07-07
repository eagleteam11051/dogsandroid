package com.tbm.dogs.activities.viecdangco;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.tbm.dogs.Helper.Var;
import com.tbm.dogs.model.obj.Job;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HandlerP {
    private Results results;
    public HandlerP(Results results){
        this.results = results;
    }

    public void getJobs() {
        new handlerJobs().execute(Var.API_GET_ORDERS,Var.shiper.getHero_id());
    }

    public class handlerJobs extends AsyncTask<String,Void,String>{
        OkHttpClient client = new OkHttpClient();
        @Override
        protected String doInBackground(String... strings) {
            Uri uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendQueryParameter("hero_id",strings[1])
                    .build();
            Log.e("uriViecdangco",uri.toString());
            URL url = null;
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            builder.addHeader(Var.HEADER,Var.tokenOther);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                Log.e("getviecdangco:",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            handlerResults(s);
        }

        private void handlerResults(String s) {
            ArrayList<Job> jobs = new ArrayList<>();
            //parse job
            //TODO
        }
    }
}
