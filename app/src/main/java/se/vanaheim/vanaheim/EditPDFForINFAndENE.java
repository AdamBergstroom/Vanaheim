package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import se.vanaheim.vanaheim.data.HandleDatabases;

public class EditPDFForINFAndENE extends AppCompatActivity {

    private EditPDFObject pdfObject;
    private HandlePDF pdfHandler;
    private HandleDatabases databases;
    private int readyCheckboxValue;
    private int notReadyCheckboxValue;
    private String content;
    private int objectType;
    private LatLng latLng;
    private ArrayList<Object> objectArrayList;
    final Calendar myCalendar = Calendar.getInstance();
    private int datumOrMatdatum;

    private EditText projektnummer;
    private EditText dokumentnummer;
    private EditText ansvarig;
    private EditText matdatum;
    private EditText bestallaren;
    private EditText referens;

    private EditText spar;
    private EditText datum;
    private EditText tid;
    private EditText vader;
    private EditText temperatur;
    private EditText kontrollanter;
    private EditText matinstrument;

    private EditText sparkomponenter;
    private EditText vaxlar;
    private EditText ovrigaKommentarer;
    private FloatingActionButton createPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            latLng = getIntent().getExtras().getParcelable("LatLng");
            objectType = getIntent().getIntExtra("objectType", 0);
            readyCheckboxValue = getIntent().getIntExtra("objectsReadyCheckbox", 0);
            notReadyCheckboxValue = getIntent().getIntExtra("objectsNotReadyCheckbox", 0);
            content = getIntent().getStringExtra("content");

            databases = new HandleDatabases(this);
            pdfHandler = new HandlePDF(this);
            pdfObject = new EditPDFObject();

            if (objectType == 0) {
                setContentView(R.layout.edit_pdf_inf_object);
                establishLayoutAttributesForINF();
            } else {
                setContentView(R.layout.edit_pdf_ene_object);
                establishLayoutAttributesForENE();
            }

            searchForContent();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något gick fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void establishLayoutAttributesForINF() {

        objectArrayList = new ArrayList<>();

        projektnummer = findViewById(R.id.projektnummer_value);
        dokumentnummer = findViewById(R.id.dokumentnummer_value);
        ansvarig = findViewById(R.id.ansvarig_value);
        matdatum = findViewById(R.id.matdatum_value);
        bestallaren = findViewById(R.id.bestallare_value);
        referens = findViewById(R.id.ref_value);

        spar = findViewById(R.id.spar_value);
        datum = findViewById(R.id.datum_value);
        tid = findViewById(R.id.tid_value);
        vader = findViewById(R.id.vader_value);
        temperatur = findViewById(R.id.temperatur_value);
        kontrollanter = findViewById(R.id.kontrollanter_value);
        matinstrument = findViewById(R.id.matinstrument_value);
        sparkomponenter = findViewById(R.id.sparkomponenter_values);
        vaxlar = findViewById(R.id.vaxlar_values);
        ovrigaKommentarer = findViewById(R.id.ovrig_kommentar);
        createPDF = findViewById(R.id.fab_create_pdf_file);


        if(objectType == 0) {
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;

            Object sparOchVaxlarValues = databases.recoverSparOchVaxlarComments(latitude, longitude);

            String sparkomponenterFromDB = sparOchVaxlarValues.getSparComments();
            String vaxlarFromDB = sparOchVaxlarValues.getVaxlarComments();

            sparkomponenter.setText(sparkomponenterFromDB);
            vaxlar.setText(vaxlarFromDB);
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        matdatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datumOrMatdatum = 0;
                new DatePickerDialog(EditPDFForINFAndENE.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datumOrMatdatum = 1;
                // TODO Auto-generated method stub
                new DatePickerDialog(EditPDFForINFAndENE.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        createPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast;

                ProgressBar progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);

                setValuesFromLayout();
                try {
                    pdfHandler.deletePDFFile();
                    pdfHandler.createPDFForINFObjects(objectArrayList, pdfObject);

                    toast = Toast.makeText(getApplicationContext(),
                            "PDF skapad",
                            Toast.LENGTH_LONG);
                    toast.show();

                    pdfHandler.sendPDF();

                    progressBar.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Kunde inte skapa PDF",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    public void establishLayoutAttributesForENE() {

        objectArrayList = new ArrayList<>();

        projektnummer = findViewById(R.id.projektnummer_value);
        dokumentnummer = findViewById(R.id.dokumentnummer_value);
        ansvarig = findViewById(R.id.ansvarig_value);
        matdatum = findViewById(R.id.matdatum_value);
        bestallaren = findViewById(R.id.bestallare_value);
        referens = findViewById(R.id.ref_value);

        spar = findViewById(R.id.spar_value);
        datum = findViewById(R.id.datum_value);
        tid = findViewById(R.id.tid_value);
        vader = findViewById(R.id.vader_value);
        temperatur = findViewById(R.id.temperatur_value);
        kontrollanter = findViewById(R.id.kontrollanter_value);
        matinstrument = findViewById(R.id.matinstrument_value);

        ovrigaKommentarer = findViewById(R.id.ovrig_kommentar);
        createPDF = findViewById(R.id.fab_create_pdf_file);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        matdatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datumOrMatdatum = 0;
                new DatePickerDialog(EditPDFForINFAndENE.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datumOrMatdatum = 1;
                // TODO Auto-generated method stub
                new DatePickerDialog(EditPDFForINFAndENE.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        createPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);

                Toast toast;

                setValuesFromLayout();
                try {
                    pdfHandler.deletePDFFile();
                    pdfHandler.createPDFForENEObjects(objectArrayList, pdfObject);

                    toast = Toast.makeText(getApplicationContext(),
                            "PDF skapad",
                            Toast.LENGTH_LONG);
                    toast.show();

                    pdfHandler.sendPDF();

                    progressBar.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Kunde inte skapa PDF",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        if (datumOrMatdatum == 1)
            datum.setText(sdf.format(myCalendar.getTime()));
        else
            matdatum.setText(sdf.format(myCalendar.getTime()));
    }

    public void setValuesFromLayout() {

        pdfObject.setProjektnummer(projektnummer.getText().toString());
        pdfObject.setDokumentnummer(dokumentnummer.getText().toString());
        pdfObject.setAnsvarig(ansvarig.getText().toString());
        pdfObject.setMatdatum(matdatum.getText().toString());
        pdfObject.setBestallare(bestallaren.getText().toString());
        pdfObject.setReferens(referens.getText().toString());

        pdfObject.setSpar(spar.getText().toString());
        pdfObject.setDatum(datum.getText().toString());
        pdfObject.setTid(tid.getText().toString());
        pdfObject.setVader(vader.getText().toString());
        pdfObject.setTemperatur(temperatur.getText().toString());
        pdfObject.setKontrollanter(kontrollanter.getText().toString());
        pdfObject.setMatinstrument(matinstrument.getText().toString());

        if (objectType == 0) {
            pdfObject.setSparkomponenter(sparkomponenter.getText().toString());
            pdfObject.setVaxlar(vaxlar.getText().toString());
        }
        pdfObject.setOvrigaKommentarer(ovrigaKommentarer.getText().toString());
    }

    public void searchForContent() {

        if (content.equals("") || content.equals(" ")) {
            if (readyCheckboxValue == 1) {

                if (objectType == 0)
                    objectArrayList = databases.recoverINFObjects(latLng, true);
                if (objectType == 1)
                    objectArrayList = databases.recoverENEObjects(latLng, true);
            }
            else if(notReadyCheckboxValue == 1){

                if (objectType == 0)
                    objectArrayList = databases.recoverNotReadyINFObjects(latLng);
                if (objectType == 1)
                    objectArrayList = databases.recoverNotReadyENEObjects(latLng);
            }
            else {
                if (objectType == 0)
                    objectArrayList = databases.recoverINFObjects(latLng, false);
                if (objectType == 1)
                    objectArrayList = databases.recoverENEObjects(latLng, false);
            }
        } else {
            //Nu kommer en sträng som ska sökas i
            if (readyCheckboxValue == 1) {
                if (objectType == 0)
                    objectArrayList = databases.recoverINFObjectsWithContentAndCheckedMarker(content);
                if (objectType == 1)
                    objectArrayList = databases.recoverENEObjectsWithContentAndCheckedMarker(content);
            }
            else if(notReadyCheckboxValue == 1){
                if (objectType == 0)
                    objectArrayList = databases.recoverINFObjectsWithContentAndNotCheckedMarker(content);
                if (objectType == 1)
                    objectArrayList = databases.recoverENEObjectsWithContentAndNotCheckedMarker(content);
            }
            else {
                if (objectType == 0)
                    objectArrayList = databases.recoverINFObjectsWithContent(content);
                if (objectType == 1)
                    objectArrayList = databases.recoverENEObjectsWithContent(content);
            }
        }
        //***************************SLUT PÅ SÖKNINGEN******************
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Ändringar sparas inte");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        try {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("returnCheckboxValue", readyCheckboxValue);
                            resultIntent.putExtra("returnNotReadyCheckboxValue", notReadyCheckboxValue);
                            resultIntent.putExtra("content", content);
                            resultIntent.putExtra("objectType", objectType);
                            resultIntent.putExtra("location", latLng);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
