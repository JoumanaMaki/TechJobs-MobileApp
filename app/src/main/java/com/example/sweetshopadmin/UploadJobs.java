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
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadJobs extends AppCompatActivity {
TextView tv;
EditText title,compname,compnumb,compemail,requirements,objectives;
RadioButton remote,onplace,hybrid;
Spinner category;
ImageView img;
Button select,upload;
String Type,JobCategory;
String id;
    Bitmap bitmap;
    String encodedimage="default.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_jobs);


        Intent i = getIntent();
       id= i.getStringExtra("ID");

        tv=findViewById(R.id.mesgjob);
        title=findViewById(R.id.etTitleJob);
        compname=findViewById(R.id.etCompanyName);
        compnumb=findViewById(R.id.etCpompanyNumber);

        compemail=findViewById(R.id.etCpompanyEmail);
        requirements=findViewById(R.id.etRequirements);
        objectives=findViewById(R.id.etObjectives);
        remote=findViewById(R.id.rbremote);
        onplace=findViewById(R.id.rbonplace);
        hybrid=findViewById(R.id.rbhybrid);
        category=findViewById(R.id.spCategory);
        select=findViewById(R.id.btnjobcompany);
        upload=findViewById(R.id.btnjobupload);
        img=findViewById(R.id.imgjobcompany);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(UploadJobs.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"select image"),1);
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


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(compname.getText().toString().trim().length() == 0 ||
                        title.getText().toString().trim().length() == 0||
                        compnumb.getText().toString().trim().length() == 0||
                        requirements.getText().toString().trim().length() == 0||
                        objectives.getText().toString().trim().length() == 0||
                        compemail.getText().toString().trim().length() == 0

                ){
                    tv.setText("All fields are required");
                    return;
                }


                if(remote.isChecked()){
                    Type="Remote";
                }else if(onplace.isChecked()){
                    Type="OnPlace";
                }else if(hybrid.isChecked()){
                    Type="Hybrid";
                }

                int spPosition = category.getSelectedItemPosition();
                //String spValue = cars.getSelectedItem().toString();
                if(spPosition == 0){
                    JobCategory="0";
                }else if(spPosition== 1){
                    JobCategory="1";
                }
                else if(spPosition==2){
                    JobCategory="2";
                }
                else if(spPosition==3){
                    JobCategory="3";
                }   else if(spPosition==4){
                    JobCategory="4";
                }   else if(spPosition==5){
                    JobCategory="5";
                }  else if(spPosition==6){
                    JobCategory="6";
                }




                String url = "https://mobileappcourse.000webhostapp.com/JobTech/uploadJob.php";

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
                     params.put("Title", title  .getText().toString());
                      params.put("CompName",compname.getText().toString());
                        params.put("CompNum",compnumb.getText().toString());
                      params.put("CompEmail",compemail.getText().toString());
                        params.put("Requirements",requirements.getText().toString());
                        params.put("Objectives",objectives.getText().toString());
                        params.put("JobTpe",Type);
                        params.put("JobCategory",JobCategory);
                        params.put("USERID",id);

                        return params;
                    }
                };
                RequestQueue requestQueue  = Volley.newRequestQueue(UploadJobs.this);
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