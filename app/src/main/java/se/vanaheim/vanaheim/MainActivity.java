package se.vanaheim.vanaheim;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

import se.vanaheim.vanaheim.data.ContractAreasDB;
import se.vanaheim.vanaheim.data.AreaDbHelper;
import se.vanaheim.vanaheim.data.HandleDatabases;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private GoogleMap mMap;
    private float zoom;
    private SupportMapFragment mapFragment;
    private static final int EDIT_REQUEST = 1;
    private HandleDatabases databases;

    private LatLng currentLocation;
    private static final String TAG = "MainActivity";
    private Toolbar mTopToolbar;
    private int objectTypeToDelete;

    private Boolean showInfProjects;
    private Boolean showEneProjects;
    private Boolean showPrmProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        try {
            databases = new HandleDatabases(this);
            mTopToolbar = findViewById(R.id.toolbar);
            mTopToolbar.setTitle("Vanaheim");
            //It is a material define android support widget. Use set- Otherwise an error will happen.
            setSupportActionBar(mTopToolbar);

            //Compatibility checking for the toolbar if api level is below 21
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mTopToolbar.setElevation(10.f);

            FloatingActionButton fab_objects_list = findViewById(R.id.fab_objects_list);
            FloatingActionButton fab_search_objects = findViewById(R.id.fab_search_button);


            fab_objects_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    double latitude = mMap.getCameraPosition().target.latitude;
                    double longitude = mMap.getCameraPosition().target.longitude;
                    LatLng currentLatLng = new LatLng(latitude, longitude);
                    float currentZoom = mMap.getCameraPosition().zoom;

                    Intent edit = new Intent(MainActivity.this, ChooseAreaViewActivity.class);
                    edit.putExtra("location", currentLatLng);
                    edit.putExtra("zoom", currentZoom);
                    MainActivity.this.startActivityForResult(edit, EDIT_REQUEST);
                }
            });

            fab_search_objects.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    double latitude = mMap.getCameraPosition().target.latitude;
                    double longitude = mMap.getCameraPosition().target.longitude;
                    LatLng currentLatLng = new LatLng(latitude, longitude);
                    float currentZoom = mMap.getCameraPosition().zoom;

                    Intent edit = new Intent(MainActivity.this, TextSearchForMainActivity.class);
                    edit.putExtra("location", currentLatLng);
                    edit.putExtra("zoom", currentZoom);
                    MainActivity.this.startActivityForResult(edit, EDIT_REQUEST);
                }
            });

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            // mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något gick fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        showInfProjects = true;
        showEneProjects = true;
        showPrmProjects = true;

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                // Check if the user exists inside the Firebase Authentication
                if (mFirebaseUser == null) {
                    Toast.makeText(MainActivity.this,
                            "Utloggad", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LoginScreenActivity.class);
                    i.putExtra("logout", true);
                    startActivity(i);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng sweden = new LatLng(59.334591, 18.063240);
        mMap = googleMap;
        if (currentLocation != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
        else
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sweden, 5));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                float zoom = mMap.getCameraPosition().zoom;
                Intent edit = new Intent(MainActivity.this, CreateAreaActivity.class);
                edit.putExtra("location", latLng);
                edit.putExtra("zoom", zoom);
                MainActivity.this.startActivityForResult(edit, EDIT_REQUEST);
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                String snippet = marker.getSnippet();
                float currentZoom = mMap.getCameraPosition().zoom;

                int objectType;

                if (snippet.contains("INF") == true)
                    objectType = 0;
                else if (snippet.contains("ENE") == true)
                    objectType = 1;
                else
                    objectType = 2;

                if (objectType == 2) {
                    Intent intent = new Intent(MainActivity.this, ObjectViewPRMActivity.class);
                    intent.putExtra("location", latLng);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("zoom", currentZoom);
                    MainActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                } else {
                    Intent intent = new Intent(MainActivity.this, ObjectViewActivity.class);
                    intent.putExtra("location", latLng);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("zoom", currentZoom);
                    MainActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                }
            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {

                LatLng latLng = marker.getPosition();
                String snippet = marker.getSnippet();


                if (snippet.contains("INF") == true)
                    objectTypeToDelete = 0;
                else if (snippet.contains("ENE") == true)
                    objectTypeToDelete = 1;
                else
                    objectTypeToDelete = 2;

                int objectType;

                if (snippet.contains("INF") == true)
                    objectType = 0;
                else if (snippet.contains("ENE") == true)
                    objectType = 1;
                else
                    objectType = 2;

                showDialog(latLng,objectType);
            }
        });

        mMap.clear();

        restoreAllMarkers();

        isWriteStoragePermissionGranted();
        isReadStoragePermissionGranted();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case (EDIT_REQUEST): {

                if (resultCode == Activity.RESULT_OK) {

                    MarkerOptions markerOptions = data.getParcelableExtra("markerOptions");

                    if (data.hasExtra("zoom"))
                        zoom = data.getFloatExtra("zoom", 0);

                    if (data.hasExtra("location"))
                        currentLocation = data.getParcelableExtra("location");
                    else {
                        drawMarker(markerOptions);
                        saveAreaMarker(markerOptions);
                        currentLocation = markerOptions.getPosition();
                    }
                }
            }
            break;
        }
    }

    public void saveAreaMarker(MarkerOptions markerOptions) {

        int objectTypeInteger = 0;
        String projectName;
        String objectType;
        LatLng latLng;
        Double latitude;
        Double longitude;

        projectName = markerOptions.getTitle();
        if (projectName.isEmpty() == true)
            projectName = "Unknown";

        objectType = markerOptions.getSnippet();

        if (objectType.contains("INF") == true)
            objectTypeInteger = 0;
        if (objectType.contains("ENE") == true)
            objectTypeInteger = 1;
        if (objectType.contains("PRM") == true)
            objectTypeInteger = 2;

        latLng = markerOptions.getPosition();
        latitude = latLng.latitude;
        longitude = latLng.longitude;

        databases.saveAreaMarker(projectName, objectTypeInteger, latitude, longitude);
        databases.createVaxlarOchSpar(latitude, longitude);
    }

    //Använder sig av Drawmarker för varje projekt som hittas.
    public void restoreAllMarkers() {
        AreaDbHelper mDbHelper = new AreaDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ContractAreasDB.AreaEntry._ID,
                ContractAreasDB.AreaEntry.COLUMN_AREA_NAME,
                ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE,
                ContractAreasDB.AreaEntry.COLUMN_LATITUDE,
                ContractAreasDB.AreaEntry.COLUMN_LONGITUDE};

        Cursor cursor = db.query(
                ContractAreasDB.AreaEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        try {
            int nameColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_AREA_NAME);
            int objectColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_OBJECT_TYPE);
            int latColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LATITUDE);
            int longColumnIndex = cursor.getColumnIndex(ContractAreasDB.AreaEntry.COLUMN_LONGITUDE);

            while (cursor.moveToNext()) {
                MarkerOptions markerOptions = new MarkerOptions();

                String name = cursor.getString(nameColumnIndex);

                int objectType = cursor.getInt(objectColumnIndex);
                String obType = String.valueOf(objectType);
                markerOptions.title(name);

                double lat = cursor.getDouble(latColumnIndex);
                double lon = cursor.getDouble(longColumnIndex);

                LatLng latLng = new LatLng(lat, lon);
                markerOptions.position(latLng);

                /*
                int numberOfObjects = 0;

                if (objectType == 0)
                    numberOfObjects = databases.countINFObjectsForAreaMarker(latLng);
                if (objectType == 1)
                    numberOfObjects = databases.countENEObjectsForAreaMarker(latLng);
                if (objectType == 2)
                    numberOfObjects = databases.countPRMLjudmatningForAreaMarker(latLng);
                */
                if (obType.contains("0") == true)
                    markerOptions.snippet("Delsystem: INF"/* + numberOfObjects*/);
                else if (obType.contains("1") == true)
                    markerOptions.snippet("Delsystem: ENE"/* + numberOfObjects*/);
                else if (obType.contains("2") == true)
                    markerOptions.snippet("Delsystem: PRM"/* + numberOfObjects*/);

                drawMarker(markerOptions);
            }
        } finally {
            cursor.close();
        }
    }

    private void drawMarker(MarkerOptions markerOptions) {

        String omradeType = markerOptions.getSnippet();

        switch (omradeType) {
            case "Delsystem: INF":
                if (showInfProjects == true)
                    createMarker(markerOptions, 1);
                break;
            case "Delsystem: ENE":
                if (showEneProjects == true)
                    createMarker(markerOptions, 2);
                break;
            case "Delsystem: PRM":
                if (showPrmProjects == true)
                    createMarker(markerOptions, 3);
                break;
        }
    }

    private void createMarker(MarkerOptions markerOptions, int classType) {

        int height = 100;
        int width = 100;
        Bitmap b;

        switch (classType) {
            case 1:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.project_inf_90px);
                break;
            case 2:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.project_ene_90px);
                break;
            case 3:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.project_prm_90px);
                break;
            default:
                b = BitmapFactory.decodeResource(getResources(), R.drawable.project_inf_90px);
                break;
        }

        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
        markerOptions.icon(smallMarkerIcon);

        markerOptions.anchor(0.2f, 0.2f);
        mMap.addMarker(markerOptions);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View newInfoWindow = getLayoutInflater().inflate(R.layout.info_window_marker, null);

                TextView tvTitle = newInfoWindow.findViewById(R.id.textViewTitle);
                TextView tvSnippet = newInfoWindow.findViewById(R.id.textViewSnippet);
                //TextView tvNumberOfObjects = newInfoWindow.findViewById(R.id.number_of_objects);

                tvTitle.setText(marker.getTitle());

                String snippet = marker.getSnippet();
                String[] snippetAndNumberOfObjects = snippet.split(",");
                tvSnippet.setText(snippetAndNumberOfObjects[0]);

                //tvNumberOfObjects.setText("Antal objekt: " + snippetAndNumberOfObjects[1]);

                return newInfoWindow;
            }
        });
    }

    public void showDialog(final LatLng latLng, final int objectType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Radera projekt");
        String message = "Är du säker på att du vill radera projektet?";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    databases.deleteAreaMarker(latLng);
                    databases.deleteObjectsFromAreaMarker(latLng, objectTypeToDelete);
                    zoom = mMap.getCameraPosition().zoom;
                    mapFragment.getMapAsync(MainActivity.this);

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Projekt raderat",
                            Toast.LENGTH_SHORT);
                    toast.show();

                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Något gick fel",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                currentLocation = latLng;
            }
        });
        builder.setNegativeButton("Nej", null);
        builder.setNeutralButton("Ändra namn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                double latitude = mMap.getCameraPosition().target.latitude;
                double longitude = mMap.getCameraPosition().target.longitude;
                LatLng currentLatLng = new LatLng(latitude, longitude);
                float currentZoom = mMap.getCameraPosition().zoom;

                Intent intent = new Intent(MainActivity.this, EditProjectNameActivity.class);
                intent.putExtra("sendBackLatLng", true);
                intent.putExtra("currentAreaPosition", currentLatLng);
                intent.putExtra("objectType", objectType);
                intent.putExtra("location",latLng);
                intent.putExtra("zoom", currentZoom);
                MainActivity.this.startActivityForResult(intent, EDIT_REQUEST);
            }
        });
        builder.show();
    }

    /**
     * Här tar vi in textsträngen som skrevs in i sökrutan. Texten behandlas sedan i
     * en Geocoder. Geocoden behandlar texten och gör om den till koordinater (om området finns
     * tillgängligt i Google Maps system).
     * <p>
     * Koordinaterna skickas sedan in till goToLocation() där vi förflyttas till rätt område.
     *
     * @param view View-elementet som ska behandlas
     * @throws IOException
     */
    public void geoLocate(View view, String content) throws IOException {

        hideSoftKeyboard(view);
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(content, 1);

        if (list.size() > 0) {
            Address add = list.get(0);
            String locality = add.getLocality();
            Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

            double lat = add.getLatitude();
            double lng = add.getLongitude();

            goToLocation(lat, lng, 12);
        } else
            Toast.makeText(this, "Hittade inget", Toast.LENGTH_SHORT).show();
    }

    /**
     * Här förflyttar vi kartan till rätt ställe med de koordinater som har angivits sen tidigare.
     *
     * @param lat  Latitud värdet
     * @param lng  Longitud värdet
     * @param zoom zoom nivån på kartan
     */
    private void goToLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate updateCameraAngle = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(updateCameraAngle);
    }

    /**
     * Här gömmer vi undan tangentbordet efter en sökning på nya området.
     *
     * @param view View-elementet som ska behandlas
     */
    private void hideSoftKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), R.string.permission_granted, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_main_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        final EditText searchEditText = (EditText)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint(R.string.search_here_for_google_maps);

        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String content = searchEditText.getText().toString();

                    if (!content.equals("")) {
                        try {
                            geoLocate(view, content);
                        } catch (IOException e) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Något gick fel",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }

                return false;
            }

        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.format_database:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Formatera hela databasen");
                String message = "Är du säker på att du vill formatera hela databasen?";
                builder.setMessage(message);
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setTitle("Formatera hela databasen");
                        String message = "Klicka på okej för att formatera databasen";
                        builder.setMessage(message);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    databases.onDownGradeAreaDatabase();
                                    databases.onDownGradeINFDatabase();
                                    databases.onDownGradeENEDatabase();
                                    databases.onDownGradePRMLjudmatningDatabase();
                                    databases.onDownGradePRMLjusmatningDatabase();
                                    databases.onDownGradeVaxlarOchSparDatabase();

                                } catch (Exception e) {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Något gick fel",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                                currentLocation = null;
                                mapFragment.getMapAsync(MainActivity.this);
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Formatering utförd",
                                        Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                        builder.setNegativeButton("Avbryt", null);
                        builder.show();
                    }
                });
                builder.setNegativeButton("Avbryt", null);
                builder.show();

                return true;
            case R.id.filter_shown_projects:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.filter_projects_dialog_window);

                Button filterButton = dialog.findViewById(R.id.done);
                final CheckBox cbInf = dialog.findViewById(R.id.cb_inf);
                final CheckBox cbEne = dialog.findViewById(R.id.cb_ene);
                final CheckBox cbPrm = dialog.findViewById(R.id.cb_prm);

                if (showInfProjects == true)
                    cbInf.setChecked(true);
                else
                    cbInf.setChecked(false);

                if (showEneProjects == true)
                    cbEne.setChecked(true);
                else
                    cbEne.setChecked(false);

                if (showPrmProjects == true)
                    cbPrm.setChecked(true);
                else
                    cbPrm.setChecked(false);

                filterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (cbInf.isChecked())
                            showInfProjects = true;
                        else
                            showInfProjects = false;

                        if (cbEne.isChecked())
                            showEneProjects = true;
                        else
                            showEneProjects = false;

                        if (cbPrm.isChecked())
                            showPrmProjects = true;
                        else
                            showPrmProjects = false;

                        dialog.dismiss();
                        currentLocation = null;
                        mapFragment.getMapAsync(MainActivity.this);
                    }
                });
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.show();

                return true;
            case R.id.search:
                return true;
            case R.id.logout:
                showDialogLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        showDialogLogout();
    }

    private void showDialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Logga ut");
        String message = "Vill du logga ut?";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mFirebaseAuth.getInstance().signOut();
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
/*
 * För att få menyn att funka i MainActiviy:
 * https://stackoverflow.com/questions/32298868/the-menu-does-not-appear-in-the-map-activity
 *
 * För att lösa vänster sida med items i menyn
 *https://www.youtube.com/watch?v=InF5tU_hecI
 *
 *För att bläddra mellan includes i layouts:
 * https://stackoverflow.com/questions/8589880/how-can-i-change-included-xml-layout-to-another-layout-on-java-code
 *
 * */