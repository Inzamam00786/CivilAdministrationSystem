package com.inzamam.civiladministrationsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    TextView loginregtv;
    FirebaseAuth mAuth;
    EditText edusername, edpassword;
    Button signupbtn;
    String user, password;
    ProgressBar progressBar;
    FirebaseAuth.AuthStateListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        progressBar=(ProgressBar) findViewById(R.id.loginprogressbar);
        progressBar.setVisibility(View.GONE);

        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null){
                    Intent intent=new Intent(SignInActivity.this, AddNewComplaint.class);
                    startActivity(intent);
                }
            }
        };
        loginregtv=(TextView) findViewById(R.id.loginregtv);
        loginregtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.addAuthStateListener(listener);
    }

    public void SignInMethod(View view){
        progressBar.setVisibility(View.VISIBLE);
        mAuth=FirebaseAuth.getInstance();
        edusername=(EditText) findViewById(R.id.loginusername);
        edpassword=(EditText) findViewById(R.id.loginpassword);

        user=edusername.getText().toString().trim();
        password=edpassword.getText().toString().trim();

        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
            Toast.makeText(SignInActivity.this, "Enter User Name Or Password", Toast.LENGTH_SHORT).show();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            Toast.makeText(SignInActivity.this, "Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            Toast.makeText(SignInActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInActivity.this, AddNewComplaint.class));
                        }
                        else{
                            Toast.makeText(SignInActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
