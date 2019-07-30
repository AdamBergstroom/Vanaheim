package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import se.vanaheim.vanaheim.data.HandleDatabases;


public class ObjectViewActivity extends AppCompatActivity {

    private ArrayList<Object> objectList;
    private ArrayList<Object> newObjectList;
    private ObjectAdapter adapter;
    private HandleDatabases databases;
    private HandlePDF pdfHandler;
    private boolean searchFailedLayoutIsOn;
    private LatLng latLng;
    private String content;
    private static final int EDIT_REQUEST = 1;
    private ListView listView;
    private int objectType;
    private CheckBox showReadyObjects;
    private CheckBox showNotReadyObjects;
    private Button commentsForSparOchVaxlar;
    private FloatingActionButton fab_settings;
    private int readyCheckboxValue;
    private int readyNotCheckboxValue;
    private int setContentViewStatus;
    private Toast toast;
    private RadioButton prmLjudmatning;
    private RadioButton prmLjusmatning;
    private int currentPosition;
    private int currentAreaPosition;
    private float currentZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            databases = new HandleDatabases(this);
            pdfHandler = new HandlePDF(this);
            content = "";
            searchFailedLayoutIsOn = false;
            readyCheckboxValue = 0;
            readyNotCheckboxValue = 0;

            latLng = getIntent().getExtras().getParcelable("location");
            objectType = getIntent().getIntExtra("objectType", 0);
            if (getIntent().hasExtra("currentAreaPosition"))
                currentAreaPosition = getIntent().getIntExtra("currentAreaPosition", 0);
            if (getIntent().hasExtra("zoom"))
                currentZoom = getIntent().getFloatExtra("zoom", 0);

            setContentView(R.layout.list_view_for_objects);

            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
            showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
            showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
            commentsForSparOchVaxlar = findViewById(R.id.comments_for_spar_och_vaxlar);

            establishUserInterface();
            createListView();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något gick fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void establishDatabase(int readyCheckbox, int readyNotCheckboxValue) {

        if (readyCheckbox == 1) {
            if (objectType == 0)
                objectList = databases.recoverINFObjects(latLng, true);
            if (objectType == 1)
                objectList = databases.recoverENEObjects(latLng, true);
        }
        if (readyNotCheckboxValue == 1) {
            if (objectType == 0)
                objectList = databases.recoverNotReadyINFObjects(latLng);
            if (objectType == 1)
                objectList = databases.recoverNotReadyENEObjects(latLng);
        }
        if (readyCheckbox == 0 && readyNotCheckboxValue == 0) {
            if (objectType == 0)
                objectList = databases.recoverINFObjects(latLng, false);
            if (objectType == 1)
                objectList = databases.recoverENEObjects(latLng, false);
        }
    }

    public void establishDatabaseWithContent(int readyCheckboxValue, int readyNotCheckboxValue) {

        if (readyCheckboxValue == 1) {
            if (objectType == 0)
                objectList = databases.recoverINFObjectsWithContentAndCheckedMarker(content);
            else
                objectList = databases.recoverENEObjectsWithContentAndCheckedMarker(content);
        }
        if (readyNotCheckboxValue == 1) {
            if (objectType == 0)
                objectList = databases.recoverINFObjectsWithContentAndNotCheckedMarker(content);
            else
                objectList = databases.recoverENEObjectsWithContentAndNotCheckedMarker(content);
        }
        if (readyCheckboxValue == 0 && readyNotCheckboxValue == 0) {
            if (objectType == 0)
                objectList = databases.recoverINFObjectsWithContent(content);
            else
                objectList = databases.recoverENEObjectsWithContent(content);
        }
    }

    public void establishUserInterface() {

        if (readyNotCheckboxValue == 1) {
            showReadyObjects.setChecked(false);
            showNotReadyObjects.setChecked(true);
        } else
            showNotReadyObjects.setChecked(false);

        if (readyCheckboxValue == 1) {
            showNotReadyObjects.setChecked(false);
            showReadyObjects.setChecked(true);
        } else
            showReadyObjects.setChecked(false);


        showNotReadyObjects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    showReadyObjects.setChecked(false);
                    readyNotCheckboxValue = 1;
                } else {
                    readyNotCheckboxValue = 0;
                }

                //För att se till att search_failed layout inte skapar nya objekt när knappens status ändras.
                if (searchFailedLayoutIsOn == false) {

                    if (objectType == 0) {
                        if (content.equals("") || content.equals(" "))
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                        else
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                        //establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                        createListView();
                    } else {
                        if (content.equals("") || content.equals(" "))
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                        else
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                        //establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                        createListView();
                    }
                }
            }
        });

        showReadyObjects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    showNotReadyObjects.setChecked(false);
                    readyCheckboxValue = 1;
                } else {
                    readyCheckboxValue = 0;
                }

                //För att se till att search_failed layout inte skapar nya objekt när knappens status ändras.
                if (searchFailedLayoutIsOn == false) {

                    if (objectType == 0) {
                        if (content.equals("") || content.equals(" "))
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                        else
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);

                        //establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                        createListView();
                    } else {
                        if (content.equals("") || content.equals(" "))
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                        else
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);

                        //establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                        createListView();
                    }
                }
            }
        });

        if (objectType == 0) {
            commentsForSparOchVaxlar.setVisibility(View.VISIBLE);
            commentsForSparOchVaxlar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ObjectViewActivity.this, VaxlarOchSparActivity.class);
                    intent.putExtra("location", latLng);
                    ObjectViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                }
            });
        }

        if (searchFailedLayoutIsOn == false) {
            fab_settings = findViewById(R.id.fab_add_new_object);
            fab_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ObjectViewActivity.this, CreateObjectActivity.class);

                    intent.putExtra("location", latLng);
                    intent.putExtra("objectType", objectType);
                    if (showReadyObjects.isChecked() == true)
                        intent.putExtra("objectsReadyCheckbox", 1);
                    else
                        intent.putExtra("objectsReadyCheckbox", 0);

                    if (showNotReadyObjects.isChecked() == true)
                        intent.putExtra("objectsNotReadyCheckbox", 1);
                    else
                        intent.putExtra("objectsNotReadyCheckbox", 0);

                    ObjectViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                }
            });
        }
    }

    public void createListView() {
        try {
            listView = findViewById(R.id.listObjects);
            adapter = new ObjectAdapter(ObjectViewActivity.this, objectList, objectType);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Object object = objectList.get(position);
                    int idRow = object.getIdRow();

                    Intent intent = new Intent(ObjectViewActivity.this, EditObjectActivity.class);
                    intent.putExtra("idRow", idRow);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("LatLng", latLng);
                    intent.putExtra("positionInListView", position);
                    if (showReadyObjects.isChecked() == true)
                        intent.putExtra("objectsReadyCheckbox", 1);
                    else
                        intent.putExtra("objectsReadyCheckbox", 0);
                    intent.putExtra("content", content);
                    ObjectViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    currentPosition = position;
                    showDialog();
                    return true;
                }
            });
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något gick fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ta bort objekt");
        String message = "Är du säker på att du vill ta bort objektet?";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    Object object = objectList.get(currentPosition);
                    int idRow = object.getIdRow();
                    String idRowString = String.valueOf(idRow);

                    objectList.remove(currentPosition);
                    adapter.notifyDataSetChanged();

                    databases.deleteObjectFromAreaMarker(objectType, idRowString);
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Något gick fel",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    public void searchForContent() {

        //Content är tom. Hämta data och återställ listorna som vanligt.
        if (content.equals("") || content.equals(" ")) {
            try {
                setContentView(R.layout.list_view_for_objects);
                showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
                showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
                fab_settings = findViewById(R.id.fab_add_new_object);
                commentsForSparOchVaxlar = findViewById(R.id.comments_for_spar_och_vaxlar);

                searchFailedLayoutIsOn = false;

                if (readyCheckboxValue == 1) {
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                    showReadyObjects.setChecked(true);
                } else if (readyNotCheckboxValue == 1) {
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                    showNotReadyObjects.setChecked(true);
                } else
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);

                establishUserInterface();
                createListView();
            } catch (Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Något gick fel",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            //Content är inte tom.
            establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);

            if (objectType == 0) {

                //Om listan är tom, visas ingen sökningsträff.
                if (objectList.isEmpty() == true || objectList.size() == 0) {
                    try {
                        searchFailedLayoutIsOn = true;
                        setContentView(R.layout.search_failed_with_checkbox_with_ready_markers);
                        showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
                        showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
                        establishUserInterface();

                        if (readyCheckboxValue == 1)
                            showReadyObjects.setChecked(true);
                        else if (readyNotCheckboxValue == 1)
                            showNotReadyObjects.setChecked(true);
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                //Listan är inte tom
                else {
                    try {
                        searchFailedLayoutIsOn = false;
                        setContentView(R.layout.list_view_for_object_searched);
                        showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
                        showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
                        fab_settings = findViewById(R.id.fab_add_new_object);
                        commentsForSparOchVaxlar = findViewById(R.id.comments_for_spar_och_vaxlar);

                        establishUserInterface();

                        if (readyCheckboxValue == 1)
                            showReadyObjects.setChecked(true);
                        else if (readyNotCheckboxValue == 1)
                            showNotReadyObjects.setChecked(true);

                        createListView();

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            } else { //object 1

                //Om listan är tom, visas ingen sökningsträff.
                if (objectList.isEmpty() == true || objectList.size() == 0) {
                    try {
                        searchFailedLayoutIsOn = true;
                        setContentView(R.layout.search_failed_with_checkbox_with_ready_markers);
                        showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
                        showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
                        establishUserInterface();

                        if (readyCheckboxValue == 1)
                            showReadyObjects.setChecked(true);
                        else if (readyNotCheckboxValue == 1)
                            showNotReadyObjects.setChecked(true);

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                //Listan är inte tom
                else {
                    try {
                        searchFailedLayoutIsOn = false;
                        setContentView(R.layout.list_view_for_object_searched);
                        showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
                        showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
                        fab_settings = findViewById(R.id.fab_add_new_object);
                        commentsForSparOchVaxlar = findViewById(R.id.comments_for_spar_och_vaxlar);

                        establishUserInterface();

                        if (readyCheckboxValue == 1)
                            showReadyObjects.setChecked(true);
                        else if (readyNotCheckboxValue == 1)
                            showNotReadyObjects.setChecked(true);

                        createListView();
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        }
    }

    //***************************SLUT PÅ SÖKNINGEN******************

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case (EDIT_REQUEST): {

                if (resultCode == Activity.RESULT_OK) {

                    try {
                        if (data.hasExtra("returnReadyCheckboxValue"))
                            readyCheckboxValue = data.getIntExtra("returnReadyCheckboxValue", 0);

                        if (data.hasExtra("returnReadyCheckboxValue"))
                            readyNotCheckboxValue = data.getIntExtra("returnReadyCheckboxValue", 0);

                        if (data.hasExtra("objectType"))
                            objectType = data.getIntExtra("objectType", 0);

                        if (data.hasExtra("location"))
                            latLng = getIntent().getParcelableExtra("location");

                        int idRow = 0;
                        if (data.hasExtra("idRow"))
                            idRow = data.getIntExtra("idRow", 0);

                        String newStolpNummer = "";
                        if (data.hasExtra("newStolpNummer"))
                            newStolpNummer = data.getStringExtra("newStolpNummer");

                        String newKmNummer = "";
                        if (data.hasExtra("newKmNummer"))
                            newKmNummer = data.getStringExtra("newKmNummer");

                        int positionInListView = 0;
                        if (data.hasExtra("positionInListView"))
                            positionInListView = data.getIntExtra("positionInListView", 0);

                        if (data.hasExtra("content"))
                            content = data.getStringExtra("content");

                        String kmVarde = "";
                        String stolpNr = "";

                        switch (objectType) {
                            case 0:
                                kmVarde = data.getStringExtra("kmVarde");
                                break;
                            case 1:
                                stolpNr = data.getStringExtra("stolpNr");
                                break;
                        }

                        boolean objectCreated = false;
                        if (data.hasExtra("objectCreated"))
                            objectCreated = data.getBooleanExtra("objectCreated", false);


                        boolean updateListView = false;
                        if (data.hasExtra("updateListView"))
                            updateListView = data.getBooleanExtra("updateListView", false);

                        Object newObject;

                        if (objectCreated == true) {
                            //skapa nytt objekt i listview
                            switch (objectType) {
                                case 0:
                                    newObject = databases.recoverOneINFObject(kmVarde, latLng);
                                    objectList.add(newObject);
                                    adapter.notifyDataSetChanged();
                                    break;
                                case 1:
                                    newObject = databases.recoverOneENEObject(stolpNr, latLng);
                                    objectList.add(newObject);
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                        if (updateListView == true) {
                            //Uppdatera värdena i listview endast
                            if (objectType == 0) {
                                newObject = databases.recoverOneINFObject(idRow);
                                objectList.set(positionInListView, newObject);
                                adapter.notifyDataSetChanged();
                            } else {
                                newObject = databases.recoverOneENEObject(idRow);
                                objectList.set(positionInListView, newObject);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_object_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        final EditText searchEditText = (EditText)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint("Sök här...");

        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    try {
                        content = searchEditText.getText().toString();
                        searchForContent();
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något gick fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
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
            case R.id.email_support:
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"adam.bergstroom@hotmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Förklara problemet så tydligt som möjligt.");
                    startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Något gick fel",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            case R.id.send_pdf:
                try {
                    Intent intent = new Intent(ObjectViewActivity.this, EditPDFForINFAndENE.class);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("LatLng", latLng);
                    if (showReadyObjects.isChecked() == true)
                        intent.putExtra("objectsReadyCheckbox", 1);
                    else
                        intent.putExtra("objectsReadyCheckbox", 0);
                    if (showNotReadyObjects.isChecked() == true)
                        intent.putExtra("objectsNotReadyCheckbox", 1);
                    else
                        intent.putExtra("objectsNotReadyCheckbox", 0);

                    intent.putExtra("content", content);
                    ObjectViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Något gick fel",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            case android.R.id.home:
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}