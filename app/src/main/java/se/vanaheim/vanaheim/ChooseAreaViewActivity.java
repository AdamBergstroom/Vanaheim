package se.vanaheim.vanaheim;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import se.vanaheim.vanaheim.viewmodels.AreaTableAndViewPagerLayout;

public class ChooseAreaViewActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST = 1;
    private LatLng latLng;
    private Float zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.choose_area_view);

        Button showINFAreas = findViewById(R.id.show_infrastructure_areas);
        Button showENEAreas = findViewById(R.id.show_energy_areas);
        Button showPRMAreas = findViewById(R.id.show_PRM_areas);
        Button showAllAreas = findViewById(R.id.show_all_areas);
        Button showPropertyList = findViewById(R.id.property_list);

        latLng = getIntent().getParcelableExtra("location");
        zoom = getIntent().getFloatExtra("zoom", 0);

        showINFAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseAreaViewActivity.this, AreaViewActivity.class);
                intent.putExtra("objectType", 0);
                startActivity(intent);
            }
        });

        showENEAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAreaViewActivity.this, AreaViewActivity.class);
                intent.putExtra("objectType", 1);
                startActivity(intent);
            }
        });

        showPRMAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAreaViewActivity.this, AreaViewActivity.class);
                intent.putExtra("objectType", 2);
                startActivity(intent);
            }
        });

        showAllAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseAreaViewActivity.this, AreaTableAndViewPagerLayout.class));
            }
        });

        showPropertyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseAreaViewActivity.this, PropertyListViewActivity.class);
                ChooseAreaViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                try {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("location", latLng);
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

    @Override
    public void onBackPressed() {
        try {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("location", latLng);
            resultIntent.putExtra("zoom", zoom);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något blev fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
