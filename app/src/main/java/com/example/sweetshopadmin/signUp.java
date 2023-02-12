package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity {
EditText username, email, password, repassword;
RadioButton male,female;
TextView message;
String gender;
TextView login;
    String pass;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_sign_up);


        male= findViewById(R.id.rbMale);
        female= findViewById(R.id.rbFemale);
        username    = findViewById(R.id.etUsername);
        email  = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        repassword = findViewById(R.id.etConfirmPassword);
        message = findViewById(R.id.tvmessage);

        login = findViewById(R.id.tvlogin);


        login.setOnClickListener(view -> {
            Intent intent = new Intent(signUp.this, Login.class);
            startActivity(intent);
        });

    }

    public void submit(View view) {
        if(username.getText().toString().trim().length() == 0 ||
                email.getText().toString().trim().length() == 0 ||
                password.getText().toString().trim().length() == 0 ||
        repassword.getText().toString().trim().length() == 0){
        message.setText("All fields are required");
            return;
        }



        if(male.isChecked()){
            //Toast.makeText(this, "Male is selected", Toast.LENGTH_SHORT).show();
            gender = "Male";
        }
        if(female.isChecked()){
            //Toast.makeText(this, "Female is selected", Toast.LENGTH_SHORT).show();
            gender = "Female";
        }
        RequestQueue queue= Volley.newRequestQueue(this);

      String url = "https://mobileappcourse.000webhostapp.com/JobTech/addUsers.php";
       // String url = "http://jobtech.infinityfreeapp.com/addUsers.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                message.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.setText(error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("username", String.valueOf(username.getText()));
                params.put("email", String.valueOf(email.getText()));
                params.put("password", password.getText().toString());
                params.put("repassword", repassword.getText().toString());
                params.put("gender", gender);
                //to validate who is inserting new info into database
                params.put("key", "joumana");

                return params;
            }
        };
        queue.add(request);

    }


    }
