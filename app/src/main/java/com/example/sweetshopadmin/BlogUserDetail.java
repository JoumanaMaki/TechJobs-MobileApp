package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class BlogUserDetail extends AppCompatActivity {
    ImageView img;
    TextView title, text,authorblog;
    String blogid;
    String titleBlog,titleText;
    String publish_blog;
    String urlimage;
    String author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_user_detail);

        Intent blogint = getIntent();


        blogid = blogint.getStringExtra("POSITION");
        img = findViewById(R.id.ImageBloguser);
        title = findViewById(R.id.titleBlguser);
        text = findViewById(R.id.textBlguser);
        authorblog= findViewById(R.id.authorBlguser);
        int id = Integer.parseInt(blogid);

        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/getOneBlog.php?ID="+id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();

                String name = String.valueOf(response.charAt(1));
                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 8);
                blogid = array[0].replaceAll("\"", "");
                titleBlog = array[1].replaceAll("\"", "");
                urlimage = array[2].replaceAll("\"", "");
                titleText = array[3].replaceAll("\"", "");
                publish_blog= array[4].replaceAll("\"", "");
                author = array[5].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Images/"+urlimage;

                Picasso.get().load(imageurl).into(img);
                title.setText(titleBlog);
                text.setText(titleText);
                authorblog.setText(author);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BlogUserDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }


    public void backuserdetail(View view) {finish();
    }
}