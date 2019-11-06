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

import com.google.android.gms.maps.model.LatLng;

import se.vanaheim.vanaheim.data.HandleDatabase;

public class CreateObjectActivity extends AppCompatActivity {

    private String lat;
    private String lng;
    private int returnReadyCheckBoxValue;
    private int returnNotReadyCheckBoxValue;
    private Button createObjectButton;
    private HandleDatabase databases;
    private int objectType;

    //INF
    private EditText etKmVarde;
    private String kmVarde;

    //ENE
    private EditText etStolpNr;
    private String stolpNr;

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
        returnReadyCheckBoxValue = getIntent().getIntExtra("objectsReadyCheckbox", 0);
        returnNotReadyCheckBoxValue = getIntent().getIntExtra("objectsNotReadyCheckbox", 0);
        latLng = getIntent().getParcelableExtra("location");

        lat = String.valueOf(latLng.latitude);
        lng = String.valueOf(latLng.longitude);

        databases = new HandleDatabase(this);

        if (objectType == 0)
            setContentView(R.layout.create_new_inf_object);
        if (objectType == 1)
            setContentView(R.layout.create_new_ene_object);

        createObjectButton = findViewById(R.id.create_new_object_button);

        createObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                switch (objectType) {
                    case 0:
                        etKmVarde = findViewById(R.id.first_value_for_object);
                        kmVarde = etKmVarde.getText().toString();
                        databases.saveINFObject(kmVarde, lat, lng);
                        break;
                    case 1:
                        etStolpNr = findViewById(R.id.first_value_for_object);
                        stolpNr = etStolpNr.getText().toString();
                        databases.saveENEObject(stolpNr, lat, lng);
                        break;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("returnReadyCheckboxValue", returnReadyCheckBoxValue);
                resultIntent.putExtra("returnNotReadyCheckboxValue", returnReadyCheckBoxValue);
                resultIntent.putExtra("objectType", objectType);
                resultIntent.putExtra("location", latLng);
                if (objectType == 0)
                    resultIntent.putExtra("kmVarde", kmVarde);
                else
                    resultIntent.putExtra("stolpNr", stolpNr);
                resultIntent.putExtra("objectCreated", true);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("returnReadyCheckboxValue", returnReadyCheckBoxValue);
                resultIntent.putExtra("returnNotReadyCheckboxValue", returnReadyCheckBoxValue);
                resultIntent.putExtra("objectType", objectType);
                resultIntent.putExtra("location", latLng);
                resultIntent.putExtra("objectCreated", false);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
        }
        return true;
    }
}