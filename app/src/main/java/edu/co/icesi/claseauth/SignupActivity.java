package edu.co.icesi.claseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.co.icesi.claseauth.data.User;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameET, cityET, emailET, passwordET, repasswordET;
    private Button signupBtn;
    private TextView loginLink;
    private FirebaseFirestore db;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        nameET = findViewById(R.id.nameET);
        cityET = findViewById(R.id.cityET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        repasswordET = findViewById(R.id.repasswordET);
        signupBtn = findViewById(R.id.signupBtn);
        loginLink = findViewById(R.id.loginLink);

        loginLink.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginLink:
                finish();
                break;
            case R.id.signupBtn:

                //Si es exitoso nos deja logged
                String email = emailET.getText().toString();
                String pass = passwordET.getText().toString();
                auth.createUserWithEmailAndPassword(email,pass)
                        .addOnSuccessListener(this::uploadUser)
                        .addOnFailureListener(this::onFailure);
                break;
        }
    }

    public void uploadUser(AuthResult result) {
        //Registrar los otros datos
        User user = new User(
                auth.getCurrentUser().getUid(),
                nameET.getText().toString(),
                emailET.getText().toString(),
                cityET.getText().toString()
        );

        db.collection("users").document(user.id)
                .set(user).addOnSuccessListener(
                dbresult -> {
                    Intent i = new Intent(this, ProfileActivity.class);
                    startActivity(i);
                }
        );
    }

    public void onFailure(Exception exception) {
        Toast.makeText(this, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

}