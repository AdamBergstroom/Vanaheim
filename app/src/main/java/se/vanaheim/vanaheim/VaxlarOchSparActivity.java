package se.vanaheim.vanaheim;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import se.vanaheim.vanaheim.data.HandleDatabases;
import se.vanaheim.vanaheim.models.Object;

public class VaxlarOchSparActivity extends AppCompatActivity {

    private EditText vaxlar;
    private EditText sparkomponenter;
    private HandleDatabases databases;
    private LatLng latLng;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.spar_och_vaxlar_layout);

        latLng = getIntent().getParcelableExtra("location");

        latitude = latLng.latitude;
        longitude = latLng.longitude;

        sparkomponenter = findViewById(R.id.sparkomponenter_from_db);
        vaxlar = findViewById(R.id.vaxlar_from_db);

        databases = new HandleDatabases(this);

        boolean exists = databases.checkIfObjectExistsVaxlarOchSparComments(latitude,longitude);

        if(exists == false)
            databases.createVaxlarOchSpar(latitude,longitude);

        restoreValues();
    }

    public void restoreValues(){
        Object object = databases.recoverSparOchVaxlarComments(latitude,longitude);

        String sparkomponenterValue = object.getSparComments();
        String vaxelValue = object.getVaxlarComments();

        sparkomponenter.setText(sparkomponenterValue);
        vaxlar.setText(vaxelValue);
    }

    public void updateValues(){
        Object updatedObject = new Object();

        String sparkomponenterValue = sparkomponenter.getText().toString();
        String vaxelValue = vaxlar.getText().toString();

        updatedObject.setSparComments(sparkomponenterValue);
        updatedObject.setVaxlarComments(vaxelValue);

        databases.updateVaxlarOchSpar(updatedObject,latitude,longitude);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_edit_object, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_edite_check:

                try{
                    updateValues();

                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                }catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Objektet är borttaget",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

                return true;

            case android.R.id.home:
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

