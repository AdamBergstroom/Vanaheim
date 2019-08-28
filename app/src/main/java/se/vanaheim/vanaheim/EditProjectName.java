package se.vanaheim.vanaheim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import se.vanaheim.vanaheim.data.HandleDatabases;

public class EditProjectName extends AppCompatActivity {

    private EditText projectName;
    private Button changeName;
    private LatLng latLng;
    private int objectType;
    private Boolean sendBackLatLng;
    private int currentAreaPosition;
    private float currentZoom;
    private HandleDatabases databases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_project_name);

        try {
            projectName = findViewById(R.id.edit_project_name_text);
            changeName = findViewById(R.id.edit_change_project_name);

            databases = new HandleDatabases(this);

            if (getIntent().hasExtra("sendBackLatLng"))
                sendBackLatLng = getIntent().getBooleanExtra("sendBackLatLng", false);
            if (getIntent().hasExtra("location"))
                latLng = getIntent().getParcelableExtra("location");
            if (getIntent().hasExtra("objectType"))
                objectType = getIntent().getIntExtra("objectType", 0);
            if (getIntent().hasExtra("currentAreaPosition"))
                currentAreaPosition = getIntent().getIntExtra("currentAreaPosition", 0);
            if (getIntent().hasExtra("zoom"))
                currentZoom = getIntent().getFloatExtra("zoom", 0);


                recoverAreaMarkerName();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något gick fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }


        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAreaName();

                if (sendBackLatLng == true) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("objectType", objectType);
                    resultIntent.putExtra("location", latLng);
                    resultIntent.putExtra("currentAreaPosition", currentAreaPosition);
                    resultIntent.putExtra("zoom", currentZoom);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("objectType", objectType);
                    resultIntent.putExtra("location", latLng);
                    resultIntent.putExtra("currentAreaPosition", currentAreaPosition);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Uppdaterat",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void recoverAreaMarkerName() {
        Area area = databases.recoverOneAreaMarker(objectType, latLng);
        projectName.setText(area.getAreaName());
    }

    private void changeAreaName() {
        String newName = projectName.getText().toString();
        databases.updateOneAreaName(latLng, objectType, newName);
    }

    public void onBackPressed() {

        if (sendBackLatLng == true) {
            try {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("objectType", objectType);
                resultIntent.putExtra("location", latLng);
                resultIntent.putExtra("currentAreaPosition", currentAreaPosition);
                resultIntent.putExtra("zoom", currentZoom);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } catch (Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Något gick fel",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            try {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("objectType", objectType);
                resultIntent.putExtra("location", latLng);
                resultIntent.putExtra("currentAreaPosition", currentAreaPosition);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } catch (Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Något gick fel",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (sendBackLatLng == true) {
                    try {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("objectType", objectType);
                        resultIntent.putExtra("location", latLng);
                        resultIntent.putExtra("currentAreaPosition", currentAreaPosition);
                        resultIntent.putExtra("zoom", currentZoom);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    try {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("objectType", objectType);
                        resultIntent.putExtra("location", latLng);
                        resultIntent.putExtra("currentAreaPosition", currentAreaPosition);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
