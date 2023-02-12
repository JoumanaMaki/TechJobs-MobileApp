package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class JobDetail extends AppCompatActivity {
ImageView img,checked;
TextView title, requirements, objectives, details, contactcompany,jobcategory;
String jobid;int id;
String categoryid, categoryname;
String jobtitle, companyname, jobtype, category, requirementsjob, objectivesjob,phone,email,publish,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        Intent jobint = getIntent();
        jobid = jobint.getStringExtra("POSITION");

        img = findViewById(R.id.ImageJob);
        title = findViewById(R.id.titleJob);
        requirements = findViewById(R.id.JobRequirements);
        objectives = findViewById(R.id.JobObjectives);
        checked = findViewById(R.id.ispublishjob);
        details = findViewById(R.id.JobDetail);
        contactcompany = findViewById(R.id.CompanyDetails);
        id = Integer.parseInt(jobid);

        jobcategory=findViewById(R.id.Jobcategory);
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/getOneJob.php?ID=" + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();

                String name = String.valueOf(response.charAt(1));
                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 12);
                jobid = array[0].replaceAll("\"", "");
                jobtitle = array[1].replaceAll("\"", "");
                companyname = array[2].replaceAll("\"", "");
                jobtype = array[3].replaceAll("\"", "");
                category = array[4].replaceAll("\"", "");
                requirementsjob = array[5].replaceAll("\"", "");
                objectivesjob = array[6].replaceAll("\"", "");
                phone = array[7].replaceAll("\"", "");
                email = array[8].replaceAll("\"", "");
                publish = array[9].replaceAll("\"", "");
                image = array[10].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Jobs/" + image;

                Picasso.get().load(imageurl).into(img);
                if (publish.equals("0")) {
                    checked.setImageResource(R.drawable.nocheck);
                } else {
                    checked.setImageResource(R.drawable.check);

                }
                title.setText(jobtitle);
                requirements.setText(requirementsjob);
                objectives.setText(objectivesjob);
                contactcompany.setText("Email: " + email + "\n\nPhone Number: " + phone);



                details.setText("Company Name: " + companyname + "\n\nJob Type: " + jobtype );
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);




        RequestQueue queue1= Volley.newRequestQueue(this);

        String url1 = "https://mobileappcourse.000webhostapp.com/JobTech/getOneCategory.php?IDC="+category;

        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();

                String name = String.valueOf(response.charAt(1));
                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 12);
                categoryid = array[0].replaceAll("\"", "");
                categoryname = array[1].replaceAll("\"", "");
                jobcategory.setText("\nJob Category: "+categoryname);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue1.add(request1);

    }

    public void Jobback(View view) {
        finish();
    }

    public void deletetJob(View view) {

        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/deleteJob.php?ID="+id;

        //String url = "http://jobtech.infinityfreeapp.com/deleteBlog.php?ID="+blogid;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                title.setText(response);
                requirements.setText("");
                details.setText("");
                objectives.setText("");
                contactcompany.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }

    public void UpdateJob(View view) {
        Intent newAct = new Intent(this, JobUpdate.class);
        newAct.putExtra("ID", jobid );
        startActivity(newAct);
    }

    public void ViewApplicants(View view) {
        Intent newAct = new Intent(this, AllApplicants.class);
        newAct.putExtra("ID", jobid );
        startActivity(newAct);
    }
}