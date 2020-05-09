package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.BuySell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.WebViewActivity;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.ClubFDB;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.SupplementDB;
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
import java.util.List;

public class SupplementsActivity extends AppCompatActivity {


    String TAG = "XIAN";

    ProgressBar PB_loadingSup;
    RecyclerView RV_supplements;

    DatabaseReference databaseRefSup;

    ArrayList<SupplementDB> supplementDBS = new ArrayList<SupplementDB>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplements);

        RV_supplements = findViewById(R.id.RV_supplements);
        PB_loadingSup = findViewById(R.id.PB_loadingSup);

        databaseRefSup = FirebaseDatabase.getInstance().getReference("SUPPLEMENTS");

        databaseRefSup.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                supplementDBS.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    SupplementDB res = ds.getValue(SupplementDB.class);
                    supplementDBS.add(res);
                }


                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                RV_supplements.setLayoutManager(mLayoutManager);

                RecyclerView.Adapter mRecycleAdapter = new RecycleViewAdapterForSupplements(getApplicationContext(), supplementDBS);
                RV_supplements.setAdapter(mRecycleAdapter);

                PB_loadingSup.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }



    public class RecycleViewAdapterForSupplements extends RecyclerView.Adapter<RecycleViewAdapterForSupplements.ViewHolder> {


        ArrayList<SupplementDB> supplementDBS;
        Context context;

        public RecycleViewAdapterForSupplements(Context context, ArrayList<SupplementDB> supplementDBS) {
            super();
            this.context = context;
            this.supplementDBS = supplementDBS;

        }

        @Override
        public RecycleViewAdapterForSupplements.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.single_supplements, viewGroup, false);

            RecycleViewAdapterForSupplements.ViewHolder viewHolder = new RecycleViewAdapterForSupplements.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecycleViewAdapterForSupplements.ViewHolder viewHolder, final int i) {

            viewHolder.TV_name.setText(supplementDBS.get(i).getName());
            viewHolder.TV_details.setText(supplementDBS.get(i).getDetails());
            Glide.with(getApplicationContext()).load(supplementDBS.get(i).getPic()).transform(new CenterInside(),new RoundedCorners(15)).dontAnimate().into(viewHolder.IV_supPic);

            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {

                    } else {
                        if(checkAndRequestPermissions()){
                            makeCall(supplementDBS.get(position).getCall(), supplementDBS.get(position).getLink());
                        }

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return supplementDBS.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            TextView TV_name, TV_details;
            ImageView IV_supPic;

            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);

                TV_name = itemView.findViewById(R.id.TV_name);
                TV_details = itemView.findViewById(R.id.TV_details);
                IV_supPic = itemView.findViewById(R.id.IV_supPic);

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

    private void makeCall(final String number, final String Website) {

        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(SupplementsActivity.this);
        // Add the buttons
        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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
        builder.setNegativeButton("Website", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
                i.putExtra("WEBSITE", Website);
                startActivity(i);

            }
        });
        // Set other dialog properties
        builder.setMessage("Do you want to call or visit website?");
        builder.setTitle("Buy Supplements");

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
            ActivityCompat.requestPermissions(SupplementsActivity.this,
                    listPermissinsNeeded.toArray(new String[listPermissinsNeeded.size()]),
                    PERMISSION_REQUES_CODE);
            return false;
        }
        return true;
    }
}
