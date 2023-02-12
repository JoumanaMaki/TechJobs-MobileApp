package com.example.sweetshopadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {
EditText otpverify;
Button verify;
TextView phone;
String userid,username;
    String nameapp, phoneapp, emailapp, imageapp,idapp;

String phonenumb;
String getbackend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);



        otpverify=findViewById(R.id.notp);
        verify = findViewById(R.id.verifyotp);
        phone = findViewById(R.id.phonenumber);

        Intent i1 = getIntent();
        phonenumb = i1.getStringExtra("PHONE");
        getbackend=i1.getStringExtra("BACKEND");
        nameapp = i1.getStringExtra("NAME");
        phoneapp = i1.getStringExtra("PHONEAPP");
        emailapp = i1.getStringExtra("EMAIL");
        imageapp = i1.getStringExtra("IMAGE");
        idapp = i1.getStringExtra("ID");
        userid = i1.getStringExtra("USER");
        username = i1.getStringExtra("USERNAME");
        //Toast.makeText(this, idapp, Toast.LENGTH_SHORT).show();

        phone.setText(phonenumb);

        verify.setOnClickListener((view) -> {
            if(!otpverify.getText().toString().trim().isEmpty()){

                if(getbackend != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getbackend, otpverify.getText().toString());
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String url = "https://mobileappcourse.000webhostapp.com/JobTech/uploadApplicant.php";

                                //String url = "http://jobtech.infinityfreeapp.com/addBlogs.php";

                                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(VerifyOtp.this, response, Toast.LENGTH_SHORT).show();
                                      //  phone.setText(response);
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(VerifyOtp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                             //   phone.setText(error.toString());
                                            }

                                        }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("image", imageapp);
                                        params.put("Name", nameapp);
                                        params.put("Phone",phoneapp);
                                        params.put("Email",emailapp);
                                        params.put("ID",idapp);
                                       // params.put("JobID",JobCategory);
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue  = Volley.newRequestQueue(VerifyOtp.this);
                                requestQueue.add(request);
                                request.setRetryPolicy(new DefaultRetryPolicy(
                                        30000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                 Intent newintent  = new Intent(getApplicationContext(), UserDashboard.class);
                                newintent.putExtra("USER", userid);
                                newintent.putExtra("USERNAME", username);
                               // Toast.makeText(VerifyOtp.this, username, Toast.LENGTH_SHORT).show();
                                 //newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(newintent);
                            }else{
                                Toast.makeText(VerifyOtp.this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

              //  Toast.makeText(this, "OTP VERFIY", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Please Enter the OTP sent ro you", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void resend(View view) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumb,
                60,
                TimeUnit.SECONDS,
                VerifyOtp.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(VerifyOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        getbackend=s;
                    }
                }
        );

    }
}