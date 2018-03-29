package com.example.dell.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SecondActivity extends AppCompatActivity {

    private TextView newemail, newphoneno, newage1;
    private ImageView image;
    private Button logout, update;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        newemail = findViewById(R.id.newname);
        newphoneno = findViewById(R.id.newphone);
        newage1 = findViewById(R.id.newage);
        logout = findViewById(R.id.btnlogout);
        update = findViewById(R.id.btnedit);
        image = findViewById(R.id.image2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //getting image from storage
        storageReference = firebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/myapplication-3ca85.appspot.com/o/" + firebaseAuth.getCurrentUser().getUid() + "/Images/Profile Picture");
        //String ab = firebaseAuth.getCurrentUser().getUid();
        //String abc = ab + "/Images/Profile Picture";

        Glide.with(SecondActivity.this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(image);

        databaseReference = firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile;
                userProfile = dataSnapshot.getValue(UserProfile.class);
                newemail.setText("Email: " + userProfile.getA());
                newphoneno.setText("Phone No.: " + userProfile.getC());
                newage1.setText("Age: " + userProfile.getD());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, ProfileUpdate.class));
            }
        }));

        logout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(SecondActivity.this, FirstActivity.class));
            }
        }));

    }
}
