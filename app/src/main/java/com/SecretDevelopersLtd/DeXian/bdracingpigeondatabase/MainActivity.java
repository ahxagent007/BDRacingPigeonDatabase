package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    //Button btn_Pigeons, btn_TopPigeons, btn_TopClubs, btn_TopFanciers, btn_PigeonPedigree, btn_Developer, btn_Report, btn_buyPigeon;
    Button btn_racingPigeons, btn_racerClubs, btn_auctionSite, btn_buy_sell, btn_pigeonPedigree, btn_developer;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    String TAG = "XIAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialAll();
        onClickListener();


    }

    private void initialAll(){

        btn_racingPigeons = findViewById(R.id.btn_racingPigeons);
        btn_racerClubs = findViewById(R.id.btn_racerClubs);
        btn_auctionSite = findViewById(R.id.btn_auctionSite);
        btn_buy_sell = findViewById(R.id.btn_buy_sell);
        btn_pigeonPedigree = findViewById(R.id.btn_pigeonPedigree);
        btn_developer = findViewById(R.id.btn_developer);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6049858234928469/1990430488"); //test ads ca-app-pub-3940256099942544/1033173712 // my ca-app-pub-6049858234928469/1990430488
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    private void onClickListener(){

        /*btn_Pigeons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load_popUu_Ads();
                startActivity(new Intent(getApplicationContext(),AllPigeons.class));
            }
        });


        btn_TopPigeons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load_popUu_Ads();
                startActivity(new Intent(getApplicationContext(),TopPigeons.class));
            }
        });
        btn_TopClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load_popUu_Ads();
                startActivity(new Intent(getApplicationContext(),TopClubs.class));
            }
        });

        btn_TopFanciers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load_popUu_Ads();
                startActivity(new Intent(getApplicationContext(),TopFanciers.class));
            }
        });*/

        btn_pigeonPedigree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load_popUu_Ads();
                //URL to my app
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=pigeonpedigreepro.dexian.secretdevltd.com.pigeonpedigreepro"));
                startActivity(browserIntent);
            }
        });

        btn_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load_popUu_Ads();
                startActivity(new Intent(getApplicationContext(),Developer.class));
            }
        });

        /*btn_buyPigeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Load_popUu_Ads();

                Uri uri = Uri.parse("https://www.bdpigeonweb.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/

        btn_racingPigeons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_racerClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_auctionSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_buy_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i(TAG, "Code to be executed when an ad finishes loading.");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i(TAG, "Code to be executed when an ad request fails.");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i(TAG, "Code to be executed when the ad is displayed.");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.i(TAG, "Code to be executed when the user clicks on an ad.");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i(TAG, "Code to be executed when the user has left the app.");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.i(TAG, "Code to be executed when the interstitial ad is closed.");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });



    }

    private void Load_popUu_Ads(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.i(TAG, "The interstitial wasn't loaded yet.");

        }

    }
}
