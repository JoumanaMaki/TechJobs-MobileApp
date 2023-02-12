package com.example.sweetshopadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateRoadmap extends AppCompatActivity {
    Button select ,upload;
    ImageView img;
    Bitmap bitmap;
    String encodedimage="default.jpg";
    EditText titleroad, stepsroad,youtuberoad;
    TextView tv;
    String roadid;
    String title,text;
    String urlimage;
    RadioButton yes,no;
    String checked;

    String roadId,titleRoad,stepsRoad,youtubelinkroad,imageurlroad,publish_road;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_roadmap);

        titleroad = findViewById(R.id.etTitleRoadmapupdate);
        stepsroad = findViewById(R.id.etStepsRoadmapupdate);
        youtuberoad = findViewById(R.id.etYoutubeLinkupdate);
        tv = findViewById(R.id.mesgroadmapupdate);
        Intent roadmap = getIntent();
        id = Integer.parseInt(roadmap.getStringExtra("ID"));
img=findViewById(R.id.imageViewRoadupdate);
        upload = findViewById(R.id.buttonUploadRoadupdate);
        yes = findViewById(R.id.rbyesroad);
        no = findViewById(R.id.rbNOroad);

        select = findViewById(R.id.buttonChooseRoadupdate);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(UpdateRoadmap.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "select image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }


        });

        RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/getOneRoad.php?ID=" + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();

                String name = String.valueOf(response.charAt(1));
                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 8);
                roadId = array[0].replaceAll("\"", "");
                titleRoad = array[1].replaceAll("\"", "");
                stepsRoad = array[2].replaceAll("\"", "");
                youtubelinkroad = array[3].replaceAll("\"", "");
                publish_road = array[5].replaceAll("\"", "");
                imageurlroad = array[4].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Roadmaps/" + imageurlroad;

                Picasso.get().load(imageurl).into(img);
                if (publish_road.equals("1")) {
                    yes.setChecked(true);

                } else {

                   no.setChecked(true);

                }
                titleroad.setText(titleRoad);
                stepsroad.setText(stepsRoad);
                youtuberoad.setText(youtubelinkroad);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateRoadmap.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(titleroad.getText().toString().trim().length() == 0 ||
                        stepsroad.getText().toString().trim().length() == 0||
                        youtuberoad.getText().toString().trim().length() == 0
                ){
                    tv.setText("All fields are required");
                    return;
                }

                if(yes.isChecked()){
                    checked="1";
                }else{
                    checked="0";
                }
                String url = "https://mobileappcourse.000webhostapp.com/JobTech/updateRoadmap.php?ID="+id;

                //String url = "http://jobtech.infinityfreeapp.com/UpdateBlog.php?ID="+id;

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateRoadmap.this, response, Toast.LENGTH_SHORT).show();
                        tv.setText(response);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UpdateRoadmap.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                tv.setText(error.toString());
                            }

                        }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("image", encodedimage);
                        params.put("Title", titleroad.getText().toString());
                        params.put("Step",stepsroad.getText().toString());
                        params.put("Link",youtuberoad.getText().toString());
                        params.put("Publish",checked);
                        params.put("key", "joumana");

                        return params;
                    }
                };
                RequestQueue requestQueue  = Volley.newRequestQueue(UpdateRoadmap.this);
                requestQueue.add(request);

                request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode == 1 && resultCode == RESULT_OK && data !=null){
            Uri filePath = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

                imageStore(bitmap);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedimage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }
}