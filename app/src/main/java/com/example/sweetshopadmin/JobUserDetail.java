package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

public class JobUserDetail extends AppCompatActivity {
    ImageView img;
    TextView jobcategory;
    String categoryid, categoryname;
    ImageButton wts,em,userapply;
    TextView title, requirements, objectives, details, contactcompany;
    String jobid;int id;
    String userid,USERname;
    String jobtitle, companyname, jobtype, category, requirementsjob, objectivesjob,phone,email,publish,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_user_detail);
        Intent jobint = getIntent();
        userid = jobint.getStringExtra("ID");
        jobid = jobint.getStringExtra("POSITION");
        USERname = jobint.getStringExtra("USERNAME");
        wts=findViewById(R.id.btnwts);
        userapply= findViewById(R.id.btnapplyonapp);
        em=findViewById(R.id.btnemail);
        jobcategory=findViewById(R.id.Jobcategoryuser);
        img = findViewById(R.id.ImageJobuser);
        title = findViewById(R.id.titleJobuser);
        requirements = findViewById(R.id.JobRequirementsuser);
        objectives = findViewById(R.id.JobObjectivesuser);
        details=findViewById(R.id.JobDetailuser);
        contactcompany=findViewById(R.id.CompanyDetailsuser);
        id = Integer.parseInt(jobid);
        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/getOneJob.php?ID="+id;

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
                category= array[4].replaceAll("\"", "");
                requirementsjob = array[5].replaceAll("\"", "");
                objectivesjob = array[6].replaceAll("\"", "");
                phone = array[7].replaceAll("\"", "");
                email = array[8].replaceAll("\"", "");
                publish = array[9].replaceAll("\"", "");
                image = array[10].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Jobs/"+image;

                Picasso.get().load(imageurl).into(img);

                title.setText(jobtitle);
                requirements.setText(requirementsjob);
                objectives.setText(objectivesjob);
                contactcompany.setText("Email: "+ email +"\n\nPhone Number: "+ phone);
                details.setText("Company Name: "+ companyname +"\n\nJob Type: "+ jobtype);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobUserDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(JobUserDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue1.add(request1);


        wts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
String wrl = "https://wa.me/+961"+phone+"?text=Hi,Applying From tech job";
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setData(Uri.parse(wrl));
          startActivity(intent);

            }
        });



        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String towho=email;
                String subject = "Apply for "+ jobtitle;
                Intent emailintent = new Intent(Intent.ACTION_SENDTO);
                emailintent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                emailintent.putExtra(Intent.EXTRA_SUBJECT,subject);
               // emailintent.setType("message/rfc822");
                emailintent.setData(Uri.parse("mailto:"));
                startActivity(emailintent);

            }
        });


        userapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(JobUserDetail.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent applyint = new Intent(JobUserDetail.this, Apply.class);
                applyint.putExtra("ID", jobid);
                applyint.putExtra("USER", userid);
                applyint.putExtra("JOBNAME", jobtitle);
                applyint.putExtra("USERNAME", USERname);
                startActivity(applyint);
            }
        });

    }

    public void Jobuserback(View view) {
        finish();
    }
}