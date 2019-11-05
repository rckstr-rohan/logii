package com.example.lo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);


    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email",Toast.LENGTH_SHORT).show();

            //stopping the function exwcution further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //pass is empty
            Toast.makeText(this,"Please ener password",Toast.LENGTH_SHORT).show();
            return;
        }

        //if validations are ok
        // we will first show a progressbar

        progressDialog.setMessage("Registering User....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // user is successfully registered and logged in
                                // we will start the profile activity here
                                // right now lets display a toast only
                                Toast.makeText(MainActivity.this, "Registered Successfully",Toast.LENGTH_SHORT).show();

                            }

                            else{
                                Toast.makeText(MainActivity.this, "Could not register..please try again",Toast.LENGTH_SHORT).show();
                            }
            }
        });

    }

    @Override
    public void onClick(View view){
        if(view == buttonRegister){
            registerUser();
        }

        if(view == textViewSignin){
            // will open login activity
        }
    }
}
