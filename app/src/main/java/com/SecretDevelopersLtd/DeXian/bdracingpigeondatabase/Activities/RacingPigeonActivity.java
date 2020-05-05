package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;

public class RacingPigeonActivity extends AppCompatActivity {

    Button btn_SearchPigeon, btn_TopPigeon, btn_ToFancier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racing_pigeon);

        btn_SearchPigeon = findViewById(R.id.btn_SearchPigeon);
        btn_TopPigeon = findViewById(R.id.btn_TopPigeon);
        btn_ToFancier = findViewById(R.id.btn_ToFancier);


        btn_SearchPigeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AllPigeons.class));
            }
        });
        btn_TopPigeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TopPigeons.class));
            }
        });
        btn_ToFancier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TopFanciers.class));
            }
        });
    }
}
