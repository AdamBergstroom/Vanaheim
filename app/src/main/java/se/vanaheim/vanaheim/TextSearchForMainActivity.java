package se.vanaheim.vanaheim;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;

import se.vanaheim.vanaheim.data.HandleDatabases;

public class TextSearchForMainActivity extends AppCompatActivity {

    private ArrayList<Area> areaArrayList;
    private ArrayList<Object> ljudmatningList;
    private ArrayList<Object> ljusmatningList;
    private ArrayList<Object> rowListForLjusmatning;
    private ArrayList<Object> objectArrayList;
    private LatLng latLng;
    private Float zoom;
    private int currentAreaPosition;
    private int currentObjectPosition;
    private int objectType;

    private static final int EDIT_REQUEST = 1;

    private ViewFlipper viewFlipper;
    private RadioButton rbAreas;
    private RadioButton rbObjects;
    private CheckBox cbINF;
    private CheckBox cbENE;
    private CheckBox cbPRM;

    private AreaAdapter areaAdapter;
    private ObjectAdapter objectAdapter;
    private ListView listViewForAreas;
    private ListView listViewForObjects;

    private HandleDatabases databases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        latLng = getIntent().getParcelableExtra("location");
        zoom = getIntent().getFloatExtra("zoom", 0);

        establishInterface();
        cbINF.setChecked(true);
    }

    public void establishRecoveryForDatabases() {
        databases = new HandleDatabases(this);
    }

    public void establishInterface() {

        viewFlipper = findViewById(R.id.view_flipper);

        rbAreas = findViewById(R.id.radio_button_areas);
        rbObjects = findViewById(R.id.radio_button_objects);
        cbINF = findViewById(R.id.checkBox_INF);
        cbENE = findViewById(R.id.checkBox_ENE);
        cbPRM = findViewById(R.id.checkBox_PRM);

        rbAreas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                    rbObjects.setChecked(false);
            }
        });

        rbObjects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    rbAreas.setChecked(false);

                    if (cbENE.isChecked() == true) {
                        cbENE.setChecked(true);
                        cbINF.setChecked(false);
                        cbPRM.setChecked(false);
                    } else if (cbINF.isChecked() == true) {
                        cbINF.setChecked(true);
                        cbPRM.setChecked(false);
                        cbENE.setChecked(false);
                    } else if (cbPRM.isChecked() == true) {
                        cbPRM.setChecked(true);
                        cbENE.setChecked(false);
                        cbINF.setChecked(false);
                    } else if (cbINF.isChecked() == true && cbENE.isChecked() == true || cbINF.isChecked() == true && cbPRM.isChecked() == true ||
                            cbENE.isChecked() == true && cbPRM.isChecked() == true || cbINF.isChecked() == true && cbENE.isChecked() == true && cbPRM.isChecked() == true) {
                        cbINF.setChecked(true);
                        cbENE.setChecked(false);
                        cbINF.setChecked(false);
                    }
                }
            }
        });


        cbINF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (rbAreas.isChecked() == true) {
                    if (isChecked == false) {
                        if (!cbENE.isChecked() && !cbPRM.isChecked())
                            cbINF.setChecked(true);
                        else
                            cbINF.setChecked(false);
                    }
                }
                if (rbObjects.isChecked() == true) {
                    if (rbObjects.isChecked() == true) {
                        if (isChecked == true) {
                            cbPRM.setChecked(false);
                            cbENE.setChecked(false);
                        }
                        if (isChecked == false) {
                            if (!cbENE.isChecked() && !cbPRM.isChecked())
                                cbINF.setChecked(true);
                            else
                                cbINF.setChecked(false);
                        }

                    }
                }
            }
        });

        cbENE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (rbAreas.isChecked() == true) {
                    if (isChecked == false) {
                        if (!cbINF.isChecked() && !cbPRM.isChecked())
                            cbENE.setChecked(true);
                        else
                            cbENE.setChecked(false);
                    }
                }
                if (rbObjects.isChecked() == true) {
                    if (rbObjects.isChecked() == true) {
                        if (isChecked == true) {
                            cbINF.setChecked(false);
                            cbPRM.setChecked(false);
                        }
                        if (isChecked == false) {
                            if (!cbINF.isChecked() && !cbPRM.isChecked())
                                cbENE.setChecked(true);
                            else
                                cbENE.setChecked(false);
                        }
                    }
                }
            }
        });

        cbPRM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (rbAreas.isChecked() == true) {
                    if (isChecked == false) {
                        if (!cbENE.isChecked() && !cbINF.isChecked())
                            cbPRM.setChecked(true);
                        else
                            cbPRM.setChecked(false);
                    }
                }
                if (rbObjects.isChecked() == true) {
                    if (isChecked == true) {
                        cbINF.setChecked(false);
                        cbENE.setChecked(false);
                    }
                    if (isChecked == false) {
                        if (!cbENE.isChecked() && !cbINF.isChecked())
                            cbPRM.setChecked(true);
                        else
                            cbPRM.setChecked(false);
                    }
                }
            }
        });

    }

    public void createListViewForAreas(final ArrayList newArrayList) {


        listViewForAreas = findViewById(R.id.list_areas_for_searching);
        areaAdapter = new AreaAdapter(TextSearchForMainActivity.this, newArrayList);
        listViewForAreas.setAdapter(areaAdapter);

        listViewForAreas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Area areaObject = (Area) newArrayList.get(position);
                double lat = areaObject.getLatitude();
                double lon = areaObject.getLongitude();
                LatLng latLng = new LatLng(lat, lon);
                int objectType = areaObject.getAreaObjectTypeNumber();

                Intent intent;
                if (objectType == 2)
                    intent = new Intent(TextSearchForMainActivity.this, ObjectViewPRMActivity.class);
                else
                    intent = new Intent(TextSearchForMainActivity.this, ObjectViewActivity.class);
                intent.putExtra("location", latLng);
                intent.putExtra("objectType", objectType);
                intent.putExtra("currentAreaPosition", position);
                startActivity(intent);
            }
        });

        listViewForAreas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                currentAreaPosition = position;
                showDialogForAreas();
                return true;
            }
        });
    }

    public void createListViewForObjects(final ArrayList newArrayList) {

        listViewForObjects = findViewById(R.id.list_objects_for_searching);
        objectAdapter = new ObjectAdapter(TextSearchForMainActivity.this, newArrayList, objectType);
        listViewForObjects.setAdapter(objectAdapter);

        listViewForObjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Object object = objectArrayList.get(position);
                int idRow = object.getIdRow();
                double lat = object.getLatitude();
                double lon = object.getLongitude();
                LatLng latLng = new LatLng(lat, lon);

                Intent intent;

                if (objectType == 2) {
                    int prmType = object.getPrmType();
                    String plats = object.getPlats();
                    if (prmType == 0) { //Ljudmätning layout activity
                        intent = new Intent(TextSearchForMainActivity.this, EditObjectActivity.class);
                        intent.putExtra("idRow", idRow);
                        intent.putExtra("objectType", objectType);
                        intent.putExtra("LatLng", latLng);
                        intent.putExtra("positionInListView", position);
                        TextSearchForMainActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                    } else { //Ljusmätning layout activity
                        intent = new Intent(TextSearchForMainActivity.this, EditPRMLjusmatning.class);
                        intent.putExtra("plats", plats);
                        intent.putExtra("objectType", objectType);
                        intent.putExtra("LatLng", latLng);
                        intent.putExtra("positionInListView", position);
                        TextSearchForMainActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                    }
                } else { //INF och ENE layout activity
                    intent = new Intent(TextSearchForMainActivity.this, EditObjectActivity.class);
                    intent.putExtra("idRow", idRow);
                    intent.putExtra("objectType", objectType);
                    intent.putExtra("LatLng", latLng);
                    intent.putExtra("positionInListView", position);
                    TextSearchForMainActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                }
            }
        });

        listViewForObjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                currentObjectPosition = position;
                showDialogForObjects();
                return true;
            }
        });
    }

    public void showDialogForAreas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ta bort projekt");
        String message = "Är du säker på att du vill ta bort det här projektet?";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Area selectedArea = areaArrayList.get(currentAreaPosition);

                areaArrayList.remove(currentAreaPosition);
                areaAdapter.notifyDataSetChanged();

                double lat = selectedArea.getLatitude();
                double lon = selectedArea.getLongitude();
                LatLng latLng = new LatLng(lat, lon);
                int objectType = selectedArea.getAreaObjectTypeNumber();
                databases.deleteAreaMarker(latLng);
                databases.deleteObjectsFromAreaMarker(latLng, objectType);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Projekt raderat",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    public void showDialogForObjects() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Ta bort objekt");
        String message = "Är du säker på att du vill ta bort objektet?";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Object object = objectArrayList.get(currentObjectPosition);
                int idRow = object.getIdRow();
                String idRowString = String.valueOf(idRow);
                String currentPlats = object.getPlats();
                int prmType = object.getPrmType();

                objectArrayList.remove(currentObjectPosition);
                objectAdapter.notifyDataSetChanged();

                try {
                    if (objectType == 2) {
                        if (prmType == 0)
                            databases.deleteObjectFromAreaMarker(objectType, idRowString);
                        else
                            databases.deleteObjectPRMLjusmatning(currentPlats);
                    }
                    else
                        databases.deleteObjectFromAreaMarker(objectType, idRowString);

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Objekt raderat",
                            Toast.LENGTH_LONG);
                    toast.show();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Objektet kunde inte raderas",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    public void searchForContent(String content) {

        try {
            if (content.equals("") || content.equals(" ")) {
                setContentView(R.layout.search_layout);
                establishInterface();
                cbINF.setChecked(true);
            } else {
                //Nu kommer en sträng som ska sökas i
                if (rbAreas.isChecked() == true) {

                    //INF område är endast ifyllt
                    if (cbINF.isChecked() == true && cbENE.isChecked() == false && cbPRM.isChecked() == false) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        areaArrayList = new ArrayList<Area>();
                        areaArrayList = databases.recoverAreaMarkers(content, 0);

                        if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbINF.setChecked(true);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_areas_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbINF.setChecked(true);
                            createListViewForAreas(areaArrayList);
                        }

                    }
                    //ENE område är endast ifyllt
                    if (cbINF.isChecked() == false && cbENE.isChecked() == true && cbPRM.isChecked() == false) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        areaArrayList = new ArrayList<Area>();
                        areaArrayList = databases.recoverAreaMarkers(content, 1);


                        if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbENE.setChecked(true);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_areas_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbENE.setChecked(true);
                            createListViewForAreas(areaArrayList);
                        }

                    }
                    //PRM område är endast ifyllt
                    if (cbINF.isChecked() == false && cbENE.isChecked() == false && cbPRM.isChecked() == true) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        areaArrayList = new ArrayList<Area>();
                        areaArrayList = databases.recoverAreaMarkers(content, 2);

                        if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbPRM.setChecked(true);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_areas_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbPRM.setChecked(true);
                            createListViewForAreas(areaArrayList);
                        }

                    }
                    //INF och ENE områdena är endast ifyllda
                    if (cbINF.isChecked() == true && cbENE.isChecked() == true && cbPRM.isChecked() == false) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        areaArrayList = new ArrayList<Area>();
                        areaArrayList = databases.recoverTwoAreaMarkers(content, 0, 1);

                        if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbINF.setChecked(true);
                            cbENE.setChecked(true);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_areas_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbINF.setChecked(true);
                            cbENE.setChecked(true);
                            createListViewForAreas(areaArrayList);
                        }

                    }
                    // INF och PRM områdena är endast ifyllda
                    if (cbINF.isChecked() == true && cbENE.isChecked() == false && cbPRM.isChecked() == true) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        areaArrayList = new ArrayList<Area>();
                        areaArrayList = databases.recoverTwoAreaMarkers(content, 0, 2);

                        if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbPRM.setChecked(true);
                            cbINF.setChecked(true);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_areas_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbPRM.setChecked(true);
                            cbINF.setChecked(true);
                            createListViewForAreas(areaArrayList);
                        }

                    }
                    //ENE och PRM områdena är endast ifyllda
                    if (cbINF.isChecked() == false && cbENE.isChecked() == true && cbPRM.isChecked() == true) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        areaArrayList = new ArrayList<Area>();
                        areaArrayList = databases.recoverTwoAreaMarkers(content, 1, 2);

                        if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbPRM.setChecked(true);
                            cbENE.setChecked(true);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_areas_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbPRM.setChecked(true);
                            cbENE.setChecked(true);
                            createListViewForAreas(areaArrayList);
                        }

                    }
                    // INF, ENE, och PRM områdena är ifyllda
                    if (cbINF.isChecked() == true && cbENE.isChecked() == true && cbPRM.isChecked() == true) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        areaArrayList = new ArrayList<Area>();
                        areaArrayList = databases.recoverAllAreaMarkers(content);

                        if (areaArrayList == null || areaArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbINF.setChecked(true);
                            cbPRM.setChecked(true);
                            cbENE.setChecked(true);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_areas_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbAreas.setChecked(true);
                            cbINF.setChecked(true);
                            cbPRM.setChecked(true);
                            cbENE.setChecked(true);
                            createListViewForAreas(areaArrayList);
                        }
                    }

                }
                //**************************    Söka på enbart objekt ******************************************************
                if (rbObjects.isChecked() == true) {

                    //INF objekt är ifyllt
                    if (cbINF.isChecked() == true && cbENE.isChecked() == false && cbPRM.isChecked() == false) {

                        establishRecoveryForDatabases();
                        establishInterface();
                        objectArrayList = new ArrayList<Object>();
                        objectArrayList = databases.recoverINFObjectsWithContent(content);

                        if (objectArrayList == null || objectArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbObjects.setChecked(true);
                            cbINF.setChecked(true);
                            cbENE.setChecked(false);
                            cbPRM.setChecked(false);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_objects_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbObjects.setChecked(true);
                            cbINF.setChecked(true);
                            cbENE.setChecked(false);
                            cbPRM.setChecked(false);
                            objectType = 0;
                            createListViewForObjects(objectArrayList);
                        }

                    }
                    // ENE objekt är ifyllt
                    if (cbINF.isChecked() == false && cbENE.isChecked() == true && cbPRM.isChecked() == false) {
                        establishRecoveryForDatabases();
                        establishInterface();
                        objectArrayList = new ArrayList<Object>();
                        objectArrayList = databases.recoverENEObjectsWithContent(content);


                        if (objectArrayList == null || objectArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbObjects.setChecked(true);
                            cbINF.setChecked(false);
                            cbENE.setChecked(true);
                            cbPRM.setChecked(false);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_objects_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbObjects.setChecked(true);
                            cbINF.setChecked(false);
                            cbENE.setChecked(true);
                            cbPRM.setChecked(false);
                            objectType = 1;
                            createListViewForObjects(objectArrayList);
                        }

                    }
                    //PRM objekt är ifyllt
                    if (cbINF.isChecked() == false && cbENE.isChecked() == false && cbPRM.isChecked() == true) {
                        establishRecoveryForDatabases();
                        ljudmatningList = new ArrayList<>();
                        ljusmatningList = new ArrayList<>();
                        rowListForLjusmatning = new ArrayList<>();

                        ljudmatningList = databases.recoverPRMLjudmatningObjectsWithContent(content);
                        rowListForLjusmatning = databases.recoverAllPRMLjusmatningObjectsWithContent(content);

                        //Objekt finns. Lägg i dem i listan för att visa i listview
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

                        //establishRecoveryForDatabases();
                        //establishInterface();
                        objectArrayList = new ArrayList<>();
                        objectArrayList.addAll(ljudmatningList);
                        objectArrayList.addAll(ljusmatningList);

                        if (objectArrayList == null || objectArrayList.isEmpty() == true) {
                            //Ingen sökning hittades
                            setContentView(R.layout.search_layout);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbObjects.setChecked(true);
                            cbPRM.setChecked(true);
                            cbINF.setChecked(false);
                            cbENE.setChecked(false);
                            viewFlipper.setDisplayedChild(1);

                        } else {
                            //Sökning fanns
                            setContentView(R.layout.list_view_objects_for_searching_menu);
                            establishRecoveryForDatabases();
                            establishInterface();
                            rbObjects.setChecked(true);
                            cbPRM.setChecked(true);
                            cbINF.setChecked(false);
                            cbENE.setChecked(false);
                            objectType = 2;
                            createListViewForObjects(objectArrayList);
                        }
                    }

                }

                //***************************SLUT PÅ SÖKNINGEN******************
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något gick fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_text_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        final EditText searchEditText = (EditText)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //searchEditText.setMaxWidth(Integer.MAX_VALUE);  //Fungerar inte
        searchEditText.setHint("Sök här");

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
        };
    }
}

/*
 * How to use searchView correctly:
 * https://abhiandroid.com/ui/searchview
 *
 * För att bläddra mellan includes i layouts:
 * https://stackoverflow.com/questions/8589880/how-can-i-change-included-xml-layout-to-another-layout-on-java-code
 *
 * */
