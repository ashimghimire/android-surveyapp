package com.example.survey.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class SharedPrefs {
    private Context context;

public SharedPrefs(Context context){
    this.context=context;
}

public SharedPreferences getSharedPref(){
    SharedPreferences sharedPref =
            context.getSharedPreferences("User",
                    Context.MODE_PRIVATE);
    return sharedPref;
}

public void putUser (String user){
    SharedPreferences.Editor editor = getSharedPref().edit();
    editor.putString("userObject", user);
    editor.apply();
}

public void deleteUser (){
    SharedPreferences.Editor editor = getSharedPref().edit();
    editor.remove("userObject").apply();
}

public String getUser(){

    String user=getSharedPref().getString("userObject", null);
    return user;

}

public boolean isLoggedIn(){
    if(getUser()==null){
        return false;
    }else
    {
        return true;
    }
}
}
