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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import se.vanaheim.vanaheim.data.HandleDatabases;

public class EditPDFForPRMLjusmatning extends AppCompatActivity {

    private HandlePDF pdfHandler;
    private String platsID;
    private LatLng latLng;
    private ArrayList<Object> ljusmatningList;
    private int numberOfColumns;
    private EditPDFObject pdfValuesForLjusmatning;
    private HandleDatabases databases;
    private Toast toast;
    private boolean rotateValue;
    final Calendar myCalendar = Calendar.getInstance();
    private int datumOrMatdatum;

    private EditText driftplats;
    private EditText projektnummer;
    private EditText dokumentnummer;
    private EditText ansvarig;
    private EditText matdatum;
    private EditText faststalldAv;

    private EditText plats;
    private EditText datum;
    private EditText tid;
    private EditText vader;
    private EditText temperatur;
    private EditText nederbord;
    private EditText soluppgang;
    private EditText solnedgang;
    private EditText deltagare;
    private EditText bestallare;
    private EditText bestallareRef;
    private EditText stationsklass;
    private EditText matinstrument;
    private EditText serienummer;
    private EditText kalibreringsId;
    private EditText kalibreringsdatum;
    private EditText nastaKalibrering;
    private EditText armatur;
    private EditText regelverk;
    private EditText hojdArmatur;
    private EditText matomrade;
    private EditText ovrigaKommentarer;
    private CheckBox rotateCheckBox;
    private FloatingActionButton createPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pdf_ljusmatning);
        //**********************************************************   FATTAS DATABAS ENDAST ***********************************************************************
        databases = new HandleDatabases(this);
        pdfHandler = new HandlePDF(this);
        pdfValuesForLjusmatning = new EditPDFObject();

        platsID = getIntent().getStringExtra("plats");
        latLng = getIntent().getParcelableExtra("LatLng");
        numberOfColumns = getIntent().getIntExtra("numberOfColumns", 0);

        ljusmatningList = databases.recoverRowsFromOneObjectFromPRMLjusmatning(platsID);

        recoverLayout();
    }

    public void saveValues() {

        pdfValuesForLjusmatning.setDriftplats(driftplats.getText().toString());
        pdfValuesForLjusmatning.setProjektnummer(projektnummer.getText().toString());
        pdfValuesForLjusmatning.setDokumentnummer(dokumentnummer.getText().toString());
        pdfValuesForLjusmatning.setAnsvarig(ansvarig.getText().toString());
        pdfValuesForLjusmatning.setMatdatum(matdatum.getText().toString());
        pdfValuesForLjusmatning.setFaststalldAv(faststalldAv.getText().toString());

        pdfValuesForLjusmatning.setPlats(plats.getText().toString());
        pdfValuesForLjusmatning.setDatum(datum.getText().toString());
        pdfValuesForLjusmatning.setTid(tid.getText().toString());
        pdfValuesForLjusmatning.setVader(vader.getText().toString());
        pdfValuesForLjusmatning.setTemperatur(temperatur.getText().toString());
        pdfValuesForLjusmatning.setNederbord(nederbord.getText().toString());
        pdfValuesForLjusmatning.setSoluppgang(soluppgang.getText().toString());
        pdfValuesForLjusmatning.setSolnedgang(solnedgang.getText().toString());
        pdfValuesForLjusmatning.setDeltagare(deltagare.getText().toString());
        pdfValuesForLjusmatning.setBestallare(bestallare.getText().toString());
        pdfValuesForLjusmatning.setBestallareRef(bestallareRef.getText().toString());
        pdfValuesForLjusmatning.setStationsklass(stationsklass.getText().toString());
        pdfValuesForLjusmatning.setMatinstrument(matinstrument.getText().toString());
        pdfValuesForLjusmatning.setSerienummer(serienummer.getText().toString());
        pdfValuesForLjusmatning.setKalibreringsId(kalibreringsId.getText().toString());
        pdfValuesForLjusmatning.setKalibreringsdatum(kalibreringsdatum.getText().toString());
        pdfValuesForLjusmatning.setNastaKalibrering(nastaKalibrering.getText().toString());
        pdfValuesForLjusmatning.setArmatur(armatur.getText().toString());
        pdfValuesForLjusmatning.setRegelverk(regelverk.getText().toString());
        pdfValuesForLjusmatning.setHojdArmatur(hojdArmatur.getText().toString());
        pdfValuesForLjusmatning.setMatomrade(matomrade.getText().toString());
        pdfValuesForLjusmatning.setOvrigaKommentarer(ovrigaKommentarer.getText().toString());
    }

    public void recoverLayout() {

        driftplats = findViewById(R.id.driftplats_value);
        projektnummer = findViewById(R.id.projektnummer_value);
        dokumentnummer = findViewById(R.id.dokumentnummer_value);
        ansvarig = findViewById(R.id.ansvarig_value);
        matdatum = findViewById(R.id.matdatum_value);
        faststalldAv = findViewById(R.id.faststalld_av_value);

        plats = findViewById(R.id.plats_value);
        datum = findViewById(R.id.datum_value);
        tid = findViewById(R.id.tid_value);
        vader = findViewById(R.id.vader_value);
        temperatur = findViewById(R.id.temperatur_value);
        nederbord = findViewById(R.id.nederbord_value);
        soluppgang = findViewById(R.id.soluppgang_value);
        solnedgang = findViewById(R.id.solnedgang_value);
        deltagare = findViewById(R.id.deltagare_value);
        bestallare = findViewById(R.id.bestallare_value);
        bestallareRef = findViewById(R.id.bestallare_ref_value);
        stationsklass = findViewById(R.id.stationsklass_value);
        matinstrument = findViewById(R.id.matinstrument_value);
        serienummer = findViewById(R.id.serienummer_value);
        kalibreringsId = findViewById(R.id.kalibrerings_id_value);
        kalibreringsdatum = findViewById(R.id.kalibreringsdatum_value);
        nastaKalibrering = findViewById(R.id.nasta_kalibrering_value);
        armatur = findViewById(R.id.armatur_value);
        regelverk = findViewById(R.id.regelverk_value);
        hojdArmatur = findViewById(R.id.hojd_armatur_value);
        matomrade = findViewById(R.id.matomrade_value);
        ovrigaKommentarer = findViewById(R.id.ovrig_kommentar);
        rotateCheckBox = findViewById(R.id.checkbox_rotate);
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
                new DatePickerDialog(EditPDFForPRMLjusmatning.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datumOrMatdatum = 1;
                new DatePickerDialog(EditPDFForPRMLjusmatning.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show(); }
        });

        rotateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                    rotateValue = true;
                else
                    rotateValue = false;
            }
        });

        createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);

                Toast toast;

                saveValues();

                try {
                    pdfHandler.deletePDFFile();
                    pdfHandler.createPDFForPRMLjusmatningObjects(ljusmatningList, numberOfColumns, pdfValuesForLjusmatning, rotateValue);

                    toast = Toast.makeText(getApplicationContext(),
                            "PDF skapad",
                            Toast.LENGTH_LONG);
                    toast.show();

                    pdfHandler.sendPDF();

                    progressBar.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    Toast.makeText(EditPDFForPRMLjusmatning.this, "Kunde inte skapa pdf", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Ändringar sparas inte");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        try {

                        } catch (Exception e) {
                            Toast.makeText(EditPDFForPRMLjusmatning.this, "Värdena gick inte att spara", Toast.LENGTH_LONG).show();
                        }
                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
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
