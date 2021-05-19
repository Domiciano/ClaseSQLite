package edu.co.icesi.claseauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private EditText usernameET;
    private FirebaseFirestore db;
    private EditText passwordET;
    private TextView singupLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        }, 1);


        db = FirebaseFirestore.getInstance();

        loginBtn = findViewById(R.id.loginBtn);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        singupLink = findViewById(R.id.signupLink);

        loginBtn.setOnClickListener(this);
        singupLink.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        usernameET.getText().toString().trim(),
                        passwordET.getText().toString().trim()
                ).addOnSuccessListener(
                        command -> {
                            Intent i = new Intent(this, ProfileActivity.class);
                            startActivity(i);
                        }
                ).addOnFailureListener(
                        command -> {
                            Toast.makeText(this, command.getLocalizedMessage(), Toast.LENGTH_LONG)
                                    .show();
                        }
                );
                break;

            case R.id.signupLink:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
        }
    }
}