package com.example.oxfordemytology;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class request extends AsyncTask<String,Integer,String>{
    final String app_id = "0bcf531f";
    TextView t1;
    final String app_key = "b6217a5e52a0f3edad9a7953893fca8e";
   Context context;
    request(Context context, TextView t1){
        this.context = context;
        this.t1 = t1;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String ety;
        //Toast.makeText(context,s, Toast.LENGTH_LONG).show();
        try {
            JSONObject j = new JSONObject(s);
            JSONArray results = j.getJSONArray("results");

            JSONObject l_e = results.getJSONObject(0);
            JSONArray l_a = l_e.getJSONArray("lexicalEntries");

            JSONObject e = l_a.getJSONObject(0);
            JSONArray ea = e.getJSONArray("entries");

            JSONObject jsonObject = ea.getJSONObject(0);
            JSONArray etymologies = jsonObject.getJSONArray("etymologies");

            ety = etymologies.getString(0);
            t1.setText(ety);
          //  Toast.makeText(context,ety, Toast.LENGTH_SHORT).show();

        }catch(JSONException e){
            e.printStackTrace();;
        }
        }
}
