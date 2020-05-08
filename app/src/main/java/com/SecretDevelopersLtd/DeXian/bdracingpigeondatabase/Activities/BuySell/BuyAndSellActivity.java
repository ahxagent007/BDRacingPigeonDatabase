package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.BuySell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.WebViewActivity;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.SharedPreffClasss;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BuyAndSellActivity extends AppCompatActivity {

    String TAG = "XIAN";

    Button btn_buyPigeon, btn_sellPigeon, btn_buySupplement, btn_profile;
    private AdView mAdView;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sell);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }else{
            Log.i(TAG, "CURRENT USER : "+currentUser.toString());
        }

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btn_buyPigeon = findViewById(R.id.btn_buyPigeon);
        btn_sellPigeon = findViewById(R.id.btn_sellPigeon);
        btn_buySupplement = findViewById(R.id.btn_buySupplement);
        btn_profile = findViewById(R.id.btn_profile);

        btn_buyPigeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BuyPigeonActivity.class));
            }
        });


        btn_sellPigeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SellPigeonActivity.class));
            }
        });


        btn_buySupplement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SupplementsActivity.class));
            }
        });


        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }
}
