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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllApplicants extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView ls;
    String jobid;
    ArrayAdapter<Applicant> adapter;
int id;
String name,phone,email,image,jobId;
    ArrayList<Applicant> applicants = new ArrayList<Applicant>();
    ArrayList<String> ids = new ArrayList<String>();
    EditText searchapplicant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_applicants);

        searchapplicant=findViewById(R.id.etsearchapplicants);
        ls = findViewById(R.id.lsApplicants);

        Intent intent = getIntent();
        jobid = intent.getStringExtra("ID");

        adapter = new ArrayAdapter<Applicant>(this, android.R.layout.simple_list_item_1,applicants);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(this);
        searchapplicant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (AllApplicants.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    RequestQueue queue= Volley.newRequestQueue(this);

    String url = "https://mobileappcourse.000webhostapp.com/JobTech/allApplicants.php?ID="+jobid;
    // String url = "http://jobtech.infinityfreeapp.com/fetchBlogs.php";

    JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            if(response.length() == 0){
                searchapplicant.setText("NO Applicants");
                searchapplicant.setEnabled(false);
            }
            for(int i = 0; i<response.length(); i++){
                try{
                    JSONObject row = response.getJSONObject(i);
                    id= row.getInt("Id");
                    name = row.getString("Name");
                    phone  = row.getString("Phone");
                     email = row.getString("Email");
                     image = row.getString("Image");
                     jobId = row.getString("JobId");


                    ids.add(String.valueOf(id));
                    applicants.add(new Applicant(id, name, phone,email,image,jobId));

                } catch (Exception ex) {
                       Toast.makeText(AllApplicants.this, "error", Toast.LENGTH_SHORT).show();
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
        Intent newAct = new Intent(this, ApplicantDetail.class);
        String position = ids.get(i);
        newAct.putExtra("POSITION", position );
        startActivity(newAct);
    }
}