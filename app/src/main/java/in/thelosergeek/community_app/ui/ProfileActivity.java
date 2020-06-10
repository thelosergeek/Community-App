package in.thelosergeek.community_app.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import in.thelosergeek.community_app.R;

public class ProfileActivity extends AppCompatActivity {

     FirebaseAuth firebaseAuth;
     FirebaseUser firebaseUser;
     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;

     CircleImageView d_image;
     TextView d_name, d_email, d_designation;
     StorageReference imagestorage;
     ProgressDialog progressDialog;
     FloatingActionButton floatingActionButton;

    private static final int imagepick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        d_image = findViewById(R.id.tvphoto);
        d_name = (TextView) findViewById(R.id.tvname);
        d_email = (TextView) findViewById(R.id.tvemail);
        d_designation = findViewById(R.id.tvdesignation);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.btnchange);

        // Fetching User Data
        firebaseAuth =
        imagestorage = FirebaseStorage.getInstance().getReference();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = currentuser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        databaseReference.keepSynced(true);




}