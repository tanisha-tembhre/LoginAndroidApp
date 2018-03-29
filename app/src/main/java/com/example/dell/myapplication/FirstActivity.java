package com.example.dell.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstActivity extends AppCompatActivity {

    private Button login;
    private Button newreg;
    private Button forgot;
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        newreg = findViewById(R.id.btreg);
        login = findViewById(R.id.btnlogin);
        email = findViewById(R.id.etname);
        password = findViewById(R.id.etpassword);
        forgot = findViewById(R.id.btforgot);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            startActivity(new Intent(FirstActivity.this, SecondActivity.class));
        }

        newreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this,SignUpActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent= new Intent(FirstActivity.this, SecondActivity.class);
                //startActivity(intent);
                validate(email.getText().toString(), password.getText().toString());
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this,ForgotPassword.class));
            }
        });
    }

    private void validate(String userName, String userPassword){
    /*    if((userName.equals("tanisha")) && (userPassword.equals("123"))){
            Intent intent1= new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent1);
        }else{
            Toast.makeText(FirstActivity.this, "Failing", Toast.LENGTH_SHORT).show();
        }
     */
        progressDialog.setMessage("Waiting for login to complete");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    //Toast.makeText(FirstActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(FirstActivity.this, SecondActivity.class));
                    checkEmailVerification();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(FirstActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag){
            startActivity(new Intent(FirstActivity.this, SecondActivity.class));
        }else{
            Toast.makeText(this, "Email not verified", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
