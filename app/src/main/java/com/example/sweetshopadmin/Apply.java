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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Apply extends AppCompatActivity {
    TextView name, number, email;
    TextView jobname, message;
    ImageView img;
    Button select;
    Bitmap bitmap;
    String encodedimage="default.jpg";
    String jobid,jobName,userid,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        img=findViewById(R.id.imgapply);
        name =findViewById(R.id.etapplyName);
        number = findViewById(R.id.etapplyphonenumber);
        email  = findViewById(R.id.etapplyemail);
        jobname = findViewById(R.id.jobName);
        select = findViewById(R.id.btnapplybrowse);
message= findViewById(R.id.tvmessageapply);


        Intent job = getIntent();
        jobid = job.getStringExtra("ID");
        jobName=job.getStringExtra("JOBNAME");
        userid=job.getStringExtra("USER");
        username=job.getStringExtra("USERNAME");
        //Toast.makeText(this, jobid, Toast.LENGTH_SHORT).show();
jobname.setText(jobName);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Apply.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
    }


    public void GoToOtp(View view) {

        if(name.getText().toString().trim().length() == 0 ||
                number.getText().toString().trim().length() == 0||
                email.getText().toString().trim().length() == 0

        ){
            message.setText("All fields are required");
            return;
        }
        Intent i = new Intent(Apply.this, Sendotp.class);
        i.putExtra("NAME", name.getText().toString());
        i.putExtra("NUMBER", number.getText().toString());
        i.putExtra("EMAIL", email.getText().toString());
        i.putExtra("IMAGE", encodedimage);
        i.putExtra("ID", jobid);
        i.putExtra("USER", userid);
        i.putExtra("USERNAME", username);
        startActivity(i);

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