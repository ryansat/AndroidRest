package com.hmkcode.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
public class ViewData extends AppCompatActivity {

    private JSONObject jObject;
    private String xResult ="";
    //Seusuaikan url dengan nama domain yang anda gunakan
    //private String url = "http://satriaworld.000webhostapp.com/android/daftarmakanan.php";
    private String url  = "http://10.0.2.2/books/search/?key=123&keyword=pemrograman";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        TextView txtResult = (TextView)findViewById(R.id.TextViewResult);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        xResult = getRequest(url);
        try {
            parse(txtResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void parse(TextView txtResult) throws Exception {
        jObject = new JSONObject(xResult);
        JSONArray menuitemArray = jObject.getJSONArray("data");
        String sret="";
        for (int i = 0; i < menuitemArray.length(); i++) {
            sret +=menuitemArray.getJSONObject(i)
                    .getString("author").toString()+" : ";
            System.out.println(menuitemArray.getJSONObject(i)
                    .getString("author").toString());
            System.out.println(menuitemArray.getJSONObject(i).getString(
                    "sinopsis").toString());
            sret +=menuitemArray.getJSONObject(i).getString(
                    "sinopsis").toString()+"\n";
        }
        txtResult.setText(sret);
    }
    /**
     * Method untuk Mengirimkan data kes erver
     * event by button login diklik
     *

     */
    public String getRequest(String Url){
        String sret="";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);
        try{
            HttpResponse response = client.execute(request);
            sret =request(response);
        }catch(Exception ex){
            Toast.makeText(this,"Gagal "+ex, Toast.LENGTH_SHORT).show();
        }
        return sret;
    }
    /**
     * Method untuk Menenrima data dari server
     * @param response
     * @return
     */
    public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }
}
