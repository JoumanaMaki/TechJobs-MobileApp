package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class ApplicantDetail extends AppCompatActivity {
ImageView img;
TextView Name, Contact;
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_detail);

        Intent applicant = getIntent();
        id = applicant.getStringExtra("POSITION");
        Contact=findViewById(R.id.ApplicantDetail);
        img=findViewById(R.id.ApplicantImage);
        Name = findViewById(R.id.NameApplicant);

        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/getOneApplicant.php?ID="+id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();

                String name = String.valueOf(response.charAt(1));
                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 12);
                id = array[0].replaceAll("\"", "");
                String name1 = array[1].replaceAll("\"", "");
                String phone = array[2].replaceAll("\"", "");
                String email = array[3].replaceAll("\"", "");
                String image= array[4].replaceAll("\"", "");
                String jobid = array[5].replaceAll("\"", "");

                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Applicants/"+image;

                Picasso.get().load(imageurl).into(img);
Name.setText(name1);
Contact.setText("\nPHONE NUMBER: "+ phone +"\n\nEMAIL: "+email);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApplicantDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

    }

    public void ApplicantBack(View view) {finish();
    }
}