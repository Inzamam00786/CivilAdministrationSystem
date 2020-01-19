package com.inzamam.civiladministrationsystem;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.santalu.maskedittext.MaskEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddNewComplaint extends AppCompatActivity {
    Toolbar toolbar;
    Button submitbtn, clickimagebtn, gallerybtn;
    File imageFile;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseDatabase fdb;
    DatabaseReference myRef;
    FirebaseUser user;
    String userid;
    FirebaseAuth mAuth;
    MaskEditText maskEditText;
    ProgressBar progressBar;
    String cnic;
    EditText eddesc, edarea;
    public static int i=0;
    private StorageReference storageRef;
    private ImageView imageview;
    private Button btnSelectImage;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    public static String timeStamp;
    public static Uri downloadUrl;


    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewcomplaint);
        toolbar=(Toolbar) findViewById(R.id.addnewcomplainttoolbar);
        submitbtn=(Button) findViewById(R.id.submitbutton);
        clickimagebtn=(Button) findViewById(R.id.clickimagebtn);
        maskEditText=(MaskEditText) findViewById(R.id.cnicmaskedEditText);
        eddesc=(EditText) findViewById(R.id.descriptionEditText);
        edarea=(EditText) findViewById(R.id.areaedittext);
        drawerLayout=(DrawerLayout) findViewById(R.id.mydrawerlayout);
        imageview=(ImageView) findViewById(R.id.clickedimageview);
        progressBar=(ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        storageRef = FirebaseStorage.getInstance().getReference();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New");
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,  R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setTitle("Add Complaint");


      clickimagebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              selectImage();
          }
      });

        navigationView=(NavigationView) findViewById(R.id.mynavigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.viewstatus:
                        startActivity(new Intent(AddNewComplaint.this, ViewComplaintStatus.class));
                        break;
                    case R.id.updateactivity:
                        startActivity(new Intent(AddNewComplaint.this, UpdationActivity.class));
                        break;
                    case R.id.logoutbtn:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(AddNewComplaint.this, SignInActivity.class));
                        finish();
                }
                return false;
            }
        });
    }

    private void selectImage() {
        //try {
        //PackageManager pm = getPackageManager();
        //int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
        // if (hasPerm == PackageManager.PERMISSION_GRANTED) {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AddNewComplaint.this);
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else if (options[item].equals("Choose From Gallery")) {
                    dialog.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }/* else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                 timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                imageview.setImageBitmap(bitmap);
                //imageview.setScaleType(ImageView.ScaleType.FIT_END);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                imgPath = getRealPathFromURI(selectedImage);
                destination = new File(imgPath.toString());
                imageview.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /*public void process(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.jpg");
            Uri uri=Uri.fromFile(imageFile);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 0);


    }*/

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            Uri uri=data.getData();
            StorageReference filepath=storageRef.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddNewComplaint.this, "Your Pic was Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddNewComplaint.this, "Problem while uploading Pic", Toast.LENGTH_SHORT).show();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

  public void uploadPhotoMethod(){
      Uri file = Uri.fromFile(destination);
      StorageReference riversRef = storageRef.child("images/rivers.jpg");

      riversRef.putFile(file)
              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      // Get a URL to the uploaded content
                       downloadUrl = taskSnapshot.getDownloadUrl();
                  }
              })
              .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception exception) {
                      Toast.makeText(AddNewComplaint.this, "Error"+exception.getMessage(), Toast.LENGTH_LONG).show();
                  }
              });
    }
    public void registerComplaintMethod(View view){
        uploadPhotoMethod();
        fdb=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        userid=user.getUid();
       myRef=fdb.getReference("Users").child(userid).child(maskEditText.getRawText());
       myRef.child("Description").setValue(eddesc.getText().toString());
       myRef.child("Area").setValue(edarea.getText().toString());
       myRef.child("Image").setValue(downloadUrl);
       imageview.setImageResource(R.drawable.noimage);
       eddesc.setText("");
       edarea.setText("");
       maskEditText.setText("");
       Toast.makeText(AddNewComplaint.this, "Success", Toast.LENGTH_SHORT).show();
    }
}
