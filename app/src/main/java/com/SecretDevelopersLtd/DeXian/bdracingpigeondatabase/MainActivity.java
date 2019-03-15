package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_Pigeons, btn_TopPigeons, btn_TopClubs, btn_TopFanciers, btn_PigeonPedigree, btn_Developer, btn_Report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialAll();
        onClickListener();


    }

    private void initialAll(){

        btn_Pigeons = findViewById(R.id.btn_Pigeons);
        btn_TopPigeons = findViewById(R.id.btn_TopPigeons);
        btn_TopClubs = findViewById(R.id.btn_TopClubs);
        btn_TopFanciers = findViewById(R.id.btn_TopFanciers);
        btn_PigeonPedigree = findViewById(R.id.btn_PigeonPedigree);
        btn_Developer = findViewById(R.id.btn_Developer);
        btn_Report = findViewById(R.id.btn_Report);

    }

    private void onClickListener(){

        btn_Pigeons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AllPigeons.class));
            }
        });


        btn_TopPigeons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TopPigeons.class));
            }
        });
        btn_TopClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TopClubs.class));
            }
        });

        btn_TopFanciers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TopFanciers.class));
            }
        });

        btn_PigeonPedigree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //URL to my app
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=pigeonpedigreepro.dexian.secretdevltd.com.pigeonpedigreepro"));
                startActivity(browserIntent);
            }
        });

        btn_Developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Developer.class));
            }
        });

        btn_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReportToUs.class));
            }
        });





    }
}
