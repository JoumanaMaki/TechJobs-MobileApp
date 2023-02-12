package com.example.sweetshopadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

import org.w3c.dom.Text;

public class BlogDetail extends AppCompatActivity {
ImageView img;
TextView title, text,authorblog;
String blogid;
String titleBlog,titleText;
String publish_blog;
String urlimage;
ImageView is_publish;
    String author;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        Intent blogint = getIntent();
         blogid = blogint.getStringExtra("POSITION");
is_publish = findViewById(R.id.ispublish);
         img = findViewById(R.id.ImageBlog);
        title = findViewById(R.id.titleBlg);
        text = findViewById(R.id.textBlg);
        authorblog= findViewById(R.id.authorBlg);
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
                if(publish_blog.equals("0")){
                    is_publish.setImageResource(R.drawable.nocheck);
                }else{
                    is_publish.setImageResource(R.drawable.check);

                }
                title.setText(titleBlog);
                text.setText(titleText);
                authorblog.setText(author);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BlogDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }

    public void UpdateBlog(View view) {
        Intent updateblog = new Intent(BlogDetail.this,UpdateBlg.class);
         updateblog.putExtra("ID",blogid);
        startActivity(updateblog);
    }

    public void deletetBlog(View view) {


        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/deleteBlog.php?ID="+blogid;

        //String url = "http://jobtech.infinityfreeapp.com/deleteBlog.php?ID="+blogid;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                title.setText(response);
                text.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BlogDetail.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }

    public void back(View view) {
        finish();
    }
}
