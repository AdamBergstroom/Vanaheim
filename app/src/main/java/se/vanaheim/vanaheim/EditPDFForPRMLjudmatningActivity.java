package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import se.vanaheim.vanaheim.data.HandleDatabases;
import se.vanaheim.vanaheim.models.EditPDFObject;
import se.vanaheim.vanaheim.models.Object;
import se.vanaheim.vanaheim.viewmodels.HandlePDF;

public class EditPDFForPRMLjudmatningActivity extends AppCompatActivity {

    private int readyCheckboxValue;
    private int objectType;
    private LatLng latLng;
    private int prmType;
    private String content;
    private HandleDatabases databases;
    private HandlePDF pdfHandler;
    private EditPDFObject pdfObject;
    final Calendar myCalendar = Calendar.getInstance();
    private int datumOrMatdatum;

    private EditText projektnummer;
    private EditText dokumentnummer;
    private EditText ansvarig;
    private EditText matdatum;
    private EditText bestallaren;
    private EditText referens;

    private EditText datum;
    private EditText klockslag;
    private EditText vader;
    private EditText temperatur;
    private EditText matinstrument;
    private EditText kalibreringsinstrument;
    private EditText testsignal;
    private EditText signalkalla;
    private EditText bakgrundStipa;
    private EditText ovrigaKommentarer;

    private FloatingActionButton createPdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            setContentView(R.layout.edit_pdf_ljudmatning_object);

            databases = new HandleDatabases(this);
            pdfHandler = new HandlePDF(this);
            pdfObject = new EditPDFObject();

            readyCheckboxValue = getIntent().getIntExtra("objectsReadyCheckbox", 0);
            objectType = getIntent().getIntExtra("objectType", 0);
            latLng = getIntent().getParcelableExtra("LatLng");
            prmType = getIntent().getIntExtra("prmType", 0);
            content = getIntent().getStringExtra("content");

            recoverLayoutAttributes();
            setValuesFromLaoyout();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Något gick fel",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void recoverLayoutAttributes() {

        projektnummer = findViewById(R.id.projektnummer_value);
        dokumentnummer = findViewById(R.id.dokumentnummer_value);
        ansvarig = findViewById(R.id.ansvarig_value);
        matdatum = findViewById(R.id.matdatum_value);
        bestallaren = findViewById(R.id.bestallare_value);
        referens = findViewById(R.id.ref_value);

        datum = findViewById(R.id.datum_value);
        klockslag = findViewById(R.id.klockslag_value);
        vader = findViewById(R.id.vader_value);
        temperatur = findViewById(R.id.temperatur_value);
        matinstrument = findViewById(R.id.matinstrument_value);
        kalibreringsinstrument = findViewById(R.id.kalibreringsinstrument_value);
        testsignal = findViewById(R.id.testsignal_value);
        signalkalla = findViewById(R.id.signalkalla_value);
        bakgrundStipa = findViewById(R.id.bakgrund_stipa_value);
        ovrigaKommentarer = findViewById(R.id.ovrig_kommentar);

        createPdf = findViewById(R.id.fab_create_pdf_file);

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
                new DatePickerDialog(EditPDFForPRMLjudmatningActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datumOrMatdatum = 1;
                new DatePickerDialog(EditPDFForPRMLjudmatningActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);

                Toast toast;

                ArrayList<Object> ljudmatningslist;

                if (content.equals("")) {
                    if (readyCheckboxValue == 1) {
                        ljudmatningslist = databases.recoverPRMLjudmatningObjects(latLng, true);
                    } else {
                        ljudmatningslist = databases.recoverPRMLjudmatningObjects(latLng, false);
                    }
                } else {
                    if (readyCheckboxValue == 1) {
                        ljudmatningslist = databases.recoverPRMLjudmatningObjectsWithContentAndCheckedMarker(content);
                    } else {
                        ljudmatningslist = databases.recoverPRMLjudmatningObjectsWithContent(content);
                    }
                }
                setValuesFromLaoyout();
                try {
                    pdfHandler.deletePDFFile();
                    pdfHandler.createPDFForPRMLjudmatningObjects(ljudmatningslist, pdfObject);

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
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        if (datumOrMatdatum == 1)
            datum.setText(sdf.format(myCalendar.getTime()));
        else
            matdatum.setText(sdf.format(myCalendar.getTime()));
    }

    public void setValuesFromLaoyout() {

        pdfObject.setProjektnummer(projektnummer.getText().toString());
        pdfObject.setDokumentnummer(dokumentnummer.getText().toString());
        pdfObject.setAnsvarig(ansvarig.getText().toString());
        pdfObject.setMatdatum(matdatum.getText().toString());
        pdfObject.setBestallare(bestallaren.getText().toString());
        pdfObject.setReferens(referens.getText().toString());

        pdfObject.setDatum(datum.getText().toString());
        pdfObject.setTid(klockslag.getText().toString());
        pdfObject.setVader(vader.getText().toString());
        pdfObject.setTemperatur(temperatur.getText().toString());
        pdfObject.setMatinstrument(matinstrument.getText().toString());
        pdfObject.setKalibreringsinstrument(kalibreringsinstrument.getText().toString());
        pdfObject.setTestsignal(testsignal.getText().toString());
        pdfObject.setSignalkalla(signalkalla.getText().toString());
        pdfObject.setBakgrundStipa(bakgrundStipa.getText().toString());
        pdfObject.setOvrigaKommentarer(ovrigaKommentarer.getText().toString());
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
                            resultIntent.putExtra("objectType", objectType);
                            resultIntent.putExtra("location", latLng);
                            resultIntent.putExtra("prmType", prmType);
                            resultIntent.putExtra("content", content);
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
