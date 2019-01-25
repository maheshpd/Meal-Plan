package com.arfeenkhan.mealplan.Utils;

import android.app.Application;
import android.content.Intent;

import com.arfeenkhan.mealplan.Login;
import com.arfeenkhan.mealplan.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseAuth != null){
            startActivity(new Intent(Home.this,MainActivity.class));
        }else {
            startActivity(new Intent(Home.this, Login.class));
        }
    }
}
