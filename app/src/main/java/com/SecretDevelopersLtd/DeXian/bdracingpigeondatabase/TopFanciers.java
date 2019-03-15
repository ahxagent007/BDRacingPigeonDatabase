package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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

import org.json.JSONObject;

import java.util.List;

public class TopFanciers extends AppCompatActivity {


    String rIP = "192.168.0.104/BdRacingPigeonDatabase";
    String IP = "bdracingpigeon.bdpigeonweb.com";

    String TAG = "XIAN";

    Button btn_TopFancierViaPoint;
    RecyclerView RV_recyclerViewTF;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private List<TopFancierModel> TopFancierList;


    private ProgressBar PB_loadingTF;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_fanciers);


        initial();
        onClickListener();
    }


    private void initial(){

        btn_TopFancierViaPoint = findViewById(R.id.btn_TopFancierViaPoint);
        RV_recyclerViewTF = findViewById(R.id.RV_recyclerViewTF);


        PB_loadingTF = findViewById(R.id.PB_loadingTF);
        PB_loadingTF.setVisibility(View.INVISIBLE);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        RV_recyclerViewTF.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        RV_recyclerViewTF.setLayoutManager(layoutManager);



    }

    private void onClickListener(){

        btn_TopFancierViaPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RV_recyclerViewTF.setVisibility(View.INVISIBLE);

                TopFancierViaPoint();

                PB_loadingTF.setVisibility(View.VISIBLE);

                //btn_byRace.setBackgroundColor(Color.TRANSPARENT);
                //btn_byVelocity.setBackgroundColor(Color.TRANSPARENT);
                btn_TopFancierViaPoint.setBackgroundColor(Color.GREEN);


            }
        });
    }
    private void setAdapter(){
        // specify an adapter (see also next example)
        mAdapter = new MyRVAdapterTF(getApplicationContext(),TopFancierList);
        RV_recyclerViewTF.setAdapter(mAdapter);
        RV_recyclerViewTF.setVisibility(View.VISIBLE);

    }

    public class MyRVAdapterTF extends RecyclerView.Adapter<MyRVAdapterTF.ViewHolder> {

        List<TopFancierModel> fancierList;
        Context context;

        public MyRVAdapterTF(Context context, List<TopFancierModel> fList) {
            super();
            this.context = context;
            this.fancierList = fList;
            Log.i(TAG,"RECYCLE VIEW COnstructor");
        }

        @Override
        public MyRVAdapterTF.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_top_fancier_for_list, viewGroup, false);
            MyRVAdapterTF.ViewHolder viewHolder = new MyRVAdapterTF.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyRVAdapterTF.ViewHolder viewHolder, final int i) {

            Log.i(TAG,i+" RECYCLE VIEW "+(fancierList.get(i).getOwnerName()));

            /*viewHolder.TV_position.setText(productList.get(i).getProductName());
            viewHolder.TV_productQuantity.setText(productList.get(i).getProductStockQuantity()+" pc");
            viewHolder.TV_productPrice.setText(productList.get(i).getProductPrice()+" BDT");*/

            viewHolder.TV_ownerNameTF.setText(fancierList.get(i).getOwnerName());
            viewHolder.TV_positionTF.setText(fancierList.get(i).getPositionLessThenThirty()+" Top Position in "+fancierList.get(i).getRacePlayed()+" Races!");
            viewHolder.TV_pointTF.setText("Point: "+String.format("%.02f",(float)fancierList.get(i).getPositionLessThenThirty()/(float)fancierList.get(i).getRacePlayed()));

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
            return fancierList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public TextView TV_ownerNameTF,  TV_positionTF, TV_pointTF;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                TV_ownerNameTF = itemView.findViewById(R.id.TV_ownerNameTF);
                TV_positionTF = itemView.findViewById(R.id.TV_positionTF);
                TV_pointTF = itemView.findViewById(R.id.TV_pointTF);


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

    private void TopFancierViaPoint(){

        String readURL = "http://"+IP+"/Fanciers/TopFanciersViaPoint.php";

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
                        TopFancierList fList = gson.fromJson(object, TopFancierList.class);

                        Log.i(TAG,fList.getRecords().toString());

                        List<TopFancierModel> pp = fList.getRecords();

                        TopFancierList = pp;
                        setAdapter();

                        PB_loadingTF.setVisibility(View.INVISIBLE);

                        Log.i(TAG,"pp size : "+pp.size());
                        Log.i(TAG,"pp 0 name = "+pp.get(0).getOwnerName());

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
