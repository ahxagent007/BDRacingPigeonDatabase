package com.SecretDevelopersLtd.DeXian.bdracingpigeondatabase;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreffClasss {

    Context context;

    public SharedPreffClasss(Context context) {
        this.context = context;
    }



    public void storeUID(String UID){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("APP", context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("UID",UID);
        mEditor.apply();
    }

    public String getUID(){
        String UID;
        SharedPreferences mSharedPreferences = context.getSharedPreferences("APP", context.MODE_PRIVATE);
        UID =  mSharedPreferences.getString("UID","Null");
        return UID;
    }

    public void storeUname(String Uname){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("APP", context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("UNAME",Uname);
        mEditor.apply();
    }

    public String getUname(){
        String UID;
        SharedPreferences mSharedPreferences = context.getSharedPreferences("APP", context.MODE_PRIVATE);
        UID =  mSharedPreferences.getString("UNAME","No Name");
        return UID;
    }

}
