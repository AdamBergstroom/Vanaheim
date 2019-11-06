package se.vanaheim.vanaheim;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import se.vanaheim.vanaheim.data.HandleDatabase;
import se.vanaheim.vanaheim.models.Object;

public class CreateObjectPRMActivity extends AppCompatActivity {

    private String lat;
    private String lng;
    private int returnCheckBoxValue;
    private Button createObjectButton;
    private HandleDatabase databases;
    private int objectType;
    private boolean makeCopy;
    private String copyOfplats;
    private int nextSpotInArray;

    //PRM
    private EditText etPlats;
    private String plats;
    private LatLng latLng;
    private int prmType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        objectType = getIntent().getIntExtra("objectType", 0);
        returnCheckBoxValue = getIntent().getIntExtra("objectsReadyCheckbox", 0);
        latLng = getIntent().getParcelableExtra("location");
        prmType = getIntent().getIntExtra("prmType", 0);
        if(getIntent().hasExtra("nextSpotInArray"))
            nextSpotInArray = getIntent().getIntExtra("nextSpotInArray", 0);

        if (getIntent().hasExtra("makeCopy"))
            makeCopy = getIntent().getBooleanExtra("makeCopy", false);
        if (getIntent().hasExtra("plats"))
            copyOfplats = getIntent().getStringExtra("plats");

        lat = String.valueOf(latLng.latitude);
        lng = String.valueOf(latLng.longitude);

        databases = new HandleDatabase(this);

        setContentView(R.layout.create_new_prm_object);
        etPlats = findViewById(R.id.prm_ljudmatning_object_plats);

        createObjectButton = findViewById(R.id.create_new_object_button);

        createObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                plats = etPlats.getText().toString();

                if (prmType == 0) {

                    boolean exists = databases.checkForProjectNameFromPRMLjudmatning(latLng, plats);

                    if (exists == false) {
                        databases.savePRMLjudmatningObject(plats, lat, lng);
                        try {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("returnCheckboxValue", returnCheckBoxValue);
                            resultIntent.putExtra("objectType", objectType);
                            resultIntent.putExtra("location", latLng);
                            resultIntent.putExtra("prmType", prmType);
                            resultIntent.putExtra("content", "");
                            resultIntent.putExtra("plats", plats);
                            resultIntent.putExtra("objectCreated", true);
                            resultIntent.putExtra("nextSpotInArray", nextSpotInArray);

                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Något blev fel", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Projektnamn upptaget", Toast.LENGTH_LONG).show();
                    }
                } else {
                    boolean exists = databases.checkForProjectNameFromPRMLjusmatning(latLng, plats);
                    //skapar 35 rader i tabellen, så att man inte behöver skapa ny rad varje gång i layout.
                    if (exists == false) {
                        if (makeCopy == false) {
                            for (int i = 0; i <= 35; i++) {
                                if (i == 0)
                                    databases.createRowForPRMLjusmatning(plats, lat, lng, 0, "start", 0);
                                else
                                    databases.createRowForPRMLjusmatning(plats, lat, lng, 0, "", i);
                            }
                            try {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("returnCheckboxValue", returnCheckBoxValue);
                                resultIntent.putExtra("objectType", objectType);
                                resultIntent.putExtra("location", latLng);
                                resultIntent.putExtra("prmType", prmType);
                                resultIntent.putExtra("content", "");
                                resultIntent.putExtra("plats", plats);
                                resultIntent.putExtra("objectCreated", true);
                                resultIntent.putExtra("nextSpotInArray", nextSpotInArray);

                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Något blev fel", Toast.LENGTH_LONG).show();
                            }
                        }
                        //make a copy of the object.
                        else {
                            ArrayList<Object> ljusmatningList = databases.recoverRowsFromOneObjectFromPRMLjusmatning(copyOfplats);

                            databases.makeACopyOfPRMLjusmatningObject(ljusmatningList, plats);
                            try {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("returnCheckboxValue", returnCheckBoxValue);
                                resultIntent.putExtra("objectType", objectType);
                                resultIntent.putExtra("location", latLng);
                                resultIntent.putExtra("prmType", prmType);
                                resultIntent.putExtra("content", "");
                                resultIntent.putExtra("plats", plats);
                                resultIntent.putExtra("objectCreated", true);
                                resultIntent.putExtra("nextSpotInArray", nextSpotInArray);

                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Något blev fel", Toast.LENGTH_LONG).show();
                            }

                            Toast.makeText(getApplicationContext(), "Kopia har skapats", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Projektnamn upptaget", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("returnCheckboxValue", returnCheckBoxValue);
                    resultIntent.putExtra("objectType", objectType);
                    resultIntent.putExtra("location", latLng);
                    resultIntent.putExtra("prmType", prmType);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Något blev fel", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }
}
