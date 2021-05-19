package edu.co.icesi.claseauth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_CALLBACK = 11;


    private EditText nameET, cityET, emailET, passwordET, repasswordET;
    private ImageView profileRegImage;
    private Button signupBtn;
    private Button editProfileImage;
    private TextView loginLink;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FirebaseAuth auth;


    //Path de la imagen de perfil
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();


        profileRegImage = findViewById(R.id.profileRegImage);
        nameET = findViewById(R.id.nameET);
        cityET = findViewById(R.id.cityET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        repasswordET = findViewById(R.id.repasswordET);
        signupBtn = findViewById(R.id.signupBtn);
        loginLink = findViewById(R.id.loginLink);
        editProfileImage = findViewById(R.id.editProfileImage);

        loginLink.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        editProfileImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginLink:
                finish();
                break;
            case R.id.signupBtn:

                //Si es exitoso nos deja logged
                auth.createUserWithEmailAndPassword(
                        emailET.getText().toString(),
                        passwordET.getText().toString()
                ).addOnSuccessListener(
                        command -> {
                            //Registrar los otros datos

                            User user = new User(
                                    auth.getCurrentUser().getUid(),
                                    nameET.getText().toString(),
                                    emailET.getText().toString(),
                                    cityET.getText().toString()
                            );

                            try {
                                FirebaseStorage.getInstance().getReference()
                                        .child("profile")
                                        .child(user.id)
                                        .putStream(new FileInputStream(new File(path)))
                                        .addOnSuccessListener(
                                                command1 -> {
                                                    db.collection("users").document(user.id)
                                                            .set(user).addOnSuccessListener(
                                                            dbaction ->{
                                                                //Tanto el create del usuario y el registro de datos en db
                                                                //Fue exitoso
                                                                Intent i = new Intent(this, ProfileActivity.class);
                                                                startActivity(i);
                                                            }
                                                    );
                                                }
                                        );

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                ).addOnFailureListener(
                        command -> {
                            Toast.makeText(this, command.getLocalizedMessage(), Toast.LENGTH_LONG)
                                    .show();
                        }
                );



                break;
            case R.id.editProfileImage:
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_CALLBACK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == GALLERY_CALLBACK){
            Uri uri = data.getData();
            path = UtilDomi.getPath(this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            profileRegImage.setImageBitmap(bitmap);
        }
    }
}