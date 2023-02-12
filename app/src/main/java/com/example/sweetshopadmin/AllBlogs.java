package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllBlogs extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView ls;String user;
    int id;ArrayAdapter<BlogClass> adapter;
    EditText searchBlog; ArrayList<BlogClass> blogs = new ArrayList<BlogClass>();
    ArrayList<String> ids = new ArrayList<String>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_blogs);
            ls=findViewById(R.id.lsBlog);


        searchBlog = findViewById(R.id.etsearchblog);



    adapter = new ArrayAdapter<BlogClass>(this, android.R.layout.simple_list_item_1,blogs);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(this);
         searchBlog.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            (AllBlogs.this).adapter.getFilter().filter(charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });

        RequestQueue queue= Volley.newRequestQueue(this);

       String url = "https://mobileappcourse.000webhostapp.com/JobTech/fetchBlogs.php";
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
                        String text = row.getString("Text");
                        int publish = row.getInt("Publish");
                        String author = row.getString("Author");
                        ids.add(String.valueOf(id));
                        blogs.add(new BlogClass(id, title, text, image,publish,author));
                        System.out.println(ids);
                    } catch (Exception ex) {
                        Toast.makeText(AllBlogs.this, "error", Toast.LENGTH_SHORT).show();
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


        Intent newAct = new Intent(this, BlogDetail.class);
        String position = ids.get(i);
        newAct.putExtra("POSITION", position );
        startActivity(newAct);

    }


}