package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllPigeons extends AppCompatActivity {

    String IP = "192.168.0.105";

    String TAG = "XIAN";

    Button btn_allPigeonAP, btn_topPigeonsAP, btn_searchAP;
    RecyclerView RV_recyclerView;
    EditText ET_searchText;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Pigeon> PigeonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pigeons);

        initial();





    }

    private void initial(){

        btn_allPigeonAP = findViewById(R.id.btn_allPigeonAP);
        btn_topPigeonsAP = findViewById(R.id.btn_topPigeonsAP);
        btn_searchAP = findViewById(R.id.btn_searchAP);
        RV_recyclerView = findViewById(R.id.RV_recyclerView);
        ET_searchText = findViewById(R.id.ET_searchText);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        RV_recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        RV_recyclerView.setLayoutManager(layoutManager);



    }

    private void setAdapter(){
        // specify an adapter (see also next example)
        mAdapter = new MyRVAdapter(getApplicationContext(),PigeonList);
        RV_recyclerView.setAdapter(mAdapter);

    }


    public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {

        List<Pigeon> pigeonList;
        Context context;

        public MyRVAdapter(Context context, List<Pigeon> pList) {
            super();
            this.context = context;
            this.pigeonList = pList;
            Log.i(TAG,"RECYCLE VIEW COnstructor");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_pigeon_for_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

            Log.i(TAG,i+" RECYCLE VIEW "+(pigeonList.get(i).getPigeonRingNumber()));

            /*viewHolder.TV_productName.setText(productList.get(i).getProductName());
            viewHolder.TV_productQuantity.setText(productList.get(i).getProductStockQuantity()+" pc");
            viewHolder.TV_productPrice.setText(productList.get(i).getProductPrice()+" BDT");*/

            //Picasso.get().load(productList.get(i).getProductPicture()).fit().centerCrop().into(viewHolder.IV_productPic);



            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, final int position, boolean isLongClick) {

                    /*viewHolder.IV_productPic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShowPicture(productList.get(position).getProductPicture());
                        }
                    });*/

                    if (isLongClick) {
                        //Toast.makeText(context, "#" + position + " (Long click)", Toast.LENGTH_SHORT).show();
                        //showPopupMenu(view,position);

                    } else {
                        //Toast.makeText(context, "#" + position + " Not Long Click", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return pigeonList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public TextView TV_productName, TV_productBrand, TV_productID, TV_productQuantity, TV_productPrice;
            public ImageView IV_productPic;
            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                /*TV_productName = itemView.findViewById(R.id.TV_productName);
                TV_productQuantity = itemView.findViewById(R.id.TV_productQuantity);
                TV_productPrice = itemView.findViewById(R.id.TV_productPrice);
                IV_productPic = itemView.findViewById(R.id.IV_productPic);*/


                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getPosition(), false);
            }

            @Override
            public boolean onLongClick(View view) {
                clickListener.onClick(view, getPosition(), true);
                return true;
            }
        }

    }

    private void onClickListener(){

        btn_allPigeonAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAll();
            }
        });

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
                        PigeonList pList = gson.fromJson(object, PigeonList.class);

                        Log.i(TAG,pList.getRecords().toString());

                        List<Pigeon> pp = pList.getRecords();

                        PigeonList = pp;
                        setAdapter();

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

    private void readOne(int searchText){
        String searchURL = "http://"+IP+"/api/product/search.php?s="+searchText;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                searchURL,
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
                        Log.i(TAG,"REST API ERROR on Search:"+error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void createProduct(String name, String description, String price, String category_id, String catergory_name){
        String createURL = "http://"+IP+"/api/product/create.php";

        String jsonString = "{\n" +
                "    \"name\" : \"Amazing Pillow 2.0\",\n" +
                "    \"price\" : \"199\",\n" +
                "    \"description\" : \"The best pillow for amazing programmers.\",\n" +
                "    \"category_id\" : 2,\n" +
                "    \"created\" : \"2018-06-01 00:35:07\"\n" +
                "}";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        Map<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put("name",name);
        jsonParams.put("description",description);
        jsonParams.put("price",price);
        jsonParams.put("category_id",category_id);
        jsonParams.put("catergory_name",catergory_name);

        Log.i(TAG, "JSON conversion = "+ new JSONObject(jsonParams));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                createURL,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String msg = (String)response.get("message");
                            Log.i(TAG,"Message on Create product : "+msg);
                        } catch (JSONException e) {
                            Log.i(TAG, "Message exception "+e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG,"REST ERROR :"+error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

}
