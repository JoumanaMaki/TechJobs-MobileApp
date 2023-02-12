package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class RoadmapDetail extends AppCompatActivity {
TextView title, steps, youtube;
String roadid;
ImageView imageroad,publish;
String imageurlroad,publish_road,youtubelinkroad,stepsroad,titleRoad,roadId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadmap_detail);

        title = findViewById(R.id.titleRoadmap);
        steps = findViewById(R.id.Steps);
        youtube = findViewById(R.id.Youtube);
        Intent roadintent = getIntent();
        roadid = roadintent.getStringExtra("POSITION");
        imageroad = findViewById(R.id.ImageRoadmap);
        int id = Integer.parseInt(roadid);
        publish=findViewById(R.id.ispublishroad);
        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/getOneRoad.php?ID="+id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();

                String name = String.valueOf(response.charAt(1));
                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 8);
                 roadId= array[0].replaceAll("\"", "");
                titleRoad = array[1].replaceAll("\"", "");
                stepsroad = array[2].replaceAll("\"", "");
                youtubelinkroad = array[3].replaceAll("\"", "");
                publish_road= array[5].replaceAll("\"", "");
                imageurlroad = array[4].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Roadmaps/"+imageurlroad;

                Picasso.get().load(imageurl).into(imageroad);
                if(publish_road.equals("0")){
                    publish.setImageResource(R.drawable.nocheck);
                }else{
                    publish.setImageResource(R.drawable.check);

                }
                title.setText(titleRoad);
                youtube.setText("Youtube Tutorial");
                steps.setText(stepsroad);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoadmapDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void youtubelink(View view) {
       // Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();



        Intent iresult = new Intent (RoadmapDetail.this, YoutubeLoad.class);
        iresult.putExtra("URL", youtubelinkroad);
        startActivity(iresult);
    }

    public void deletetRoadmap(View view) {

        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/deleteRoadmap.php?ID="+Integer.parseInt(roadid);

        //String url = "http://jobtech.infinityfreeapp.com/deleteBlog.php?ID="+blogid;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                title.setText(response);
                steps.setText("");
                youtube.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoadmapDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void UpdateRoadmap(View view) {

        Intent updateroad = new Intent(RoadmapDetail.this,UpdateRoadmap.class);
        updateroad.putExtra("ID",roadid);
        startActivity(updateroad);
    }

    public void backfromroadmap(View view) {

        finish();
    }
}