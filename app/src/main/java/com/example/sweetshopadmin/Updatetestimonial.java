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

public class Updatetestimonial extends AppCompatActivity {
Button select, upload;
EditText name, feedback;
RadioButton yes, no;
ImageView img;
int id;
TextView tv;String checked;
    String testimonialid;
    String testimonialname, testimonialtext, testimonialpublish, testimonialimage;

    Bitmap bitmap;
    String encodedimage="default.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatetestimonial);
tv = findViewById(R.id.mesgtestimonialupdate);
        Intent roadmap = getIntent();
        id = Integer.parseInt(roadmap.getStringExtra("ID"));
        name = findViewById(R.id.etnametestimonialupdate);
        feedback=findViewById(R.id.ettexttestimonialupdate);
        yes = findViewById(R.id.rbyestestimonial);
        no = findViewById(R.id.rbNOtestimonial);

        select= findViewById(R.id.buttonChooseTestimonialupdate);
        upload=findViewById(R.id.buttonUploadTestimonialupdate);
        img =findViewById(R.id.imageViewTestimonialupdate);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Updatetestimonial.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/GetOneTestimonial.php?ID=" + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();


                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 5);
                testimonialid = array[0].replaceAll("\"", "");
                testimonialname = array[1].replaceAll("\"", "");
                testimonialtext = array[2].replaceAll("\"", "");
                testimonialimage = array[3].replaceAll("\"", "");
                testimonialpublish = array[4].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Testimonials/" + testimonialimage;

                Picasso.get().load(imageurl).into(img);
                if (testimonialpublish.equals("0")) {
                    no.setChecked(true);
                } else {
                    yes.setChecked(true);

                }
                name.setText(testimonialname);
                feedback.setText(testimonialtext);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Updatetestimonial.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);





        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().trim().length() == 0 ||
                        feedback.getText().toString().trim().length() == 0
                ){
                    tv.setText("All fields are required");
                    return;
                }

                if(yes.isChecked()){
                    checked="1";
                }else{
                    checked="0";
                }
                String url = "https://mobileappcourse.000webhostapp.com/JobTech/updateTestimonial.php?ID="+testimonialid;

                //String url = "http://jobtech.infinityfreeapp.com/addBlogs.php";

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Uploadimages.this, response, Toast.LENGTH_SHORT).show();
                        tv.setText(response);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(Uploadimages.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                tv.setText(error.toString());
                            }

                        }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("image", encodedimage);
                        params.put("Name", name.getText().toString());
                        params.put("Feedback",feedback.getText().toString());
                        params.put("Publish",checked);
                        params.put("key","joumana");
                        return params;
                    }
                };
                RequestQueue requestQueue  = Volley.newRequestQueue(Updatetestimonial.this);
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