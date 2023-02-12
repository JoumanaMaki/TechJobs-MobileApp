package com.example.sweetshopadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JobUpdate extends AppCompatActivity {
    TextView tv;
    EditText title;
    EditText compname;
    EditText compnumb;
    EditText compemail;
    EditText requirements;
    EditText objectives;
    String ispublish;
    RadioButton jobyes, jobno;
    String jobtitle, companyname, jobtype, category, requirementsjob, objectivesjob,phone,email,publish,image;
    RadioButton remote,onplace,hybrid;
    Spinner jobcategory;
    ImageView img;
    Button select,upload;
    String Type,JobCategory;
    Bitmap bitmap;
    String encodedimage="default.jpg";
    String Jobid;
    int id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_update);

        Intent jobint = getIntent();
        Jobid = jobint.getStringExtra("ID");
       tv=findViewById(R.id.mesgjobupdate);
        title=findViewById(R.id.etTitleJobUpdate);
        compname=findViewById(R.id.etCompanyNameUpdate);
        compnumb=findViewById(R.id.etCpompanyNumberUpdate);
        jobyes=findViewById(R.id.rbyesjob);
        jobno = findViewById(R.id.rbNOjob);
        compemail=findViewById(R.id.etCpompanyEmailUpdate);
        requirements=findViewById(R.id.etRequirementsUpdate);
        objectives=findViewById(R.id.etObjectivesUpdate);
        remote=findViewById(R.id.rbremoteUpdate);
        onplace=findViewById(R.id.rbonplaceUpdate);
        hybrid=findViewById(R.id.rbhybridUpdate);
        jobcategory=findViewById(R.id.spCategoryUpdate);
        select=findViewById(R.id.btnjobcompanyUpdate);
        upload=findViewById(R.id.btnjobUpdate);
        img=findViewById(R.id.imgjobcompanyUpdate);


  id = Integer.parseInt(Jobid);

      RequestQueue queue= Volley.newRequestQueue(this);

        String url = "https://mobileappcourse.000webhostapp.com/JobTech/getOneJob.php?ID="+id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(BlogDetail.this, response, Toast.LENGTH_SHORT).show();

                String name = String.valueOf(response.charAt(1));
                String res = response.replace("[", "");
                String res2 = res.replace("]", "");

                String[] array = res2.split(",", 12);
                Jobid = array[0].replaceAll("\"", "");
                jobtitle = array[1].replaceAll("\"", "");
                companyname = array[2].replaceAll("\"", "");
                jobtype = array[3].replaceAll("\"", "");
                category= array[4].replaceAll("\"", "");
                requirementsjob = array[5].replaceAll("\"", "");
                objectivesjob = array[6].replaceAll("\"", "");
                phone = array[7].replaceAll("\"", "");
                email = array[8].replaceAll("\"", "");
                publish = array[9].replaceAll("\"", "");
                image = array[10].replaceAll("\"", "");
                String imageurl = "https://mobileappcourse.000webhostapp.com/JobTech/Jobs/"+image;

                Picasso.get().load(imageurl).into(img);
                if(publish.equals("0")){
                   jobno.setChecked(true);
                }else{
                    jobyes.setChecked(true);
                }

                if(jobtype.equals("Remote")){
                    remote.setChecked(true);
                }else if(jobtype.equals("OnPlace")){
                    onplace.setChecked(true);
                }else{
                    hybrid.setChecked(true);
                }

                title.setText(jobtitle);
                requirements.setText(requirementsjob);
                objectives.setText(objectivesjob);
                compemail.setText(email);
                compnumb.setText(phone);
                compname.setText(companyname);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobUpdate.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

       select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(JobUpdate.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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


                if(jobyes.isChecked()){
                    ispublish = "1";
                }else if(jobno.isChecked()){
                    ispublish = "0";
                }
                if(remote.isChecked()){
                    Type="Remote";
                }else if(onplace.isChecked()){
                    Type="OnPlace";
                }else if(hybrid.isChecked()){
                    Type="Hybrid";
                }

                int spPosition = jobcategory.getSelectedItemPosition();
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




                String url = "https://mobileappcourse.000webhostapp.com/JobTech/updateJob.php?ID="+id;

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
                        params.put("Publish",ispublish);
                        return params;
                    }
                };
                RequestQueue requestQueue  = Volley.newRequestQueue(JobUpdate.this);
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