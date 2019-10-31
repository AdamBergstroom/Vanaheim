package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import se.vanaheim.vanaheim.adapters.ObjectAdapter;
import se.vanaheim.vanaheim.data.HandleDatabases;
import se.vanaheim.vanaheim.models.Object;
import se.vanaheim.vanaheim.viewmodels.HandlePDF;

public class ObjectViewPRMActivity extends AppCompatActivity {

    private ArrayList<Object> ljudmatningList;
    private ArrayList<Object> ljusmatningList;
    private ArrayList<Object> rowListForLjusmatning;
    private ArrayList<Object> objectList;
    private ObjectAdapter adapter;
    private HandleDatabases databases;
    private HandlePDF pdfHandler;
    private LatLng latLng;
    private String content;
    private static final int EDIT_REQUEST = 1;
    private ListView listView;
    private int objectType;
    private CheckBox showReadyObjects;
    private CheckBox showNotReadyObjects;
    private FloatingActionButton fab_settings;
    private int readyCheckboxValue;
    private int readyNotCheckboxValue;
    private Toast toast;
    private RadioButton prmLjudmatningButton;
    private RadioButton prmLjusmatningButton;
    private int prmType;
    private boolean searchFailedLayoutIsOn;
    private String currentPlats;
    private int currentPosition;
    private int currentAreaPosition;
    private float currentZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.list_view_for_prm_objects);

        try {
            databases = new HandleDatabases(this);
            pdfHandler = new HandlePDF(this);
            content = "";
            readyCheckboxValue = 0;
            readyNotCheckboxValue = 0;
            searchFailedLayoutIsOn = false;

            ljudmatningList = new ArrayList<>();
            ljusmatningList = new ArrayList<>();

            latLng = getIntent().getParcelableExtra("location");
            objectType = getIntent().getIntExtra("objectType", 0);
            if (getIntent().hasExtra("currentAreaPosition"))
                currentAreaPosition = getIntent().getIntExtra("currentAreaPosition", 0);
            if (getIntent().hasExtra("zoom"))
                currentZoom = getIntent().getFloatExtra("zoom", 0);

            prmLjudmatningButton = findViewById(R.id.rb_prm_ljudmatning);
            prmLjusmatningButton = findViewById(R.id.rb_prm_ljusmatning);
            showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
            showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
            fab_settings = findViewById(R.id.fab_add_new_object);

            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
            establishUserInterface();
            createListView();

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.something_went_wrong,
                    Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void establishDatabase(int readyCheckbox, int readyNotCheckboxValue) {

        try {
            //rensar allt i ljusmatningslistan, eftersom dem fylls på nytt OM det finns nya objekt i vid hämtning från databasen.
            ljudmatningList = new ArrayList<>();
            ljusmatningList = new ArrayList<>();
            rowListForLjusmatning = new ArrayList<>();

            if (readyCheckbox == 1) {
                if (prmType == 0)
                    ljudmatningList = databases.recoverPRMLjudmatningObjects(latLng, true);
                else {
                    rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(latLng,content);

                    //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                    if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
                        ljusmatningList = new ArrayList<>();
                        for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                            Object currentObject = rowListForLjusmatning.get(i);
                            String searchingForStart = currentObject.getStartValue();
                            int ready = currentObject.getCompleted();

                            if (searchingForStart.contains("start") && ready == 1) {
                                Object newObject = new Object();
                                newObject.setPlats(currentObject.getPlats());
                                newObject.setCompleted(currentObject.getCompleted());
                                newObject.setPrmType(1);
                                ljusmatningList.add(newObject);
                            }
                        }
                    }
                }
            }
            if (readyNotCheckboxValue == 1) {
                if (prmType == 0)
                    ljudmatningList = databases.recoverNotReadyPRMLjudmatningObjects(latLng);
                else {
                    rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(latLng,content);

                    //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                    if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
                        ljusmatningList = new ArrayList<>();
                        for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                            Object currentObject = rowListForLjusmatning.get(i);
                            String searchingForStart = currentObject.getStartValue();
                            int ready = currentObject.getCompleted();

                            if (searchingForStart.contains("start") && ready == 0) {
                                Object newObject = new Object();
                                newObject.setPlats(currentObject.getPlats());
                                newObject.setCompleted(currentObject.getCompleted());
                                newObject.setPrmType(1);
                                ljusmatningList.add(newObject);
                            }
                        }
                    }
                }
            }
            if (readyCheckbox == 0 && readyNotCheckboxValue == 0) {
                if (prmType == 0)
                    ljudmatningList = databases.recoverPRMLjudmatningObjects(latLng, false);
                else {
                    rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(latLng, content);

                    //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                    if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
                        ljusmatningList = new ArrayList<>();
                        for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                            Object currentObject = rowListForLjusmatning.get(i);
                            String searchingForStart = currentObject.getStartValue();

                            if (searchingForStart.contains("start")) {
                                Object newObject = new Object();
                                newObject.setPlats(currentObject.getPlats());
                                newObject.setCompleted(currentObject.getCompleted());
                                newObject.setPrmType(1);
                                ljusmatningList.add(newObject);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.database_could_not_be_retrieved,
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void establishDatabaseWithContent(int readyCheckbox, int readyNotCheckboxValue) {

        try {
            //rensar allt i ljusmatningslistan, eftersom dem fylls på nytt OM det finns nya objekt i vid hämtning från databasen.
            ljudmatningList = new ArrayList<>();
            ljusmatningList = new ArrayList<>();
            rowListForLjusmatning = new ArrayList<>();

            if (readyCheckbox == 1) {
                if (prmType == 0)
                    ljudmatningList = databases.recoverPRMLjudmatningObjectsWithContentAndCheckedMarker(content);
                else {
                    rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(latLng,content);

                    //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                    if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
                        ljusmatningList = new ArrayList<>();
                        for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                            Object currentObject = rowListForLjusmatning.get(i);
                            String searchingForStart = currentObject.getStartValue();
                            int ready = currentObject.getCompleted();

                            if (searchingForStart.contains("start") && ready == 1) {
                                Object newObject = new Object();
                                newObject.setPlats(currentObject.getPlats());
                                newObject.setCompleted(currentObject.getCompleted());
                                newObject.setPrmType(1);
                                ljusmatningList.add(newObject);
                            }
                        }
                    }
                }
            }
            if (readyNotCheckboxValue == 1) {
                if (prmType == 0)
                    ljudmatningList = databases.recoverPRMLjudmatningObjectsWithContentAndNotCheckedMarker(content,latLng);
                else {
                    rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(latLng,content);

                    //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                    if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
                        ljusmatningList = new ArrayList<>();
                        for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                            Object currentObject = rowListForLjusmatning.get(i);
                            String searchingForStart = currentObject.getStartValue();
                            int ready = currentObject.getCompleted();

                            if (searchingForStart.contains("start") && ready == 0) {
                                Object newObject = new Object();
                                newObject.setPlats(currentObject.getPlats());
                                newObject.setCompleted(currentObject.getCompleted());
                                newObject.setPrmType(1);
                                ljusmatningList.add(newObject);
                            }
                        }
                    }
                }
            }
            if (readyCheckbox == 0 && readyNotCheckboxValue == 0) {
                if (prmType == 0)
                    ljudmatningList = databases.recoverPRMLjudmatningObjectsWithContent(content);
                else {
                    rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(latLng,content);

                    //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                    if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
                        ljusmatningList = new ArrayList<>();
                        for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                            Object currentObject = rowListForLjusmatning.get(i);
                            String searchingForStart = currentObject.getStartValue();

                            if (searchingForStart.contains("start")) {
                                Object newObject = new Object();
                                newObject.setPlats(currentObject.getPlats());
                                newObject.setCompleted(currentObject.getCompleted());
                                newObject.setPrmType(1);
                                ljusmatningList.add(newObject);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Databasen med sökvärde kunde inte hämtas",
                    Toast.LENGTH_SHORT);
            toast.show();
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


        if (prmLjudmatningButton.isChecked() == true)
            prmType = 0;
        else
            prmType = 1;


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
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                    createListView();
                }

                    /*
                    if (prmType == 1) {
                        if (content.equals("") || content.equals(" ")) {
                            //if (readyCheckboxValue == 1)
                                //checkForReadyObjectsInLjusmatningList();
                            //else {
                                establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                          //  }
                        }
                        //Content är inte tom
                        else {
                            //if (readyCheckboxValue == 1)
                               // checkForReadyObjectsInLjusmatningListWithContentAndCheckedMarker();
                           // else
                                establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                        }
                        createListView();
                    } else {
                        //if (content.equals("") || content.equals(" "))
                           // establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                       // else
                            establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                        //establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                        createListView();
                    }
                    */
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
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                    createListView();
                }
            /*
            if (prmType == 1) {
                if (content.equals("") || content.equals(" ")) {
                    if (readyCheckboxValue == 1)
                        checkForReadyObjectsInLjusmatningList();
                    else
                        establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                }
                //Content är inte tom
                else {
                    //if (readyCheckboxValue == 1)
                    // checkForReadyObjectsInLjusmatningListWithContentAndCheckedMarker();
                    //else
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                    //establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                    createListView();
                }
            } else {
                //if (content.equals("") || content.equals(" "))
                establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                //else
                // establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                //establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);
                createListView();
            }
            */

            }
        });

        prmLjudmatningButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    prmType = 0;
                    prmLjusmatningButton.setChecked(false);
                }

                //Search_failed layout är inte startad.
                if (searchFailedLayoutIsOn == false) {
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                    establishUserInterface();
                    createListView();
                }
            }
        });

        prmLjusmatningButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    prmType = 1;
                    prmLjudmatningButton.setChecked(false);
                }

                //Search_failed layout är inte startad.
                if (searchFailedLayoutIsOn == false) {
                    establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                    establishUserInterface();
                    createListView();
                }
            }
        });

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchFailedLayoutIsOn == false) {
                    Intent intent = new Intent(ObjectViewPRMActivity.this, CreateObjectPRMActivity.class);

                    intent.putExtra("location", latLng);
                    intent.putExtra("objectType", objectType);

                    int size = objectList.size();

                    intent.putExtra("nextSpotInArray", size);


                    if (prmLjudmatningButton.isChecked() == true)
                        intent.putExtra("prmType", 0);
                    else
                        intent.putExtra("prmType", 1);


                    if (showReadyObjects.isChecked() == true)
                        intent.putExtra("objectsReadyCheckbox", 1);
                    else
                        intent.putExtra("objectsReadyCheckbox", 0);

                    ObjectViewPRMActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                }
            }
        });
    }

    public void checkForReadyObjectsInLjusmatningList() {

        ljusmatningList = new ArrayList<>();
        rowListForLjusmatning = new ArrayList<>();

        rowListForLjusmatning = databases.recoverAllRowsFromPRMLjusmatning(latLng, true);

        //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
        if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
            ljusmatningList = new ArrayList<>();
            for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                Object currentObject = rowListForLjusmatning.get(i);
                String searchingForStart = currentObject.getStartValue();

                if (searchingForStart.contains("start")) {
                    Object newObject = new Object();
                    newObject.setPlats(currentObject.getPlats());
                    newObject.setCompleted(currentObject.getCompleted());
                    newObject.setPrmType(1);
                    ljusmatningList.add(newObject);
                }
            }
        }
    }

    public void checkForReadyObjectsInLjusmatningListWithContentAndCheckedMarker() {

        ljusmatningList = new ArrayList<>();
        rowListForLjusmatning = new ArrayList<>();

        rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(content);

        //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
        if (rowListForLjusmatning.isEmpty() == false || rowListForLjusmatning.size() != 0) {
            ljusmatningList = new ArrayList<>();
            for (int i = 0; i < rowListForLjusmatning.size(); i++) {
                Object currentObject = rowListForLjusmatning.get(i);
                String searchingForStart = currentObject.getStartValue();
                int ready = currentObject.getCompleted();

                if (searchingForStart.contains("start") && ready == 1) {
                    Object newObject = new Object();
                    newObject.setPlats(currentObject.getPlats());
                    newObject.setCompleted(currentObject.getCompleted());
                    newObject.setPrmType(1);
                    ljusmatningList.add(newObject);
                }
            }
        }
    }

    public void createListView() {

        try {
            if (prmLjudmatningButton.isChecked() == true)
                objectList = ljudmatningList;
            else
                objectList = ljusmatningList;

            listView = findViewById(R.id.listObjects);
            adapter = new ObjectAdapter(ObjectViewPRMActivity.this, objectList, objectType);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Object object = objectList.get(position);
                    int idRow = object.getIdRow();
                    String plats = object.getPlats();
                    Intent intent;

                    if (prmLjusmatningButton.isChecked() == true) {
                        intent = new Intent(ObjectViewPRMActivity.this, EditPRMLjusmatningActivity.class);
                        intent.putExtra("plats", plats);
                    } else
                        intent = new Intent(ObjectViewPRMActivity.this, EditObjectActivity.class);

                    intent.putExtra("idRow", idRow);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("LatLng", latLng);
                    intent.putExtra("content", content);
                    intent.putExtra("prmType", prmType);
                    intent.putExtra("positionInListView", position);

                    if (showReadyObjects.isChecked() == true)
                        intent.putExtra("objectsReadyCheckbox", 1);
                    else
                        intent.putExtra("objectsReadyCheckbox", 0);
                    ObjectViewPRMActivity.this.startActivityForResult(intent, EDIT_REQUEST);
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
                    "Listan kunde inte skapas",
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
                    Object currentObject = objectList.get(currentPosition);
                    String plats = currentObject.getPlats();
                    String idRow = Integer.toString(currentObject.getIdRow());

                    objectList.remove(currentPosition);
                    adapter.notifyDataSetChanged();

                    if (prmType == 0)
                        databases.deleteObjectFromAreaMarker(objectType, idRow);
                    else
                        databases.deleteObjectPRMLjusmatning(plats);

                    toast = Toast.makeText(getApplicationContext(),
                            "Objektet har raderats",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Något blev fel",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Avbryt", null);

        if (prmLjusmatningButton.isChecked() == true) {

            builder.setNeutralButton("Skapa en kopia", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        Object currentObject = objectList.get(currentPosition);
                        String plats = currentObject.getPlats();

                        Intent intent = new Intent(ObjectViewPRMActivity.this, CreateObjectPRMActivity.class);
                        intent.putExtra("location", latLng);
                        intent.putExtra("objectType", objectType);
                        intent.putExtra("prmType", 1);
                        intent.putExtra("plats", plats);

                        if (showReadyObjects.isChecked() == true)
                            intent.putExtra("objectsReadyCheckbox", 1);
                        else
                            intent.putExtra("objectsReadyCheckbox", 0);

                        boolean makeCopy = true;
                        intent.putExtra("makeCopy", makeCopy);

                        ObjectViewPRMActivity.this.startActivityForResult(intent, EDIT_REQUEST);

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något blev fel",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
        builder.show();
    }

    public void findViewsAndCheckRadioButtonsStatus(boolean prmLjudmatningRBIsChecked) {

        prmLjudmatningButton = findViewById(R.id.rb_prm_ljudmatning);
        prmLjusmatningButton = findViewById(R.id.rb_prm_ljusmatning);
        showReadyObjects = findViewById(R.id.checkbox_show_ready_objects);
        showNotReadyObjects = findViewById(R.id.checkbox_show_not_ready_objects);
        fab_settings = findViewById(R.id.fab_add_new_object);

        if (prmLjudmatningRBIsChecked == true) {
            prmLjudmatningButton.setChecked(true);
            prmLjusmatningButton.setChecked(false);
        } else {
            prmLjudmatningButton.setChecked(false);
            prmLjusmatningButton.setChecked(true);
        }
    }

    public void searchForContent() {
        try {
            //Content är tom. Hämta data och återställ listorna som vanligt.
            if (content.equals("") || content.equals(" ")) {

                boolean prmLjudmatningRBIsChecked = false;
                //Se till att rätt radioknapp är itryckt när ny layout skapas
                if (prmLjudmatningButton.isChecked() == true)
                    prmLjudmatningRBIsChecked = true;

                setContentView(R.layout.list_view_for_prm_objects);
                findViewsAndCheckRadioButtonsStatus(prmLjudmatningRBIsChecked);
                searchFailedLayoutIsOn = false;
                establishDatabase(readyCheckboxValue, readyNotCheckboxValue);
                establishUserInterface();
                createListView();
            }
            //Content är inte tom.
            else {
                establishDatabaseWithContent(readyCheckboxValue, readyNotCheckboxValue);

                if (prmType == 0) {
                    //Om listan är tom, visas ingen sökningsträff.
                    if (ljudmatningList.isEmpty() == true || ljudmatningList.size() == 0) {

                        boolean prmLjudmatningRBIsChecked = false;
                        //Se till att rätt radioknapp är itryckt när ny layout skapas
                        if (prmLjudmatningButton.isChecked() == true)
                            prmLjudmatningRBIsChecked = true;
                        searchFailedLayoutIsOn = true;
                        setContentView(R.layout.search_failed_prm_objects);
                        findViewsAndCheckRadioButtonsStatus(prmLjudmatningRBIsChecked);
                        establishUserInterface();
                        ljudmatningList = new ArrayList<>();
                        ljusmatningList = new ArrayList<>();
                    }
                    //Listan är inte tom
                    else {

                        boolean prmLjudmatningRBIsChecked = false;
                        //Se till att rätt radioknapp är itryckt när ny layout skapas
                        if (prmLjudmatningButton.isChecked() == true)
                            prmLjudmatningRBIsChecked = true;
                        searchFailedLayoutIsOn = false;
                        setContentView(R.layout.list_view_for_prm_objects);
                        findViewsAndCheckRadioButtonsStatus(prmLjudmatningRBIsChecked);
                        establishUserInterface();
                        //CrateListView ser efter vilken knapp som är ifylld, och skapar listan.
                        createListView();
                    }
                } else {

                    //Om listan är tom, visas ingen sökningsträff. Finns endast ett objekt visas 0 i listSize.
                    if (ljusmatningList.isEmpty() == true) {

                        boolean prmLjudmatningRBIsChecked = false;
                        //Se till att rätt radioknapp är itryckt när ny layout skapas
                        if (prmLjudmatningButton.isChecked() == true)
                            prmLjudmatningRBIsChecked = true;
                        searchFailedLayoutIsOn = true;
                        setContentView(R.layout.search_failed_prm_objects);
                        findViewsAndCheckRadioButtonsStatus(prmLjudmatningRBIsChecked);
                        establishUserInterface();
                        ljudmatningList = new ArrayList<>();
                        ljusmatningList = new ArrayList<>();
                    }
                    //Listan är inte tom
                    else {

                        boolean prmLjudmatningRBIsChecked = false;
                        //Se till att rätt radioknapp är itryckt när ny layout skapas
                        if (prmLjudmatningButton.isChecked() == true)
                            prmLjudmatningRBIsChecked = true;

                        searchFailedLayoutIsOn = false;
                        setContentView(R.layout.list_view_for_prm_objects);
                        findViewsAndCheckRadioButtonsStatus(prmLjudmatningRBIsChecked);
                        establishUserInterface();
                        //CrateListView ser efter vilken knapp som är ifylld, och skapar listan.
                        createListView();
                    }
                }

            }
            //***************************SLUT PÅ SÖKNINGEN******************
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något blev fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case (EDIT_REQUEST): {

                if (resultCode == Activity.RESULT_OK) {

                    try {
                        if (data.hasExtra("returnCheckboxValue"))
                            readyCheckboxValue = data.getIntExtra("returnCheckboxValue", 0);
                        if (data.hasExtra("objectType"))
                            objectType = data.getIntExtra("objectType", 0);
                        if (data.hasExtra("location"))
                            latLng = data.getParcelableExtra("location");
                        if (data.hasExtra("prmType"))
                            prmType = data.getIntExtra("prmType", 0);
                        if (data.hasExtra("content"))
                            content = data.getStringExtra("content");
                        if (data.hasExtra("plats"))
                            currentPlats = data.getStringExtra("plats");

                        int positionInListView = 0;
                        if (data.hasExtra("positionInListView"))
                            positionInListView = data.getIntExtra("positionInListView", 0);

                        boolean objectCreated = false;
                        if (data.hasExtra("objectCreated"))
                            objectCreated = data.getBooleanExtra("objectCreated", false);

                        boolean updateListView = false;
                        if (data.hasExtra("updateListView"))
                            updateListView = data.getBooleanExtra("updateListView", false);


                        if (objectCreated == true) {

                            if (prmType == 0) {
                                Object newObject = databases.recoverOnePRMLjudmatningObject(currentPlats, latLng);
                                objectList.add(newObject);
                                adapter.notifyDataSetChanged();
                            } else {
                                ArrayList<Object> currentRows = databases.recoverRowsFromOneObjectFromPRMLjusmatning(currentPlats);

                                //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                                if (currentRows.isEmpty() == false || currentRows.size() != 0) {
                                    for (int i = 0; i < currentRows.size(); i++) {
                                        Object currentObject = currentRows.get(i);
                                        String searchingForStart = currentObject.getStartValue();

                                        if (searchingForStart.contains("start")) {
                                            Object newObject = new Object();
                                            newObject.setPlats(currentObject.getPlats());
                                            newObject.setCompleted(currentObject.getCompleted());
                                            newObject.setPrmType(1);
                                            objectList.add(newObject);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        } else {
                            if (prmType == 0) {
                                //Objektet vi har uppdaterad

                                if (updateListView == true) {
                                    //Hämta data om objektet på nytt
                                    Object newObject = databases.recoverOnePRMLjudmatningObject(currentPlats, latLng);
                                    objectList.set(positionInListView, newObject);
                                    adapter.notifyDataSetChanged();
                                }
                            } else {   //Ljusmätning
                                if (updateListView == true) {
                                    ArrayList<Object> currentRows = databases.recoverRowsFromOneObjectFromPRMLjusmatning(currentPlats);

                                    //Objekt finns för Ljusmätning. Lägg i dem i listan för att visa i listview
                                    if (currentRows.isEmpty() == false || currentRows.size() != 0) {
                                        for (int i = 0; i < currentRows.size(); i++) {
                                            Object currentObject = currentRows.get(i);
                                            String searchingForStart = currentObject.getStartValue();

                                            if (searchingForStart.contains("start")) {
                                                Object newObject = new Object();
                                                newObject.setPlats(currentObject.getPlats());
                                                newObject.setCompleted(currentObject.getCompleted());
                                                newObject.setPrmType(1);
                                                objectList.set(positionInListView, newObject);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Något blev fel",
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
        inflater.inflate(R.menu.menu_prm_ljudmatning_pdf, menu);

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
            case R.id.send_pdf:
                try {
                    if (prmLjudmatningButton.isChecked() == true) {

                        if (searchFailedLayoutIsOn == false) {
                            Intent intent = new Intent(ObjectViewPRMActivity.this, EditPDFForPRMLjudmatningActivity.class);
                            intent.putExtra("objectType", objectType);
                            intent.putExtra("LatLng", latLng);
                            intent.putExtra("content", content);
                            intent.putExtra("prmType", prmType);

                            if (showReadyObjects.isChecked() == true)
                                intent.putExtra("objectsReadyCheckbox", 1);
                            else
                                intent.putExtra("objectsReadyCheckbox", 0);
                            ObjectViewPRMActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Finns inget att skriva ut", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Endast för Ljudmätning", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Något blev fel",
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
                    resultIntent.putExtra("zoom",currentZoom);
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

    public void onBackPressed() {
        try {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("objectType", objectType);
            resultIntent.putExtra("location", latLng);
            resultIntent.putExtra("currentAreaPosition", currentAreaPosition);
            resultIntent.putExtra("zoom",currentZoom);
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
