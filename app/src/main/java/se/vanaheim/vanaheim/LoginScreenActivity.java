package se.vanaheim.vanaheim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreenActivity extends AppCompatActivity {

    private String passwordValue;
    private EditText passwordET;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        passwordET = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passwordValue = passwordET.getText().toString();

                if(passwordValue.equals("gavle#VH2006")) {
                    Intent intent = new Intent(LoginScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Fel lösenord",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
