package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.BuySell;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.PigeonPostDB;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

public class SinglePigeonActivity extends AppCompatActivity {

    ImageView IV_image1, IV_image2, IV_image3;
    TextView TV_ringNo, TV_father, TV_mother, TV_details, TV_price, TV_name, TV_phone, TV_location;
    Button btn_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pigeon);

        final PigeonPostDB pg = (PigeonPostDB) getIntent().getSerializableExtra("PIGEON");

        IV_image1 = findViewById(R.id.IV_image1);
        IV_image2 = findViewById(R.id.IV_image2);
        IV_image3 = findViewById(R.id.IV_image3);

        TV_ringNo = findViewById(R.id.TV_ringNo);
        TV_father = findViewById(R.id.TV_father);
        TV_mother = findViewById(R.id.TV_mother);
        TV_details = findViewById(R.id.TV_details);
        TV_price = findViewById(R.id.TV_price);
        TV_name = findViewById(R.id.TV_name);
        TV_phone = findViewById(R.id.TV_phone);
        TV_location = findViewById(R.id.TV_location);

        btn_call = findViewById(R.id.btn_call);

        if(pg.getImage1() != null){
            Glide.with(getApplicationContext()).load(pg.getImage1()).transform(new CenterInside(), new RoundedCorners(10)).dontAnimate().into(IV_image1);
        }
        if(pg.getImage2() != null){
            Glide.with(getApplicationContext()).load(pg.getImage2()).transform(new CenterInside(), new RoundedCorners(10)).dontAnimate().into(IV_image2);
        }else {
            IV_image2.setVisibility(View.GONE);
        }
        if(pg.getImage3() != null){
            Glide.with(getApplicationContext()).load(pg.getImage3()).transform(new CenterInside(), new RoundedCorners(10)).dontAnimate().into(IV_image3);
        }else {
            IV_image3.setVisibility(View.GONE);
        }

        TV_ringNo.setText(pg.getPigeon_ring());
        TV_father.setText("Father: "+pg.getFather());
        TV_mother.setText("Mother: "+pg.getMother());
        TV_details.setText("Details: "+pg.getDetails());
        TV_price.setText("Price: "+pg.getPrice());
        TV_name.setText("Name: "+pg.getName());
        TV_phone.setText("Phone: "+pg.getPhone());
        TV_location.setText("Location: "+pg.getLocation());

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(pg.getPhone());
            }
        });

    }

    private void makeCall(final String number) {

        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(SinglePigeonActivity.this);
        // Add the buttons
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if(number == null){
                    Toast.makeText(getApplicationContext(),"No Number found!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // User clicked OK button

                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + number));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                /*
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);

                }else{

                }*/
                //startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        // Set other dialog properties
        builder.setMessage("Do you want to make a call "+number+"?");
        builder.setTitle("Buy Pigeons");

        // Create the AlertDialog
        dialog = builder.create();
        dialog.show();


    }

    String[] appPermissions = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    final int PERMISSION_REQUES_CODE = 101;

    public boolean checkAndRequestPermissions(){

        List<String> listPermissinsNeeded = new ArrayList<>();

        for(String perm : appPermissions){

            if (Build.VERSION.SDK_INT >= 23) {
                if(getApplicationContext().checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED){
                    listPermissinsNeeded.add(perm);
                }
            }else{
                if(PermissionChecker.checkSelfPermission(getApplicationContext(), perm) != PermissionChecker.PERMISSION_GRANTED){
                    listPermissinsNeeded.add(perm);
                }
            }


        }

        if(!listPermissinsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(SinglePigeonActivity.this,
                    listPermissinsNeeded.toArray(new String[listPermissinsNeeded.size()]),
                    PERMISSION_REQUES_CODE);
            return false;
        }
        return true;
    }
}
