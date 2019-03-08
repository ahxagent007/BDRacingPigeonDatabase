package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.content.Intent;
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

    }
}
