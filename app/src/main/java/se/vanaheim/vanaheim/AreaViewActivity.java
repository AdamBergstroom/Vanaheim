package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;

import se.vanaheim.vanaheim.adapters.AreaAdapter;
import se.vanaheim.vanaheim.data.HandleDatabase;
import se.vanaheim.vanaheim.models.Area;

public class AreaViewActivity extends AppCompatActivity {

    private ListView listView;
    private int objectType;
    private ArrayList<Area> areaArrayList;
    private AreaAdapter areaAdapter;
    private HandleDatabase databases;
    private int currentAreaPosition;
    private static final int EDIT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.list_view_for_areas);

        try {
            databases = new HandleDatabase(this);
            objectType = getIntent().getIntExtra("objectType", 0);
            areaArrayList = databases.recoverAreaMarkers(objectType);
            createListView();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.something_went_wrong,
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void createListView() {

        listView = findViewById(R.id.listAreas);
        areaAdapter = new AreaAdapter(this, areaArrayList);
        listView.setAdapter(areaAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Area areaObject = areaArrayList.get(position);
                double lat = areaObject.getLatitude();
                double lon = areaObject.getLongitude();
                LatLng latLng = new LatLng(lat, lon);
                int objectType = areaObject.getAreaObjectTypeNumber();

                if (objectType == 2) {
                    Intent intent = new Intent(AreaViewActivity.this, ObjectViewPRMActivity.class);
                    intent.putExtra("location", latLng);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("currentAreaPosition", position);
                    AreaViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);

                } else {
                    Intent intent = new Intent(AreaViewActivity.this, ObjectViewActivity.class);
                    intent.putExtra("location", latLng);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("currentAreaPosition", position);

                    AreaViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);

                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Area areaObject = areaArrayList.get(position);
                double lat = areaObject.getLatitude();
                double lon = areaObject.getLongitude();
                LatLng latLng = new LatLng(lat, lon);
                int objectType = areaObject.getAreaObjectTypeNumber();

                currentAreaPosition = position;

                showDialog(latLng,objectType,currentAreaPosition);

                return true;
            }
        });
    }

    public void showDialog(final LatLng latLng, final int objectType, final int currentAreaPosition) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ta bort område");
        String message = "Är du säker på att du vill ta bort det här området?";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Area selectedArea = areaArrayList.get(currentAreaPosition);

                areaArrayList.remove(currentAreaPosition);
                areaAdapter.notifyDataSetChanged();

                double lat = selectedArea.getLatitude();
                double lon = selectedArea.getLongitude();
                LatLng latLng = new LatLng(lat, lon);
                databases.deleteAreaMarker(latLng);
                databases.deleteObjectsFromAreaMarker(latLng, objectType);
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.setNeutralButton("Ändra namn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AreaViewActivity.this, EditProjectNameActivity.class);
                intent.putExtra("sendBackLatLng", false);
                intent.putExtra("objectType", objectType);
                intent.putExtra("location",latLng);
                intent.putExtra("currentAreaPosition",currentAreaPosition);
                AreaViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);
            }
        });
        builder.show();
    }

    public void searchForContent(String content) {

        if (content.equals("") || content.equals(" ")) {
            setContentView(R.layout.list_view_for_areas);
            areaArrayList = databases.recoverAreaMarkers(objectType);
            createListView();
        } else {
            //Nu kommer en sträng som ska sökas i
            if (objectType == 0) {
                areaArrayList = new ArrayList<>();
                areaArrayList = databases.recoverAreaMarkers(content, 0);

                if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                    //Ingen sökning hittades
                    setContentView(R.layout.search_failed);

                } else {
                    //Sökning fanns
                    setContentView(R.layout.list_view_for_areas);
                    createListView();
                }

            }
            if (objectType == 1) {
                areaArrayList = new ArrayList<>();
                areaArrayList = databases.recoverAreaMarkers(content, 1);

                if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                    //Ingen sökning hittades
                    setContentView(R.layout.search_failed);

                } else {
                    //Sökning fanns
                    setContentView(R.layout.list_view_for_areas);
                    createListView();
                }
            }
            if (objectType == 2) {
                areaArrayList = new ArrayList<>();
                areaArrayList = databases.recoverAreaMarkers(content, 2);

                if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                    //Ingen sökning hittades
                    setContentView(R.layout.search_failed);
                } else {
                    //Sökning fanns
                    setContentView(R.layout.list_view_for_areas);
                    createListView();
                }
            }
            //***************************SLUT PÅ SÖKNINGEN******************
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_area_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        final EditText searchEditText = (EditText)
                searchView.findViewById(R.id.search_src_text);
        //searchEditText.setMaxWidth(Integer.MAX_VALUE);  //Fungerar inte
        searchEditText.setHint("Sök här...");

        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = searchEditText.getText().toString();
                    searchForContent(content);
                }
                return false;
            }

        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case (EDIT_REQUEST): {

                if (resultCode == Activity.RESULT_OK) {

                    LatLng latLng = data.getParcelableExtra("location");
                    objectType = data.getIntExtra("objectType", 0);
                    int currentAreaPosition = data.getIntExtra("currentAreaPosition",0);

                    Area updatedArea = databases.recoverOneAreaMarker(objectType,latLng);
                    areaArrayList.set(currentAreaPosition,updatedArea);
                    areaAdapter.notifyDataSetChanged();
                }
            }
            break;
        }
    }

}
