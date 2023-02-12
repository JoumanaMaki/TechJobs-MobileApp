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

public class AllUserRoadmaps extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView ls;
    int id;
    ArrayAdapter<RoadmapClass> adapter;
    EditText searchRoadmaps;
    ArrayList<RoadmapClass> roadmaps = new ArrayList<RoadmapClass>();
    ArrayList<String> idsroadmaps = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_roadmaps);
        ls=findViewById(R.id.lsRoadmapuser);

        searchRoadmaps = findViewById(R.id.etsearchroadmapuser);
        adapter = new ArrayAdapter<RoadmapClass>(this, android.R.layout.simple_list_item_1,roadmaps);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(this);

        searchRoadmaps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (AllUserRoadmaps.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/allroadpublish.php";
        // String url = "http://jobtech.infinityfreeapp.com/fetchBlogs.php";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++){
                    try{
                        JSONObject row = response.getJSONObject(i);
                        id= row.getInt("Id");
                        String title = row.getString("Title");
                        String  steps= row.getString("Steps");
                        String youtube = row.getString("YoutubeLink");
                        int publish = row.getInt("Publish");
                        String image = row.getString("Image");
                        idsroadmaps.add(String.valueOf(id));
                        roadmaps.add(new RoadmapClass(id, title, steps, youtube,image,publish));
                        System.out.println(idsroadmaps);
                    } catch (Exception ex) {
                        Toast.makeText(AllUserRoadmaps.this, "error", Toast.LENGTH_SHORT).show();
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


        Intent newActRoad = new Intent(this, RoadmapUserDetail.class);
        String position = idsroadmaps.get(i);
        newActRoad.putExtra("POSITION", position );
        startActivity(newActRoad);}


}