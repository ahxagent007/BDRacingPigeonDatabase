package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.BuySell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.WebViewActivity;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.SharedPreffClasss;

public class BuyAndSellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sell);


        if(new SharedPreffClasss(getApplicationContext()).getUID().equalsIgnoreCase("Null")){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }


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
