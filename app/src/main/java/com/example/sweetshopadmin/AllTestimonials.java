package com.example.sweetshopadmin;

import static android.R.layout.simple_list_item_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllTestimonials extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView ls;
    int id;
    ArrayAdapter<TestimonialClass> adapter;
    EditText searchTestimonial; ArrayList<TestimonialClass> testimonials = new ArrayList<TestimonialClass>();
    ArrayList<String> ids = new ArrayList<String>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_testimonials);
        ls=findViewById(R.id.lsTestimonial);


        searchTestimonial = findViewById(R.id.etsearchtestimonial);
        adapter = new ArrayAdapter<TestimonialClass>(this, simple_list_item_1,testimonials);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(this);
        searchTestimonial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (AllTestimonials.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/AllTestimonials.php";
        // String url = "http://jobtech.infinityfreeapp.com/fetchBlogs.php";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++){
                    try{
                        JSONObject row = response.getJSONObject(i);
                        id= row.getInt("Id");
                        String name = row.getString("Name");
                        String  image= row.getString("Image");
                        String text = row.getString("Text");
                        int publish = row.getInt("Publish");
                        ids.add(String.valueOf(id));
                        testimonials.add(new TestimonialClass(id, name, text, image,publish));
                        System.out.println(ids);
                    } catch (Exception ex) {
                        Toast.makeText(AllTestimonials.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, null);
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent newAct = new Intent(this, TestimonialDetail.class);
        String position = ids.get(i);
        newAct.putExtra("POSITION", position );
        startActivity(newAct);
    }


    }