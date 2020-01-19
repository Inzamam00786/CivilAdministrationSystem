package com.inzamam.civiladministrationsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edusername, edpassword;
    Button signupbtn;
    String user, password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressBar=(ProgressBar) findViewById(R.id.signupprogressbar);
        progressBar.setVisibility(View.GONE);

    }
    public void SignUpMethod(View view){
        progressBar.setVisibility(View.VISIBLE);
        mAuth=FirebaseAuth.getInstance();
        edusername=(EditText) findViewById(R.id.signupusername);
        edpassword=(EditText) findViewById(R.id.signuppassword);


        user=edusername.getText().toString().trim();
        password=edpassword.getText().toString().trim();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
            Toast.makeText(SignUpActivity.this, "Enter User Name Or Password", Toast.LENGTH_SHORT).show();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            Toast.makeText(SignUpActivity.this, "Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
        }
        mAuth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(
                SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Error While Registration", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
