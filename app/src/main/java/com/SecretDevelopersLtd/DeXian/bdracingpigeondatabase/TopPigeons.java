package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.content.Context;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class TopPigeons extends AppCompatActivity {

    String rIP = "192.168.0.104/BdRacingPigeonDatabase";
    String IP = "bdracingpigeon.bdpigeonweb.com";

    String TAG = "XIAN";

    Button btn_byVelocity, btn_byRace, btn_byResult;
    RecyclerView RV_recyclerViewTP;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Pigeon> PigeonList;
    private List<TopPigeonModel> TopPigeonList;


    private ProgressBar PB_loadingTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_pigeons);


        initial();
        onClickListener();


    }


    private void initial(){

        btn_byVelocity = findViewById(R.id.btn_byVelocity);
        btn_byRace = findViewById(R.id.btn_byRace);
        btn_byResult = findViewById(R.id.btn_byResult);
        RV_recyclerViewTP = findViewById(R.id.RV_recyclerViewTP);


        PB_loadingTP = findViewById(R.id.PB_loadingTP);
        PB_loadingTP.setVisibility(View.INVISIBLE);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        RV_recyclerViewTP.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        RV_recyclerViewTP.setLayoutManager(layoutManager);



    }

    private void onClickListener(){

        btn_byVelocity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RV_recyclerViewTP.setVisibility(View.INVISIBLE);

                TopPigeonsViaVelocity();

                PB_loadingTP.setVisibility(View.VISIBLE);

                btn_byRace.setBackgroundColor(Color.TRANSPARENT);
                btn_byVelocity.setBackgroundColor(Color.GREEN);
                btn_byResult.setBackgroundColor(Color.TRANSPARENT);

            }
        });
        btn_byRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RV_recyclerViewTP.setVisibility(View.INVISIBLE);

                TopPigeonsViaRaceCount();

                PB_loadingTP.setVisibility(View.VISIBLE);

                btn_byRace.setBackgroundColor(Color.GREEN);
                btn_byVelocity.setBackgroundColor(Color.TRANSPARENT);
                btn_byResult.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        btn_byResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RV_recyclerViewTP.setVisibility(View.INVISIBLE);

                TopPigeonsViaTopResult();

                PB_loadingTP.setVisibility(View.VISIBLE);

                btn_byRace.setBackgroundColor(Color.TRANSPARENT);
                btn_byVelocity.setBackgroundColor(Color.TRANSPARENT);
                btn_byResult.setBackgroundColor(Color.GREEN);
            }
        });

    }

    private void setAdapter(){
        // specify an adapter (see also next example)
        mAdapter = new MyRVAdapterTP(getApplicationContext(),PigeonList);
        RV_recyclerViewTP.setAdapter(mAdapter);
        RV_recyclerViewTP.setVisibility(View.VISIBLE);

    }

    private void setAdapterTP(){
        // specify an adapter (see also next example)
        mAdapter = new MyRVAdapterTP2(getApplicationContext(),TopPigeonList);
        RV_recyclerViewTP.setAdapter(mAdapter);
        RV_recyclerViewTP.setVisibility(View.VISIBLE);

    }

    private void setAdapterTPR(){
        // specify an adapter (see also next example)
        mAdapter = new MyRVAdapterTP3(getApplicationContext(),TopPigeonList);
        RV_recyclerViewTP.setAdapter(mAdapter);
        RV_recyclerViewTP.setVisibility(View.VISIBLE);

    }

    public class MyRVAdapterTP extends RecyclerView.Adapter<MyRVAdapterTP.ViewHolder> {

        List<Pigeon> pigeonList;
        Context context;

        public MyRVAdapterTP(Context context, List<Pigeon> pList) {
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

            /*viewHolder.TV_position.setText(productList.get(i).getProductName());
            viewHolder.TV_productQuantity.setText(productList.get(i).getProductStockQuantity()+" pc");
            viewHolder.TV_productPrice.setText(productList.get(i).getProductPrice()+" BDT");*/

            viewHolder.TV_position.setText(pigeonList.get(i).getPosition()+"/"+pigeonList.get(i).getTotalPigeons()+" th");
            viewHolder.TV_ringNumber.setText(pigeonList.get(i).getPigeonRingNumber());
            viewHolder.TV_velocity.setText(pigeonList.get(i).getPigeonVelocity()+" YPM");
            viewHolder.TV_owenerName.setText(pigeonList.get(i).getOwnerName());
            viewHolder.TV_club.setText(pigeonList.get(i).getClubName());
            viewHolder.TV_race.setText(pigeonList.get(i).getRaceName() +" ( "+pigeonList.get(i).getDistance()+" KM )");
            viewHolder.TV_date.setText(pigeonList.get(i).getRaceDate());

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

            public TextView TV_position, TV_ringNumber, TV_velocity, TV_owenerName, TV_club, TV_race, TV_date;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                TV_position = itemView.findViewById(R.id.TV_position);
                TV_ringNumber = itemView.findViewById(R.id.TV_ringNumber);
                TV_velocity = itemView.findViewById(R.id.TV_velocity);
                TV_owenerName = itemView.findViewById(R.id.TV_owenerName);
                TV_club = itemView.findViewById(R.id.TV_club);
                TV_race = itemView.findViewById(R.id.TV_race);
                TV_date = itemView.findViewById(R.id.TV_date);

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

    public class MyRVAdapterTP2 extends RecyclerView.Adapter<MyRVAdapterTP2.ViewHolder> {

        List<TopPigeonModel> plist;
        Context context;

        public MyRVAdapterTP2(Context context, List<TopPigeonModel> pList) {
            super();
            this.context = context;
            this.plist = pList;
            Log.i(TAG,"RECYCLE VIEW COnstructor");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_top_pigeon_for_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

            Log.i(TAG,i+" RECYCLE VIEW "+(plist.get(i).getPigeonRingNumber()));

            /*viewHolder.TV_position.setText(productList.get(i).getProductName());
            viewHolder.TV_productQuantity.setText(productList.get(i).getProductStockQuantity()+" pc");
            viewHolder.TV_productPrice.setText(productList.get(i).getProductPrice()+" BDT");*/

            viewHolder.TV_ringNumber.setText(plist.get(i).getPigeonRingNumber());
            viewHolder.TV_owenerName.setText(plist.get(i).getOwnerName());
            viewHolder.TV_position.setText(plist.get(i).getPositionLessThenFifty()+" Top Positions in "+plist.get(i).getClubName());


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
            return plist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public TextView TV_ringNumber, TV_owenerName, TV_position;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                TV_ringNumber = itemView.findViewById(R.id.TV_ringNumber);
                TV_owenerName = itemView.findViewById(R.id.TV_owenerName);
                TV_position = itemView.findViewById(R.id.TV_position);


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

    public class MyRVAdapterTP3 extends RecyclerView.Adapter<MyRVAdapterTP3.ViewHolder> {

        List<TopPigeonModel> plist;
        Context context;

        public MyRVAdapterTP3(Context context, List<TopPigeonModel> pList) {
            super();
            this.context = context;
            this.plist = pList;
            Log.i(TAG,"RECYCLE VIEW COnstructor");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_top_pigeon_for_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

            Log.i(TAG,i+" RECYCLE VIEW "+(plist.get(i).getPigeonRingNumber()));

            /*viewHolder.TV_position.setText(productList.get(i).getProductName());
            viewHolder.TV_productQuantity.setText(productList.get(i).getProductStockQuantity()+" pc");
            viewHolder.TV_productPrice.setText(productList.get(i).getProductPrice()+" BDT");*/

            viewHolder.TV_ringNumber.setText(plist.get(i).getPigeonRingNumber());
            viewHolder.TV_owenerName.setText(plist.get(i).getOwnerName());
            viewHolder.TV_position.setText("Participated in "+plist.get(i).getPositionLessThenFifty()+" Races in "+plist.get(i).getClubName());


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
            return plist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public TextView TV_ringNumber, TV_owenerName, TV_position;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                TV_ringNumber = itemView.findViewById(R.id.TV_ringNumber);
                TV_owenerName = itemView.findViewById(R.id.TV_owenerName);
                TV_position = itemView.findViewById(R.id.TV_position);


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


    private void TopPigeonsViaVelocity(){

        String readURL = "http://"+IP+"/Pigeons/TopPigeonsViaVelocity.php";

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

                        PB_loadingTP.setVisibility(View.INVISIBLE);

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

    private void TopPigeonsViaRaceCount(){

        String readURL = "http://"+IP+"/Pigeons/TopPigeonsViaRaceCount.php";

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
                        TopPigeonList pList = gson.fromJson(object, TopPigeonList.class);

                        Log.i(TAG,pList.getRecords().toString());

                        List<TopPigeonModel> pp = pList.getRecords();

                        TopPigeonList = pp;
                        setAdapterTPR();

                        PB_loadingTP.setVisibility(View.INVISIBLE);

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

    private void TopPigeonsViaTopResult(){

        String readURL = "http://"+IP+"/Pigeons/TopPigeonsViaTopResult.php";

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
                        TopPigeonList pList = gson.fromJson(object, TopPigeonList.class);

                        Log.i(TAG,pList.getRecords().toString());

                        List<TopPigeonModel> pp = pList.getRecords();

                        TopPigeonList = pp;
                        setAdapterTP();

                        PB_loadingTP.setVisibility(View.INVISIBLE);

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
