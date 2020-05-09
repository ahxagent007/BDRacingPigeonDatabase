package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.BuySell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.PigeonPostDB;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.SupplementDB;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.ItemClickListener;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuyPigeonActivity extends AppCompatActivity {

    String TAG = "XIAN";

    ProgressBar PB_loadingBuy;
    RecyclerView RV_buy;

    DatabaseReference databaseRef;

    ArrayList<PigeonPostDB> pigeonPostDBS = new ArrayList<PigeonPostDB>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_pigeon);
        RV_buy = findViewById(R.id.RV_buy);
        PB_loadingBuy = findViewById(R.id.PB_loadingBuy);

        databaseRef = FirebaseDatabase.getInstance().getReference("PIGEON_POST");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pigeonPostDBS.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    PigeonPostDB res = ds.getValue(PigeonPostDB.class);
                    pigeonPostDBS.add(res);
                }

                //sort via boosted or not here


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                RV_buy.setLayoutManager(mLayoutManager);

                RecyclerView.Adapter mRecycleAdapter = new RecycleViewAdapterForPigeonPost(getApplicationContext(), pigeonPostDBS);
                RV_buy.setAdapter(mRecycleAdapter);

                PB_loadingBuy.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public class RecycleViewAdapterForPigeonPost extends RecyclerView.Adapter<RecycleViewAdapterForPigeonPost.ViewHolder> {


        ArrayList<PigeonPostDB> pigeonPostDBS;
        Context context;

        public RecycleViewAdapterForPigeonPost(Context context, ArrayList<PigeonPostDB> pigeonPostDBS) {
            super();
            this.context = context;
            this.pigeonPostDBS = pigeonPostDBS;

        }

        @Override
        public RecycleViewAdapterForPigeonPost.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_pigeon, viewGroup, false);

            RecycleViewAdapterForPigeonPost.ViewHolder viewHolder = new RecycleViewAdapterForPigeonPost.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecycleViewAdapterForPigeonPost.ViewHolder viewHolder, final int i) {

            viewHolder.TV_ring.setText(pigeonPostDBS.get(i).getPigeon_ring());
            viewHolder.TV_details.setText(pigeonPostDBS.get(i).getDetails());
            viewHolder.TV_location.setText("Location: "+pigeonPostDBS.get(i).getLocation());
            viewHolder.TV_price.setText("Price: "+pigeonPostDBS.get(i).getPrice());

            if (!pigeonPostDBS.get(i).getBoosted().equalsIgnoreCase("YES")) {
                viewHolder.TV_Boosted.setVisibility(View.GONE);
            }

            viewHolder.LL_SellPigeonLL.setVisibility(View.GONE);

            Glide.with(getApplicationContext()).load(pigeonPostDBS.get(i).getImage1()).transform(new CenterInside(), new RoundedCorners(15)).dontAnimate().into(viewHolder.IV_pigeonPic);


            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {

                    } else {
                        Intent i = new Intent(getApplicationContext(), SinglePigeonActivity.class);
                        i.putExtra("PIGEON", pigeonPostDBS.get(position));
                        startActivity(i);

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return pigeonPostDBS.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            TextView TV_ring, TV_details, TV_location, TV_price, TV_Boosted;
            ImageView IV_pigeonPic;
            LinearLayout LL_SellPigeonLL;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                TV_ring = itemView.findViewById(R.id.TV_ring);
                TV_details = itemView.findViewById(R.id.TV_details);
                TV_location = itemView.findViewById(R.id.TV_location);
                TV_price = itemView.findViewById(R.id.TV_price);
                IV_pigeonPic = itemView.findViewById(R.id.IV_pigeonPic);
                LL_SellPigeonLL = itemView.findViewById(R.id.LL_SellPigeonLL);
                TV_Boosted = itemView.findViewById(R.id.TV_Boosted);

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

}