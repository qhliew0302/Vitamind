package com.example.vitamind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextView login;
    ImageView backButton;
    EditText name, email, password, conPassword;
    Button regBtn;
    String nameText, emailText, passwordText, conPasswordText;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register);

        //create hooks
        login = findViewById(R.id.LoginText);
        backButton = findViewById(R.id.backButton1);
        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        conPassword = findViewById(R.id.editPassword2);
        regBtn = findViewById(R.id.RegButton);

        // click back button to go to login page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data from edit text and convert them into String
                nameText = name.getText().toString();
                emailText = email.getText().toString();
                passwordText = password.getText().toString();
                conPasswordText = conPassword.getText().toString();

                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();

                // check the data before send to firebase
                // all fields must be filled
                if(nameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || conPasswordText.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                // password has to match with confirm password
                else if(!passwordText.equals(conPasswordText)) {
                    Toast.makeText(RegisterActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                // validate email
                else if (!isValidEmail(emailText)){
                    Toast.makeText(RegisterActivity.this, "Email format is not valid", Toast.LENGTH_SHORT).show();
                }
                else{

                    // save the email and password in firebase and make sure there is no existing email as the email entered
                    mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendUserToNextActivity();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        // click login text to go to login page
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    // go to login activity if the user created an account successfully
    private void sendUserToNextActivity() {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    // check whether it is a valid email format
    private boolean isValidEmail(String checkEmail) {
        String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pat = Pattern.compile(email_pattern);
        Matcher mat = pat.matcher(checkEmail);
        return mat.matches();
    }
}