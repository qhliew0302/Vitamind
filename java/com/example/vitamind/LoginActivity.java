package com.example.vitamind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    EditText email, password;
    Button loginBtn;
    String emailText, passwordText, checkPassword;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        //create hooks
        signUp = findViewById(R.id.textSignUp);
        email = findViewById(R.id.emailAdd);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.LoginButton);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // click sign up text to go to register page
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // login by entering valid email and password
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // convert both email and password to string
                emailText = email.getText().toString();
                passwordText = password.getText().toString();

                // check if the users fill in both details
                if(emailText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter all the details needed!", Toast.LENGTH_SHORT).show();
                }
                else{
                    // authenticate the login details with the info stored in firebase
                    mAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                sendUserToNextActivity();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    // go to home activity if login successfully
    private void sendUserToNextActivity() {
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}