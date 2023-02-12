package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class UserDashboard extends AppCompatActivity {
    TextView options;
    String name;
    String id;
    String categoryid;
    String idname;
    AdView mAdView;
    TextView txtname;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_user_dashboard);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Intent newAct = getIntent();
        id = newAct.getStringExtra("USER");
        name = newAct.getStringExtra("USERNAME");

        txtname = findViewById(R.id.tvuserName);
        options = findViewById(R.id.tvuserOptions);
        txtname.setText(name + " !");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


          switch(item.getItemId()) {

            case R.id.ml_item:
                Intent ml = new Intent(UserDashboard.this, Artificial.class);
                ml.putExtra("ID","0");
                ml.putExtra("NAME", item.getTitle());
                startActivity(ml);
                return true;


            case R.id.cyber:
                Intent cyber = new Intent(UserDashboard.this, Artificial.class);
                cyber.putExtra("ID","6");
                cyber.putExtra("NAME", item.getTitle());
                startActivity(cyber);
                return true;

            case R.id.artificial_item:
                Intent artificial = new Intent(UserDashboard.this, Artificial.class);
                artificial.putExtra("ID","5");
                artificial.putExtra("NAME", item.getTitle());
                startActivity(artificial);
                return true;


            case R.id.mobile:
                Intent mobile = new Intent(UserDashboard.this, Artificial.class);
                mobile.putExtra("ID","4");
                mobile.putExtra("NAME", item.getTitle());
                startActivity(mobile);
                return true;

            case R.id.se:
                Intent sf = new Intent(UserDashboard.this, Artificial.class);
                sf.putExtra("ID","2");
                sf.putExtra("NAME", item.getTitle());
                startActivity(sf);
                return true;

            case R.id.web:

                Intent develop = new Intent(UserDashboard.this, Artificial.class);
                develop.putExtra("ID","3");
                develop.putExtra("NAME", item.getTitle());
                startActivity(develop);
                return true;
            case R.id.it_item:

                Intent itIntent = new Intent(UserDashboard.this, Artificial.class);
                itIntent.putExtra("ID","1");
                itIntent.putExtra("NAME", item.getTitle());
                startActivity(itIntent);
                return true;


              case R.id.testimonial_item:

                  Intent test = new Intent(UserDashboard.this, UploadTestimonial.class);
                  startActivity(test);
                  return true;

            default:
                return super.onOptionsItemSelected(item);}
      }
    public void viewuserRoadmap(View view) {
        Intent roadintent = new Intent(UserDashboard.this, AllUserRoadmaps.class);
        startActivity(roadintent);
    }

    public void viewTestimonialsuser(View view) {


        Intent testimonials = new Intent(UserDashboard.this, AllUserTestimonials.class);
        startActivity(testimonials);
    }

    public void viewBlogsuser(View view) {

        Intent blogs = new Intent(UserDashboard.this, AllUserBlogs.class);
        startActivity(blogs);
    }

    public void viewuserJobs(View view) {

        Intent jobs = new Intent(UserDashboard.this, AllUserJobs.class);
        jobs.putExtra("ID", id );
        jobs.putExtra("USERNAME", name );
        startActivity(jobs);
    }
}