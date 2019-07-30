package se.vanaheim.vanaheim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.vanaheim.vanaheim.data.HandleDatabases;

public class EditObjectActivity extends AppCompatActivity {

    private int idRow;
    private int objectType;
    private int completed;
    private LatLng latLng;
    private int returnCheckboxValue;
    private HandleDatabases databases;
    private Object object;
    private EditText editor;
    private CheckBox completedOrNot;
    private EditText comments;
    private TextWatcher inputTextWatcherForENE;
    private TextWatcher inputTextWatcherForPRMLjudmatning;
    private Toast toast;
    private int positionInListView;

    //INF
    private EditText kmNummer;
    private EditText sparvidd;
    private EditText ralsforhojning;
    private EditText slipersavstand;
    private EditText sparavstand;
    private EditText friaRummet;

    //ENE
    private EditText stolpnummer;
    private Spinner spinnerForObjectENE;
    private EditText objectsChosenForENE;
    private EditText hojdAvKontakttrad; //A
    private EditText avvikelseISidled;
    private EditText hojdAvUtliggarror; //B
    private EditText upphojdAvTillsatsror;  //visar värdet av B-A

    //PRM Ljudmätning
    private EditText plats;
    private EditText objektForPRMLjudmatning;
    private EditText arvarde;
    private TextView borvarde; //A
    private TextView medelvarde; //B
    private TextView avvikelse; //visar värdet av B-A
    private EditText anmarkning;
    private String content;
    private ImageButton backArrow;
    private TextView rowText;
    private ImageButton forwardArrow;
    private int row;
    private List<String> splitedArvardeValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            idRow = getIntent().getIntExtra("idRow", 0);
            objectType = getIntent().getIntExtra("objectType", 0);
            latLng = getIntent().getParcelableExtra("LatLng");
            returnCheckboxValue = getIntent().getIntExtra("objectsReadyCheckbox", 0);
            content = getIntent().getStringExtra("content");
            if (getIntent().hasExtra("positionInListView"))
                positionInListView = getIntent().getIntExtra("positionInListView", 0);

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Värdena kunde inte hämtas",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        switch (objectType) {
            case 0:
                setContentView(R.layout.edit_inf_object);
                kmNummer = findViewById(R.id.km_number_column);
                sparvidd = findViewById(R.id.track_width_column);
                ralsforhojning = findViewById(R.id.track_level_column);
                slipersavstand = findViewById(R.id.sliper_track_distance_column);
                sparavstand = findViewById(R.id.track_distance_column);
                friaRummet = findViewById(R.id.free_room_column);
                comments = findViewById(R.id.comments_column);
                editor = findViewById(R.id.user_editor_column);
                completedOrNot = findViewById(R.id.checkbox_column);
                break;
            case 1:
                setContentView(R.layout.edit_ene_object);
                stolpnummer = findViewById(R.id.column_stolp_nr);
                spinnerForObjectENE = findViewById(R.id.spinner_object_for_ene);
                objectsChosenForENE = findViewById(R.id.valda_objekt_lista);
                hojdAvKontakttrad = findViewById(R.id.column_hojd_av_kontakttrad);
                avvikelseISidled = findViewById(R.id.column_avvikelse_i_sidled);
                hojdAvUtliggarror = findViewById(R.id.column_hojd_av_utliggarror);
                upphojdAvTillsatsror = findViewById(R.id.column_upphojd_av_tillsatsror);
                comments = findViewById(R.id.comments_column);
                editor = findViewById(R.id.user_editor_column);
                completedOrNot = findViewById(R.id.checkbox_column);
                break;
            case 2:
                setContentView(R.layout.edit_prm_ljudmatning_object);
                plats = findViewById(R.id.column_plats);
                objektForPRMLjudmatning = findViewById(R.id.column_objekt_prm_sound_measure);
                arvarde = findViewById(R.id.column_arvarde);
                borvarde = findViewById(R.id.column_borvarde);
                medelvarde = findViewById(R.id.column_medelvarde);
                avvikelse = findViewById(R.id.column_avvikelse);
                anmarkning = findViewById(R.id.column_anmarkning);
                editor = findViewById(R.id.user_editor_column);
                completedOrNot = findViewById(R.id.checkbox_column);
                backArrow = findViewById(R.id.back_arrow_row_id);
                rowText = findViewById(R.id.insert_row_id);
                forwardArrow = findViewById(R.id.forward_arrow_row_id);
                row = 1;

                break;
        }

        databases = new HandleDatabases(this);
        recoverObject();

        inputTextWatcherForENE = new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Boolean valueAISEmpty = hojdAvKontakttrad.getText().toString().isEmpty();
                Boolean valueBISEmpty = hojdAvUtliggarror.getText().toString().isEmpty();

                try {
                    if (valueAISEmpty == false && valueBISEmpty == false) {
                        //B-A
                        double value = Double.parseDouble(String.valueOf(hojdAvUtliggarror.getText()))-
                                Double.parseDouble(String.valueOf(hojdAvKontakttrad.getText()));
                        String valueInString = String.format("%.2f", value);
                        upphojdAvTillsatsror.setText(valueInString);
                    }
                } catch (NumberFormatException e) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Fel format ifyllt",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        inputTextWatcherForPRMLjudmatning = new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Boolean valueAISEmpty = borvarde.getText().toString().isEmpty();
                Boolean valueBISEmpty = medelvarde.getText().toString().isEmpty();
                if (valueAISEmpty == false && valueBISEmpty == false) {

                    try {
                        double value = Double.parseDouble(String.valueOf(medelvarde.getText())) -
                                Double.parseDouble(String.valueOf(borvarde.getText()));
                        String valueInString = String.format("%.2f", value);
                        avvikelse.setText(valueInString);
                    } catch (NumberFormatException e) {
                        toast = Toast.makeText(getApplicationContext(),
                                "Fel format ifyllt",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        };

        switch (objectType) {
            case 1:
                hojdAvKontakttrad.addTextChangedListener(inputTextWatcherForENE);
                hojdAvUtliggarror.addTextChangedListener(inputTextWatcherForENE);
                break;
            case 2:
                borvarde.addTextChangedListener(inputTextWatcherForPRMLjudmatning);
                medelvarde.addTextChangedListener(inputTextWatcherForPRMLjudmatning);
                break;
        }
    }

    public void recoverObject() {

        switch (objectType) {
            case 0:
                object = databases.recoverOneINFObject(idRow);

                kmNummer.setText(object.getKmNummer());
                sparvidd.setText(object.getSparvidd());
                ralsforhojning.setText(object.getRalsforhojning());
                slipersavstand.setText(object.getSlipersavstand());
                sparavstand.setText(object.getSparavstand());
                friaRummet.setText(object.getFriaRummet());
                comments.setText(object.getComments());
                editor.setText(object.getEditor());
                completed = object.getCompleted();

                if (completed == 1)
                    completedOrNot.setChecked(true);
                break;
            case 1:
                object = databases.recoverOneENEObject(idRow);

                stolpnummer.setText(object.getStolpnummer());
                objectsChosenForENE.setText(object.getObjektForENE());
                hojdAvKontakttrad.setText(object.getHojdAvKontakttrad());
                avvikelseISidled.setText(object.getAvvikelseISidled());
                hojdAvUtliggarror.setText(object.getHojdAvUtliggarror());
                upphojdAvTillsatsror.setText(object.getUpphojdAvTillsatsror());
                comments.setText(object.getComments());
                editor.setText(object.getEditor());
                completed = object.getCompleted();

                if (completed == 1)
                    completedOrNot.setChecked(true);

                List<String> list = new ArrayList<String>();
                list.add("");
                list.add("Kontaktledningsstolpen jordas med längsgående jordlina 212 mm2 AL-lina");
                list.add("J-jord jordas till S-räl med 1x75mm2 Al-lina");
                list.add("Tvärförbindning utförs med 1x75mm2 Al-lina");
                list.add("Mellan transformator jordas till S-Räl");
                list.add("Kontaktledningsstolpe jordas med 1x70 mm2 Al-lina till S-räl");
                list.add("Skåp jordas med 50 mm2 Cu-lina eller 70mm2 Al-lina till S-räl");
                list.add("Frånskiljare");
                list.add("Transformator jordas 1x70mm2 Al-lina till S-räl");
                list.add("Ventilavledare jordas 1x70mm2 Al-lina till S-räl");
                list.add("Bullerplank jordas med 1x70 mm2 Al-lina till kontaktledningsstolpen.");
                list.add("Sugtransformator jordas med 1x50 mm2 Cu-lina till tvärförbindningens jordningsplint");
                list.add("Bro jordas med samlingsjordledare med 1x70 mm2 Cu-lina till S-räl ");
                list.add("J-jord jordas till S-räl med 1x75mm2 Al-lina");
                list.add("Långsgående jordlina Cu 70 mm2");
                list.add("Stolpe - skyddsjordas till långsgående jordlina Cu 70 mm2");

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinnerForObjectENE.setAdapter(dataAdapter);

                spinnerForObjectENE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            String item = parent.getItemAtPosition(position).toString();
                            String currentChosenObjectsFromENE = objectsChosenForENE.getText().toString();
                            if (currentChosenObjectsFromENE.equals(""))
                                currentChosenObjectsFromENE += item;
                            else
                                currentChosenObjectsFromENE += "\n\n" + item;
                            objectsChosenForENE.setText(currentChosenObjectsFromENE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                break;
            case 2:
                object = databases.recoverOnePRMLjudmatningObject(idRow);

                plats.setText(object.getPlats());
                objektForPRMLjudmatning.setText(object.getObjektForPRMLjudmatning());

                recoverRowValues();

                double arvardeOneDouble = Double.valueOf(splitedArvardeValues.get(0));
                double arvardeTwoDouble = Double.valueOf(splitedArvardeValues.get(1));
                double arvardeThreeDouble = Double.valueOf(splitedArvardeValues.get(2));

                double medelvardeValue = (arvardeOneDouble + arvardeTwoDouble + arvardeThreeDouble) / 3;
                String newMedelvarde = String.format("%.2f", medelvardeValue);

                medelvarde.setText(newMedelvarde);

                double borvardeValue = Double.valueOf(object.getBorvarde());
                double avvikelseValue = medelvardeValue - borvardeValue;
                String newAvvikelse = String.format("%.2f", avvikelseValue);

                avvikelse.setText(newAvvikelse);

                anmarkning.setText(object.getAnmarkning());
                editor.setText(object.getEditor());
                completed = object.getCompleted();

                if (completed == 1)
                    completedOrNot.setChecked(true);


                if (row == 1)
                    backArrow.setVisibility(View.INVISIBLE);
                else
                    backArrow.setVisibility(View.VISIBLE);
                if (row == 3)
                    forwardArrow.setVisibility(View.INVISIBLE);
                else
                    forwardArrow.setVisibility(View.VISIBLE);

                forwardArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (row != 3) {
                                insertRowValues();
                                saveRowValues();
                                row++;
                                rowText.setText(String.valueOf(row));
                            }
                            if (row == 3)
                                forwardArrow.setVisibility(View.INVISIBLE);
                            else
                                forwardArrow.setVisibility(View.VISIBLE);

                            if (row > 1)
                                backArrow.setVisibility(View.VISIBLE);

                            recoverRowValues();
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Fel format ifyllt",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                backArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            if (row != 1) {
                                insertRowValues();
                                saveRowValues();
                                row--;
                                rowText.setText(String.valueOf(row));
                            }

                            if (row == 1)
                                backArrow.setVisibility(View.INVISIBLE);
                            else
                                backArrow.setVisibility(View.VISIBLE);

                            if (row < 3)
                                forwardArrow.setVisibility(View.VISIBLE);
                            recoverRowValues();
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Fel format ifyllt",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                break;
        }
    }

    public void insertRowValues() {

        double testArvardeValue;

        switch (row) {
            case 1:
                testArvardeValue = Double.valueOf(arvarde.getText().toString());
                splitedArvardeValues.set(0, String.valueOf(testArvardeValue));
                break;
            case 2:
                testArvardeValue = Double.valueOf(arvarde.getText().toString());
                splitedArvardeValues.set(1, String.valueOf(testArvardeValue));
                break;
            case 3:
                testArvardeValue = Double.valueOf(arvarde.getText().toString());
                splitedArvardeValues.set(2, String.valueOf(testArvardeValue));
                break;
        }


    }

    public void saveRowValues() {

        String newArvardeValue = "";

        for (int i = 0; i < splitedArvardeValues.size(); i++) {
            if (i != 2)
                newArvardeValue += splitedArvardeValues.get(i) + ",";
            else
                newArvardeValue += splitedArvardeValues.get(i);
        }

        databases.updateRowsPRMLjudmatningObject(idRow, newArvardeValue);
    }

    public void recoverRowValues() {

        object = databases.recoverOnePRMLjudmatningObject(idRow);

        String retrievedArvarde = object.getArvarde();

        splitedArvardeValues = new ArrayList<>();
        splitedArvardeValues = Arrays.asList(retrievedArvarde.split(","));

        switch (row) {
            case 1:
                arvarde.setText(splitedArvardeValues.get(0));
                break;
            case 2:
                arvarde.setText(splitedArvardeValues.get(1));
                break;
            case 3:
                arvarde.setText(splitedArvardeValues.get(2));
                break;
        }

        borvarde.setText(object.getBorvarde());

        double arvardeOneDouble = Double.valueOf(splitedArvardeValues.get(0));
        double arvardeTwoDouble = Double.valueOf(splitedArvardeValues.get(1));
        double arvardeThreeDouble = Double.valueOf(splitedArvardeValues.get(2));

        double medelvardeValue = (arvardeOneDouble + arvardeTwoDouble + arvardeThreeDouble) / 3;
        String newMedelvarde = String.format("%.2f", medelvardeValue);

        medelvarde.setText(newMedelvarde);

        double borvardeValue = Double.valueOf(object.getBorvarde());
        double avvikelseValue = medelvardeValue - borvardeValue;
        String newAvvikelse = String.format("%.2f", avvikelseValue);

        avvikelse.setText(newAvvikelse);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_edit_object, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent;

        switch (item.getItemId()) {
            case R.id.save_edite_check:

                String newKommentar;
                String newEditor;

                switch (objectType) {
                    case 0:
                        if (completedOrNot.isChecked() == true)
                            completed = 1;
                        else
                            completed = 0;

                        String newKmNummer = String.valueOf(kmNummer.getText());
                        String newSparvidd = String.valueOf(sparvidd.getText());
                        String newRalsforhojning = String.valueOf(ralsforhojning.getText());
                        String newSlipersavstand = String.valueOf(slipersavstand.getText());
                        String newSparavstand = String.valueOf(sparavstand.getText());
                        String newFriaRummet = String.valueOf(friaRummet.getText());
                        newKommentar = String.valueOf(comments.getText());
                        newEditor = String.valueOf(editor.getText());

                        databases.updateOneINFObject(idRow, newKmNummer, newSparvidd, newRalsforhojning,
                                newSlipersavstand, newSparavstand, newFriaRummet, newKommentar, newEditor, completed);

                        try {
                            resultIntent = new Intent();
                            resultIntent.putExtra("returnCheckboxValue", returnCheckboxValue);
                            resultIntent.putExtra("content", content);
                            resultIntent.putExtra("objectType", objectType);
                            resultIntent.putExtra("location", latLng);
                            resultIntent.putExtra("idRow", idRow);
                            resultIntent.putExtra("positionInListView", positionInListView);
                            resultIntent.putExtra("updateListView", true);

                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Något blev fel",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                    case 1:
                        if (completedOrNot.isChecked() == true)
                            completed = 1;
                        else
                            completed = 0;

                        String newStolpnummer = String.valueOf(stolpnummer.getText());
                        String newObjektForENE = String.valueOf(objectsChosenForENE.getText());
                        String newHojdAvKontakttrad = String.valueOf(hojdAvKontakttrad.getText());
                        String newAvvikelseISidled = String.valueOf(avvikelseISidled.getText());
                        String newHojdAvUtliggarror = String.valueOf(hojdAvUtliggarror.getText());
                        String newUpphojdAvTillsatsror = String.valueOf(upphojdAvTillsatsror.getText());
                        newKommentar = String.valueOf(comments.getText());
                        newEditor = String.valueOf(editor.getText());

                        databases.updateOneENEObject(idRow, newStolpnummer, newObjektForENE, newHojdAvKontakttrad,
                                newAvvikelseISidled, newHojdAvUtliggarror, newUpphojdAvTillsatsror, newKommentar,
                                newEditor, completed);

                        try {
                            resultIntent = new Intent();
                            resultIntent.putExtra("returnCheckboxValue", returnCheckboxValue);
                            resultIntent.putExtra("content", content);
                            resultIntent.putExtra("objectType", objectType);
                            resultIntent.putExtra("location", latLng);
                            resultIntent.putExtra("idRow", idRow);
                            resultIntent.putExtra("newStolpNummer", newStolpnummer);
                            resultIntent.putExtra("positionInListView", positionInListView);
                            resultIntent.putExtra("updateListView", true);

                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Något blev fel",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                    case 2:
                        if (completedOrNot.isChecked() == true)
                            completed = 1;
                        else
                            completed = 0;

                        try {
                            insertRowValues();
                            saveRowValues();

                            String newPlats = String.valueOf(plats.getText());
                            String newObjekt = String.valueOf(objektForPRMLjudmatning.getText());
                            String newMedelvarde = String.valueOf(medelvarde.getText());
                            String newAvvikelse = String.valueOf(avvikelse.getText());
                            String newAnmarkning = String.valueOf(anmarkning.getText());
                            newEditor = String.valueOf(editor.getText());

                            databases.updateOnePRMLjudmatningObject(idRow, newPlats, newObjekt,
                                    newMedelvarde, newAvvikelse, newAnmarkning, newEditor, completed);

                            try {
                                resultIntent = new Intent();
                                resultIntent.putExtra("returnCheckboxValue", returnCheckboxValue);
                                resultIntent.putExtra("content", content);
                                resultIntent.putExtra("objectType", objectType);
                                resultIntent.putExtra("location", latLng);
                                resultIntent.putExtra("plats", newPlats);
                                resultIntent.putExtra("positionInListView", positionInListView);
                                resultIntent.putExtra("updateListView", true);

                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            } catch (Exception e) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Något blev fel",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Fel format ifyllt för ärvarde",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                }
                return true;

            case android.R.id.home:
                try {
                    resultIntent = new Intent();
                    resultIntent.putExtra("returnCheckboxValue", returnCheckboxValue);
                    resultIntent.putExtra("content", content);
                    resultIntent.putExtra("objectType", objectType);
                    resultIntent.putExtra("location", latLng);
                    resultIntent.putExtra("positionInListView", positionInListView);
                    resultIntent.putExtra("updateListView", false);

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
