package se.vanaheim.vanaheim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreenActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnSignIn;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


        @Override
        protected void onStart() {
            super.onStart();
            mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_testing);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordId);
        btnSignIn = findViewById(R.id.btnSignIn);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                // Check if the user exists inside the Firebase Authentication
                if(mFirebaseUser != null){
                    Toast.makeText(LoginScreenActivity.this,
                            "You are logged in",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginScreenActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginScreenActivity.this,
                            "Access Denied",Toast.LENGTH_LONG).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if(emailValue.isEmpty()){
                    Toast.makeText(LoginScreenActivity.this,
                            "Please enter email",Toast.LENGTH_LONG).show();
                } else if(passwordValue.isEmpty()){
                    Toast.makeText(LoginScreenActivity.this,
                            "Please enter password",Toast.LENGTH_LONG).show();
                }else if(emailValue.isEmpty() && passwordValue.isEmpty()){
                    Toast.makeText(LoginScreenActivity.this,
                            "Fields Empty",Toast.LENGTH_LONG).show();
                }else if(!(emailValue.isEmpty() && passwordValue.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(emailValue,passwordValue)
                            .addOnCompleteListener(LoginScreenActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginScreenActivity.this,
                                        "Login Error, Please Login Again",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginScreenActivity.this,
                                        "Login Worked!",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginScreenActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginScreenActivity.this,
                            "Error Occurred!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}