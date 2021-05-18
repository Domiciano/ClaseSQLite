package edu.co.icesi.claseauth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_CALLBACK = 11;


    private EditText nameET, cityET, emailET, passwordET, repasswordET;
    private ImageView profileRegImage;
    private Button signupBtn;
    private Button editProfileImage;
    private TextView loginLink;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    //Path de la imagen de perfil
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

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