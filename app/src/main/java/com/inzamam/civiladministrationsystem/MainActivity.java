package com.inzamam.civiladministrationsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santalu.maskedittext.MaskEditText;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button verfiybtn;
    MaskEditText maskEditText;
    String mycnic;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    LocationManager locationManager;
    LocationListener locationListener;
    String latitude, longtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude=String.valueOf(location.getLatitude());
                longtitude=String.valueOf(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        toolbar=(Toolbar) findViewById(R.id.regtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration Activity");

        maskEditText=(MaskEditText) findViewById(R.id.cnicMaskedText);

        verfiybtn=(Button) findViewById(R.id.verfiybutton);
        verfiybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*  Intent intent=new Intent(MainActivity.this, ManualActivity.class);
               startActivity(intent);*/
               /* firebaseDatabase=FirebaseDatabase.getInstance();
                myRef=firebaseDatabase.getReference("cnic");
                myRef.setValue(maskEditText.getRawText());*/

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:

                break;
            default:
                break;
        }
    }
    public void ConfigureButtonMethod(){
        if((ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
        !=PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.INTERNET}, 10);
            }
            return;
        }
        try {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }catch (SecurityException ex){
            Toast.makeText(MainActivity.this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
