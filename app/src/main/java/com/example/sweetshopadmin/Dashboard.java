package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    TextView options;
    String name;
    String id;
    TextView txtname;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_dashboard);


        Intent newAct = getIntent();
       id = newAct.getStringExtra("ID");
        name = newAct.getStringExtra("NAME");
        txtname = findViewById(R.id.tvName);
        options = findViewById(R.id.tvOptions);
        txtname.setText(name + " !");

    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.dashboard_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            // Toast.makeText(this, "Selected Item: "  + item.getTitle(), Toast.LENGTH_SHORT).show();

            switch(item.getItemId()){

                case R.id.roadmap_item:
                    Intent roadmapintent = new Intent(Dashboard.this, UploadRoadmap.class);
                 startActivity(roadmapintent);
                    return true;

                case R.id.blog_item:

                    Intent blogintent = new Intent(Dashboard.this, Uploadimages.class);
                   startActivity(blogintent);
                    return true;

                case R.id.job_item:
                    Intent jobintent = new Intent(Dashboard.this, UploadJobs.class);
                    jobintent.putExtra("ID", id);

                    startActivity(jobintent);
                    return true;


                case R.id.testimonial_item:
                    Intent testimonial = new Intent(Dashboard.this, UploadTestimonial.class);
                    startActivity(testimonial);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    public void viewRoadmap(View view) {
        Intent roadintent = new Intent(Dashboard.this, AllRoadmaps.class);
        startActivity(roadintent);
    }

    public void viewBlogs(View view) {
        Intent blogs = new Intent(Dashboard.this, AllBlogs.class);
        startActivity(blogs);
    }

    public void viewTestimonials(View view) {

        Intent testimonials = new Intent(Dashboard.this, AllTestimonials.class);
        startActivity(testimonials);
    }

    public void viewJobs(View view) {

        Intent jobs = new Intent(Dashboard.this, AllJobs.class);
        jobs.putExtra("ID", id);
        startActivity(jobs);
    }
}
