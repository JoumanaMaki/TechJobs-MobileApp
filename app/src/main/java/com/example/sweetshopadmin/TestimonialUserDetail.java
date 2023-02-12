package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class TestimonialUserDetail extends AppCompatActivity {
    TextView testname, text;
    ImageView image;
    String testimonialid;
    String testimonialname, testimonialtext, testimonialpublish, testimonialimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonial_user_detail);
        testname = findViewById(R.id.titleTestimonialuser);
        text = findViewById(R.id.textTestimonialuser);
        image = findViewById(R.id.ImageTestimonialuser);

        Intent testimonialintent = getIntent();
        testimonialid = testimonialintent.getStringExtra("POSITION");

        int id = Integer.parseInt(testimonialid);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/GetOneTestimonial.php?ID=" + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();


                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 5);
                testimonialid = array[0].replaceAll("\"", "");
                testimonialname = array[1].replaceAll("\"", "");
                testimonialtext = array[2].replaceAll("\"", "");
                testimonialimage = array[3].replaceAll("\"", "");
                testimonialpublish = array[4].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Testimonials/" + testimonialimage;

                Picasso.get().load(imageurl).into(image);

                testname.setText(testimonialname);
                text.setText(testimonialtext);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestimonialUserDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }

    public void backuserTestimonial(View view) {finish();
    }
}