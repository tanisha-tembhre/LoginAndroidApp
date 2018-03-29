package com.example.dell.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileUpdate extends AppCompatActivity {

    private EditText editphone, editage;
    private Button update1;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String e,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        editphone = findViewById(R.id.eteditphone);
        editage = findViewById(R.id.eteditage);
        update1 = findViewById(R.id.btnupdate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                editphone.setText(userProfile.getC());
                editage.setText(userProfile.getD());
                e = userProfile.getA();
                p = userProfile.getB();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileUpdate.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = editphone.getText().toString();
                String ag = editage.getText().toString();
                UserProfile userProfile = new UserProfile(e, p, ph, ag);
                databaseReference.setValue(userProfile);

                Toast.makeText(ProfileUpdate.this, "Update Successful", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ProfileUpdate.this, SecondActivity.class));

            }
        });
    }
}
