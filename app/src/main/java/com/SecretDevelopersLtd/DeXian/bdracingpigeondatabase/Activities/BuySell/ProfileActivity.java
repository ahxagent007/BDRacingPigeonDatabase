package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.BuySell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.UserDB;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.SharedPreffClasss;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {

    String TAG = "XIAN";

    ProgressBar PB_loadingPro;
    TextView TV_name, TV_phone, TV_location, TV_point, TV_post;
    Button btn_earnPoint, btn_logout;

    DatabaseReference mDatabaseRef;
    UserDB userDB;

    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        PB_loadingPro = findViewById(R.id.PB_loadingPro);
        TV_name = findViewById(R.id.TV_name);
        TV_phone = findViewById(R.id.TV_phone);
        TV_location = findViewById(R.id.TV_location);
        TV_point = findViewById(R.id.TV_point);
        TV_post = findViewById(R.id.TV_post);
        btn_earnPoint = findViewById(R.id.btn_earnPoint);
        btn_logout = findViewById(R.id.btn_logout);

        userDB = new UserDB();

        PB_loadingPro.setVisibility(View.VISIBLE);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Registration");

        Query query = mDatabaseRef.orderByKey().equalTo(new SharedPreffClasss(getApplicationContext()).getUID());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    userDB = ds.getValue(UserDB.class);
                    //Log.i(TAG, ds.toString());
                }

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        PB_loadingPro.setVisibility(View.INVISIBLE);

                        if(userDB != null){
                            TV_name.setText("Name: "+userDB.getName());
                            TV_phone.setText("Phone No. : "+userDB.getPhone());
                            TV_location.setText("Location: "+userDB.getLocation());
                            TV_point.setText("Your Point: "+userDB.getPoint());
                            TV_post.setText("Total Sell Post: "+userDB.getPost());
                        }else {
                            Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                PB_loadingPro.setVisibility(View.INVISIBLE);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Internet Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), BuyAndSellActivity.class));
                finish();
            }
        });

        btn_earnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRewardAds();
            }
        });

        //ADS
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        LoadAds();

    }


    public void loadRewardAds(){
        if (rewardedAd.isLoaded()) {
            Activity activityContext = ProfileActivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                    Log.i(TAG, "Ads showing");
                }

                @Override
                public void onRewardedAdClosed() {
                    // Ad closed.
                    Log.i(TAG, "Ads Closed");
                    LoadAds();
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                    Log.i(TAG, "User Get reward == "+reward.getAmount());
                    UpdatePoint(userDB.getId(), userDB.getPoint()+reward.getAmount());
                    Toast.makeText(getApplicationContext(), "Congratulation you have receive "+reward.getAmount()+" points", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display.
                    Log.i(TAG, "Ads onRewardedAdFailedToShow "+errorCode);
                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            Log.d(TAG, "The rewarded ad wasn't loaded yet.");
            Toast.makeText(getApplicationContext(),"Try Again.", Toast.LENGTH_LONG).show();
        }
    }

    private void LoadAds(){
        rewardedAd = new RewardedAd(this,
                "ca-app-pub-6049858234928469/1690584219");

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.i(TAG, "Ad successfully loaded.");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                Log.i(TAG, "Ad failed to load.");
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    private void UpdatePoint(String Uid, int point){
        mDatabaseRef.child(Uid).child("point").setValue(point);
    }

}
