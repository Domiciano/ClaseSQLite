package edu.co.icesi.claseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView profileImage;
    private Button signoutBtn;
    private TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
        signoutBtn = findViewById(R.id.signoutBtn);
        profileImage = findViewById(R.id.profileImage);

        signoutBtn.setOnClickListener(this);


        //Saber si estamos logeados
        FirebaseUser fireuser = FirebaseAuth.getInstance().getCurrentUser();
        if(fireuser == null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{
            FirebaseFirestore.getInstance().collection("users").document(
                    FirebaseAuth.getInstance().getUid()
            ).get().addOnSuccessListener(
                    command -> {
                        User user = command.toObject(User.class);
                        profileName.setText(user.name);
                    }
            );


            FirebaseStorage.getInstance().getReference()
                    .child("profile")
                    .child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid()
                    ).getDownloadUrl().addOnSuccessListener(
                    command -> {
                        String url = command.toString();
                        Glide.with(this).load(url).centerCrop().into(profileImage);
                    }
            );

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signoutBtn:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}