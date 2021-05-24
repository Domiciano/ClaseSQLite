package edu.co.icesi.claseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signoutBtn;
    private TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
        signoutBtn = findViewById(R.id.signoutBtn);
        signoutBtn.setOnClickListener(this);


        //Saber si estamos logeados
        FirebaseUser fireuser = FirebaseAuth.getInstance().getCurrentUser();
        if(fireuser == null){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signoutBtn:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }
}