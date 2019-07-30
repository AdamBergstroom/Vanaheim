package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateAreaActivity extends AppCompatActivity {

    private LatLng latlng;
    private float zoom;
    private String objectSelected;
    private EditText objectNameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_object_in_marker);

        latlng = getIntent().getParcelableExtra("location");
        zoom = getIntent().getFloatExtra("zoom",0);

        objectNameId = findViewById(R.id.object_name_id);
        Spinner spinner = findViewById(R.id.spinnerWithObjects);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.object_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                objectChosen(parentView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        Button button = findViewById(R.id.createButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String objectNameIDText = objectNameId.getText().toString();

                if(objectNameIDText.equals("Unknown") || objectNameIDText.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Namn på projekt saknas",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }else {

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latlng);
                    markerOptions.title(objectNameId.getText().toString());
                    markerOptions.snippet("Område: " + objectSelected + ",");

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("markerOptions", markerOptions);
                    resultIntent.putExtra("zoom", zoom);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private void objectChosen(java.lang.Object position) {
        if (position.toString().contains("INF"))
            this.objectSelected = "INF";
        else if (position.toString().contains("ENE"))
            this.objectSelected = "ENE";
        else if (position.toString().contains("PRM"))
            this.objectSelected = "PRM";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                try {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("location", latlng);
                    resultIntent.putExtra("zoom", zoom);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Något blev fel",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

