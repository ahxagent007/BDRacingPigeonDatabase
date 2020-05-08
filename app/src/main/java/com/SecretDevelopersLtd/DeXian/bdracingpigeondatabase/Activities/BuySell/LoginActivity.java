package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.Activities.BuySell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.DB.UserDB;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.R;
import com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase.SharedPreffClasss;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    String TAG = "XIAN";

    Button btn_login, btn_register, btn_forget;
    EditText ET_email, ET_pass;

    FirebaseAuth mAuth;
    DatabaseReference mDatabaseRefArea;
    ProgressDialog dialog;
    ArrayAdapter<String> spinnerArrayAdapter;

    ArrayList LocationList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_forget = findViewById(R.id.btn_forget);
        ET_email = findViewById(R.id.ET_email);
        ET_pass = findViewById(R.id.ET_pass);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mDatabaseRefArea = FirebaseDatabase.getInstance().getReference("LOCATION");

        mDatabaseRefArea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocationList.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    LocationList.add(ds.getValue());
                    Log.i(TAG, "LOCATION : "+ds.getValue());
                }

                spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, LocationList);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ET_email.getText().toString();
                String pass = ET_pass.getText().toString();

                if(email.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Email!", Toast.LENGTH_SHORT).show();
                }else if(pass.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Password!", Toast.LENGTH_SHORT).show();
                }else{
                    signIn(email,pass);
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpForm();
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ET_email.getText().toString();
                if(!email.equalsIgnoreCase("")){
                    forgetPassword(email);
                }else {
                    Toast.makeText(getApplicationContext(), "Please Enter Email!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void forgetPassword(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Forget pass Email sent.");
                            Toast.makeText(getApplicationContext(),"Success, Please Check your email !!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Enter valid Email", Toast.LENGTH_LONG).show();
                        }
                        //dialog.dismiss();
                    }
                });
    }

    private void SignUpForm(){

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(LoginActivity.this);
        View myView = getLayoutInflater().inflate(R.layout.register_dialog, null);

        final EditText ET_fullName, ET_regEmail, ET_PhoneNumber, ET_pWord1, ET_pWord2;
        Button btn_signUpDone;
        final Spinner SP_Location;

        ET_fullName = myView.findViewById(R.id.ET_fullName);
        ET_regEmail = myView.findViewById(R.id.ET_regEmail);
        ET_PhoneNumber = myView.findViewById(R.id.ET_PhoneNumber);
        ET_pWord1 = myView.findViewById(R.id.ET_pWord1);
        ET_pWord2 = myView.findViewById(R.id.ET_pWord2);
        btn_signUpDone = myView.findViewById(R.id.btn_signUpDone);
        SP_Location = myView.findViewById(R.id.SP_Location);

        SP_Location.setAdapter(spinnerArrayAdapter);

        myBuilder.setView(myView);
        final AlertDialog Dialog = myBuilder.create();
        Dialog.show();


        btn_signUpDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ET_regEmail.getText().toString().trim();
                String pass1 = ET_pWord1.getText().toString().trim();
                String pass2 = ET_pWord2.getText().toString().trim();
                String name = ET_fullName.getText().toString().trim();
                String phone = ET_PhoneNumber.getText().toString().trim();
                String location = SP_Location.getSelectedItem().toString();

                if(pass1.equals(pass2)){
                    signUp(email,pass1,name,phone,location,Dialog);

                }else{
                    Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private void signIn(String email, String pass){

        Log.i(TAG,"signIn Method");
        final String E = email;
        final String P = pass;

        if(!email.equals("") && !pass.equals("")){

            dialog = ProgressDialog.show(LoginActivity.this, "Login..",
                    "Login processing. Please wait...", true);

            Log.i(TAG,"Email: "+email+" Password: "+pass);

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.i(TAG,"Login Success");
                        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();

                        //Firebase Auth
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser fu = mAuth.getCurrentUser();
                        String uID = fu.getUid();

                        new SharedPreffClasss(getApplicationContext()).storeUID(uID);
                        getNameFromFirebase(uID);

                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),BuyAndSellActivity.class));
                        finish();


                    }else {
                        Log.i(TAG,"Login failed");
                        Toast.makeText(getApplicationContext(),"Wrong Password or Email",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                }
            });

        }else {
            Toast.makeText(getApplicationContext(),"Please fill up Email and Password!",Toast.LENGTH_LONG).show();
        }

    }

    private void signUp(final String Email, final String password, final String Name, final String Phone, final String location, final AlertDialog Dialog){

        //String email = inputEmail.getText().toString().trim();
        //String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
            if(dialog != null && dialog.isShowing()){dialog.dismiss();}

            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_LONG).show();
            if(dialog != null && dialog.isShowing()){dialog.dismiss();}
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_LONG).show();
            if(dialog != null && dialog.isShowing()){dialog.dismiss();}
            return;
        }
        dialog = ProgressDialog.show(LoginActivity.this, "Sign Up",
                "Registration processing. Please wait...", true);
        //progressBar.setVisibility(View.VISIBLE);
        //create user
        mAuth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());
                        //progressBar.setVisibility(View.GONE);


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                            dialog.cancel();

                        } else {

                            FirebaseUser fu = task.getResult().getUser();
                            String UID = fu.getUid();

                            SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
                            Date date = new Date(System.currentTimeMillis());
                            String curdate = formatter.format(date);

                            UserDB u = new UserDB(UID, Name, Email, password, Phone , 50, 0, location, curdate);

                            pushToFirebase(UID,u);

                            Toast.makeText(getApplicationContext(), "Registration Complete Successfully, Please login now",
                                    Toast.LENGTH_LONG).show();

                            Dialog.cancel();
                            dialog.dismiss();
                            //startActivity(new Intent(Login.this, MainActivity.class));
                            //finish();
                        }
                    }
                });
    }

    private void pushToFirebase(String uid, UserDB u) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Registration");
        myRef.child(uid).setValue(u);

    }

    private void getNameFromFirebase(String uid) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Registration");
        Query query = myRef.orderByKey().equalTo(uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    UserDB uu = ds.getValue(UserDB.class);
                    new SharedPreffClasss(getApplicationContext()).storeUname(uu.getName());

                    //Log.i(TAG,p.getID()+" "+p.getProductName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




}
