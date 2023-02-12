package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class IT extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView ls;
    int id;
    ArrayAdapter<JobClass> adapter;
    EditText searchBlog; ArrayList<JobClass> jobs = new ArrayList<JobClass>();
    ArrayList<String> ids = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it);
        ls=findViewById(R.id.lsJobsit);
        searchBlog = findViewById(R.id.etsearchjobsit);



        adapter = new ArrayAdapter<JobClass>(this, android.R.layout.simple_list_item_1,jobs);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(this);
        searchBlog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (IT.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/it.php";
        // String url = "http://jobtech.infinityfreeapp.com/fetchBlogs.php";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++){
                    try{
                        JSONObject row = response.getJSONObject(i);
                        id= row.getInt("Id");
                        String title = row.getString("Title");
                        String  image= row.getString("Image");
                        String CompanyName = row.getString("CompanyName");
                        String JobType = row.getString("JobType");
                        String JobCategory = row.getString("JobCategory");
                        String Requirements = row.getString("Requirements");
                        String Objectives = row.getString("Objectives");
                        String Phone_nb = row.getString("Phone_nb");
                        String Email = row.getString("Email");
                        int publish = row.getInt("Publish");

                        ids.add(String.valueOf(id));
                        jobs.add(new JobClass(id, title, CompanyName,Phone_nb,Email,Requirements,Objectives,JobType,JobCategory, publish,image));
                        System.out.println(ids);
                    } catch (Exception ex) {
                        //   Toast.makeText(AllBlogs.this, "error", Toast.LENGTH_SHORT).show();
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
        Intent newAct = new Intent(this, JobUserDetail.class);
        String position = ids.get(i);
        newAct.putExtra("POSITION", position );
        startActivity(newAct);
    }
}