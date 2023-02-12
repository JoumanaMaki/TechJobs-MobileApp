package com.example.sweetshopadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Sendotp extends AppCompatActivity {
EditText number;
Button send;
String userid;
String name, phone, email, image,jobid,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp);

        number=findViewById(R.id.numberotp);
        send=findViewById(R.id.sendotp);


        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        phone = intent.getStringExtra("NUMBER");
        email = intent.getStringExtra("EMAIL");
        image = intent.getStringExtra("IMAGE");
        jobid = intent.getStringExtra("ID");
        userid = intent.getStringExtra("USER");
        username = intent.getStringExtra("USERNAME");

        //Toast.makeText(this, jobid, Toast.LENGTH_SHORT).show();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!number.getText().toString().trim().isEmpty()){
                    if((number.getText().toString().length())>0){
PhoneAuthProvider.getInstance().verifyPhoneNumber(
        number.getText().toString(),
        60,
        TimeUnit.SECONDS,
        Sendotp.this,
        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                number.setText(e.getMessage());
                Toast.makeText(Sendotp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Intent verifyintent = new Intent(Sendotp.this, VerifyOtp.class);
                verifyintent.putExtra("PHONE", number.getText().toString());
                verifyintent.putExtra("BACKEND",s);
                verifyintent.putExtra("NAME",name);
                verifyintent.putExtra("PHONEAPP",phone);
                verifyintent.putExtra("EMAIL",email);
                verifyintent.putExtra("IMAGE",image);
                verifyintent.putExtra("ID",jobid);
                verifyintent.putExtra("USER", userid);
                verifyintent.putExtra("USERNAME", username);
                startActivity(verifyintent);
            }
        }
);
                    }else{
                        Toast.makeText(Sendotp.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Sendotp.this, "Please Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}