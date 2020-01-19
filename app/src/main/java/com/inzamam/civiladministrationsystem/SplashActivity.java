package com.inzamam.civiladministrationsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        Thread mythread=new Thread(){
          public void run(){
              try{
                  sleep(2000);
                  if(user!=null){
                      startActivity(new Intent(SplashActivity.this, VerificationActivity.class));
                      finish();
                  }else{
                      startActivity(new Intent(SplashActivity.this, VerificationActivity.class));
                      finish();
                  }

              }catch (InterruptedException ex){
                  ex.printStackTrace();
              }
          }
        };
        mythread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
