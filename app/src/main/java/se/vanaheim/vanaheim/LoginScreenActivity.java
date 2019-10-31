package se.vanaheim.vanaheim;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreenActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnSignIn;
    private Boolean logout;
    private ProgressBar progressBarLogin;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.login_screen_testing);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordId);
        btnSignIn = findViewById(R.id.btnSignIn);
        progressBarLogin = findViewById(R.id.progressBarLogin);

        progressBarLogin.setVisibility(View.INVISIBLE);
        logout = false;

        if (getIntent().hasExtra("makeCopy"))
            logout = getIntent().getBooleanExtra("logout", false);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                // Check if the user exists inside the Firebase Authentication
                if (mFirebaseUser != null) {
                    Intent i = new Intent(LoginScreenActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    if (logout == true) {
                        Toast.makeText(LoginScreenActivity.this,
                                "Utloggad", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (emailValue.isEmpty() && passwordValue.isEmpty() ||
                emailValue.isEmpty() || passwordValue.isEmpty()) {
                    Toast.makeText(LoginScreenActivity.this,
                            "Fyll i alla fält ovan", Toast.LENGTH_SHORT).show();
                } else if (!(emailValue.isEmpty() && passwordValue.isEmpty())) {

                    progressBarLogin.setVisibility(View.VISIBLE);
                    hideSoftKeyboard(v);

                    mFirebaseAuth.signInWithEmailAndPassword(emailValue, passwordValue)
                            .addOnCompleteListener(LoginScreenActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(LoginScreenActivity.this,
                                                        "Åtkomst nekad. Se över mejladress och lösenord.", Toast.LENGTH_SHORT).show();
                                            }
                                            progressBarLogin.setVisibility(View.INVISIBLE);
                                        }
                                    });
                } else {
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginScreenActivity.this,
                            "Något gick fel", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void forgotPassword(View v) {

        final Dialog dialog = new Dialog(LoginScreenActivity.this);
        dialog.setContentView(R.layout.forgot_user_login_screen);

        Button dialogButton = dialog.findViewById(R.id.reset_password_button);
        final EditText email = dialog.findViewById(R.id.email);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                progressBarLogin.setVisibility(View.VISIBLE);
                resetPassword(email.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        hideSoftKeyboard(v);
    }

    private void resetPassword(final String email) {
        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginScreenActivity.this,
                                    "Återställning av lösenord har nu skickats till " + email, Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(LoginScreenActivity.this,
                                    email + " är inte kopplat till appen. Kontakta support för hjälp.", Toast.LENGTH_LONG).show();
                        }
                        progressBarLogin.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}