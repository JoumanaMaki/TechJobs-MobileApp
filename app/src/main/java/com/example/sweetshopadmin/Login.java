package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Login extends AppCompatActivity {
    EditText email, password;
   //Button btnlogin;
    TextView signup,message;
    String id,Name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        //btnlogin = findViewById(R.id.btnlogin);
        signup = findViewById(R.id.tvsignup);

        message=findViewById(R.id.tvmessagelogin);
        signup.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, signUp.class);
            startActivity(intent);
        });



    }

    public void Login(View view) {

        if(email.getText().toString().trim().length() == 0 ||
                password.getText().toString().trim().length() == 0 ){
            message.setText("All fields are required");
            return;
        }
        RequestQueue queue= Volley.newRequestQueue(this);
        String emaill = email.getText().toString();
        String url = "https://mobileappcourse.000webhostapp.com/JobTech/GetOneUser.php?Email="+emaill;
       // String url = "http://jobtech.infinityfreeapp.com/getOneUser.php?Email="+emaill;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Record not found")) {
                    message.setText("User does not exist");
                    return;
                } else {
                    String res = response.replace("[", "");
                    String res2 = res.replace("]", "");

                    String[] array = res2.split(",", 10);
                    id = array[0].replaceAll("\"", "");
                    Name = array[1].replaceAll("\"", "");
                    String Email = array[2].replaceAll("\"", "");
                    String Password = array[3].replaceAll("\"", "");
                    String Gender = array[4].replaceAll("\"", "");
                    String Role = array[5].replaceAll("\"", "");

                    if (password.getText().toString().equals(Password)) {
if(Role.equals("1")){
                        Intent intentlogin = new Intent(Login.this, Dashboard.class);
                        intentlogin.putExtra("NAME", Name);
                        intentlogin.putExtra("ID", id);
                        startActivity(intentlogin);
    message.setText("");
    }
else{
    Intent intentlogin = new Intent(Login.this, UserDashboard.class);
    intentlogin.putExtra("USERNAME", Name);
    intentlogin.putExtra("USER", id);
    startActivity(intentlogin);
    message.setText("");
}

                    } else {

                        message.setText("LogIn Failed");
                        return;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message.setText(error.toString());

            }
        });
        queue.add(request);

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}