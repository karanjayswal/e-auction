package com.example.e_auctionfinal.BottomNavigation;

import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.e_auctionfinal.GetLocation;
import com.example.e_auctionfinal.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Seller extends MainActivity2 {
    String admin;
    String groupname,key;
    FirebaseDatabase database;
    Toolbar toolbar;
    EditText etNameSeller,etDescSeller,etPriceSeller;
    String name,desc,price,url;
    ImageView imageView;
    EditText etGroupname;
    private Uri imageUri;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Items");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    ProgressDialog progressDialog;

    @Override
    int getContentViewId() {
        getSupportActionBar().setTitle("Seller");
        admins = getIntent().getStringExtra("username");
        return R.layout.activity_seller;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.BOTTOM_Seller;
    }

    public void Select(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView = findViewById(R.id.imageViewSeller);
            imageView.setImageURI(imageUri);
        }
    }
    public void Upload(View view) {

        if (imageUri != null){
            uploadToFirebase(imageUri);
        }else{
            Toast.makeText(Seller.this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadToFirebase(Uri imageUri) {
        etDescSeller = findViewById(R.id.etDescSeller);
        etNameSeller = findViewById(R.id.etNameSeller);
        etPriceSeller = findViewById(R.id.etPriceSeller);

        name = etNameSeller.getText().toString().trim();
        desc = etDescSeller.getText().toString().trim();
        price = etPriceSeller.getText().toString().trim();

        final StorageReference fileRef = FirebaseStorage.getInstance().getReference().
                child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Intent intent = new Intent(Seller.this, GetLocation.class);
                        intent.putExtra("name",name);
                        intent.putExtra("desc",desc);
                        intent.putExtra("price",price);
                        intent.putExtra("url",uri.toString());
                        intent.putExtra("owner",admins);
                        startActivity(intent);
                    }
                });
            }
        });



    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}