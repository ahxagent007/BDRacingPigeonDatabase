package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;

public class BuyAndSellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sell);

        findViewById(R.id.IV_secretDev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
                i.putExtra("WEBSITE", "https://www.secretdevbd.com/");
                startActivity(i);
            }
        });
    }
}
