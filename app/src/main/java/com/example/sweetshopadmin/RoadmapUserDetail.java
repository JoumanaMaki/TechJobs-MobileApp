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

public class RoadmapUserDetail extends AppCompatActivity {
    TextView title, steps, youtube;
    String roadid;
    ImageView imageroad;
    String imageurlroad,publish_road,youtubelinkroad,stepsroad,titleRoad,roadId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadmap_user_detail);
        title = findViewById(R.id.titleRoadmapuser);
        steps = findViewById(R.id.Stepsuser);
        youtube = findViewById(R.id.Youtubeuser);
        Intent roadintent = getIntent();
        roadid = roadintent.getStringExtra("POSITION");
        imageroad = findViewById(R.id.ImageRoadmapuser);
        int id = Integer.parseInt(roadid);

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

                title.setText(titleRoad);
                youtube.setText("Youtube Tutorial");
                steps.setText(stepsroad);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoadmapUserDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

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



        Intent iresult = new Intent (RoadmapUserDetail.this, YoutubeLoad.class);
        iresult.putExtra("URL", youtubelinkroad);
        startActivity(iresult);
    }

    public void roaduserdetail(View view) {finish();
    }
}