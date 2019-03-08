package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.List;

public class AllPigeons extends AppCompatActivity {

    String IP = "192.168.0.105";

    String TAG = "XIAN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pigeons);

        readAll();



    }


    private void readAll(){
        String readURL = "http://"+IP+"/BdRacingPIgeonDatabase/Pigeons/read.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                readURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String jsonRes = response.toString();

                        Log.i(TAG,jsonRes);

                        Gson gson = new Gson();
                        JsonParser parser = new JsonParser();
                        JsonObject object = (JsonObject) parser.parse(jsonRes);// response will be the json String
                        PigeonList emp = gson.fromJson(object, PigeonList.class);

                        Log.i(TAG,emp.getRecords().toString());

                        List<Pigeon> pp = emp.getRecords();

                        Log.i(TAG,"pp size : "+pp.size());
                        Log.i(TAG,"pp 0 name = "+pp.get(0).getPigeonRingNumber());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG,"REST API ERROR on READ ALL:"+error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);


    }
}
