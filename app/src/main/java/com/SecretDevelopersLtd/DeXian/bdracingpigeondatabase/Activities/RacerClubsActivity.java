package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.ClubFDB;
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

public class RacerClubsActivity extends AppCompatActivity {

    String TAG = "XIAN";

    ProgressBar PB_loadingClub;
    RecyclerView RV_clubz;

    DatabaseReference databaseRefClub;

    ArrayList<ClubFDB> clubs = new ArrayList<ClubFDB>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racer_clubs);

        RV_clubz = findViewById(R.id.RV_clubz);
        PB_loadingClub = findViewById(R.id.PB_loadingClub);

        databaseRefClub = FirebaseDatabase.getInstance().getReference("BD_CLUB");

        databaseRefClub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clubs.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ClubFDB res = ds.getValue(ClubFDB.class);
                    clubs.add(res);
                }


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                RV_clubz.setLayoutManager(mLayoutManager);

                RecyclerView.Adapter mRecycleAdapter = new RecycleViewAdapterForClubs(getApplicationContext(), clubs);
                RV_clubz.setAdapter(mRecycleAdapter);

                PB_loadingClub.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    public class RecycleViewAdapterForClubs extends RecyclerView.Adapter<RecycleViewAdapterForClubs.ViewHolder> {


        ArrayList<ClubFDB> clubFDBS;
        Context context;

        public RecycleViewAdapterForClubs(Context context, ArrayList<ClubFDB> clubFDBS) {
            super();
            this.context = context;
            this.clubFDBS = clubFDBS;

        }

        @Override
        public RecycleViewAdapterForClubs.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_club, viewGroup, false);

            RecycleViewAdapterForClubs.ViewHolder viewHolder = new RecycleViewAdapterForClubs.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecycleViewAdapterForClubs.ViewHolder viewHolder, final int i) {

            viewHolder.TV_clubName.setText(clubFDBS.get(i).getName());
            Glide.with(getApplicationContext()).load(clubFDBS.get(i).getPic()).transform(new CenterInside(),new RoundedCorners(15)).dontAnimate().into(viewHolder.IV_clubPic);

            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {

                    } else {
                        if(!clubFDBS.get(position).getWebsite().equalsIgnoreCase("#")){
                            Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
                            i.putExtra("WEBSITE", clubFDBS.get(position).getWebsite());
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(), "No Website Found", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return clubFDBS.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            TextView TV_clubName;
            ImageView IV_clubPic;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                TV_clubName = itemView.findViewById(R.id.TV_clubName);
                IV_clubPic = itemView.findViewById(R.id.IV_clubPic);

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
