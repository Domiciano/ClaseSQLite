package edu.co.icesi.claseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.UUID;

import edu.co.icesi.claseauth.data.Contact;
import edu.co.icesi.claseauth.data.User;
import edu.co.icesi.claseauth.db.AppDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signoutBtn;
    private TextView profileName;

    private EditText contactNameET;
    private EditText contacPhonetET;
    private Button submitBtn;
    private Button deleteBtn;
    private Button syncBtn;
    private TextView dbconsoleTV;

    private FirebaseFirestore db;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        profileName = findViewById(R.id.profileName);
        signoutBtn = findViewById(R.id.signoutBtn);
        contactNameET = findViewById(R.id.contactNameET);
        contacPhonetET = findViewById(R.id.contacPhonetET);
        submitBtn = findViewById(R.id.submitBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        dbconsoleTV = findViewById(R.id.dbconsoleTV);
        syncBtn = findViewById(R.id.syncBtn);


        signoutBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        syncBtn.setOnClickListener(this);



        //Saber si estamos logeados
        if (auth.getCurrentUser() == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            db.collection("users")
                    .document(auth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(
                            command -> {
                                User user = command.toObject(User.class);
                                Toast.makeText(this, "Bienvenido "+user.name, Toast.LENGTH_LONG).show();
                            }
                    );
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.submitBtn:


                break;

            case R.id.deleteBtn:

                break;

            case R.id.syncBtn:

                break;

            case R.id.signoutBtn:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;

        }
    }
}