package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import se.vanaheim.vanaheim.data.HandleDatabases;
import se.vanaheim.vanaheim.models.Object;

public class EditPRMLjusmatningActivity extends AppCompatActivity {

    private String plats;
    private LatLng latLng;
    private int currentRowIdInTabel;
    private int objectType;
    private String content;
    private int prmType;
    private int returnCheckboxValue;
    private HandleDatabases databases;
    private ArrayList<Object> ljusmatningList;
    private Toast toast;
    private int swapToLayer;
    private boolean changeLayoutOrNot;
    private boolean saveWentThrough;
    private int positionInListView;
    private static final int EDIT_REQUEST = 1;

    //****** Columns **********
    private EditText column1;
    private EditText column2;
    private EditText column3;
    private EditText column4;
    private EditText column5;
    private EditText column6;
    private EditText column7;
    private EditText column8;
    private EditText column9;
    private EditText column10;
    private EditText column11;
    private EditText column12;
    private EditText column13;
    private EditText column14;
    private CheckBox fardig;
    private Button infoColumns;

    //***Rows******
    private EditText columnWidthValue;
    private EditText value1;
    private EditText value2;
    private EditText value3;
    private EditText value4;
    private EditText value5;
    private EditText value6;
    private EditText value7;
    private EditText value8;
    private EditText value9;
    private EditText value10;
    private EditText value11;
    private EditText value12;
    private EditText value13;
    private EditText value14;
    private TextView insertRowId;
    private ImageButton backArrowRowId;
    private ImageButton forwardArrowRowId;
    private ImageButton swapLayoutButton;
    private Button infoRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getIntent().hasExtra("plats"))
            plats = getIntent().getStringExtra("plats");
        if (getIntent().hasExtra("LatLng"))
            latLng = getIntent().getParcelableExtra("LatLng");
        if (getIntent().hasExtra("objectType"))
            objectType = getIntent().getIntExtra("objectType", 0);
        if (getIntent().hasExtra("objectsReadyCheckbox"))
            returnCheckboxValue = getIntent().getIntExtra("objectsReadyCheckbox", 0);
        if (getIntent().hasExtra("content"))
            content = getIntent().getStringExtra("content");
        if (getIntent().hasExtra("prmType"))
            prmType = getIntent().getIntExtra("prmType", 0);
        if (getIntent().hasExtra("positionInListView"))
            positionInListView = getIntent().getIntExtra("positionInListView",0);

        databases = new HandleDatabases(this);
        changeLayoutOrNot = true;
        saveWentThrough = true;
        currentRowIdInTabel = 1;

        ljusmatningList = databases.recoverRowsFromOneObjectFromPRMLjusmatning(plats);
        Object firstRow = ljusmatningList.get(0);
        String firstColumn = firstRow.getFirstValue();

        if (firstColumn == "") {
            swapToLayer = 0;
            setContentView(R.layout.edit_prm_ljusmatning_columns);
            establishEditColumns();
            setValuesInEditTextColumns();
        } else {
            swapToLayer = 1;
            setContentView(R.layout.edit_prm_ljusmatning_rows);
            establishEditRows();
            setValuesInEditTextRows();
        }
    }

    public void establishEditColumns() {

        column1 = findViewById(R.id.column_edit_text_1);
        column2 = findViewById(R.id.column_edit_text_2);
        column3 = findViewById(R.id.column_edit_text_3);
        column4 = findViewById(R.id.column_edit_text_4);
        column5 = findViewById(R.id.column_edit_text_5);
        column6 = findViewById(R.id.column_edit_text_6);
        column7 = findViewById(R.id.column_edit_text_7);
        column8 = findViewById(R.id.column_edit_text_8);
        column9 = findViewById(R.id.column_edit_text_9);
        column10 = findViewById(R.id.column_edit_text_10);
        column11 = findViewById(R.id.column_edit_text_11);
        column12 = findViewById(R.id.column_edit_text_12);
        column13 = findViewById(R.id.column_edit_text_13);
        column14 = findViewById(R.id.column_edit_text_14);
        fardig = findViewById(R.id.checkbox_column);
        infoColumns = findViewById(R.id.image_view_columns);


        infoColumns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(EditPRMLjusmatningActivity.this);
                LayoutInflater factory = LayoutInflater.from(EditPRMLjusmatningActivity.this);
                final View view = factory.inflate(R.layout.imageview_columns, null);
                alertadd.setView(view);

                alertadd.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                    }
                });
                alertadd.show();
            }
        });

        swapLayoutButton = findViewById(R.id.swap_layout_button);

        swapLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swapToLayer == 0) {
                    saveValuesFromEditTextInColumns();
                    if (changeLayoutOrNot != false) {
                        setContentView(R.layout.edit_prm_ljusmatning_rows);
                        establishEditRows();
                        setValuesInEditTextRows();
                        swapToLayer = 1;
                    }
                } else {
                    saveValuesFromEditTextInRows();
                    if (saveWentThrough == true) {
                        if (changeLayoutOrNot != false) {
                            setContentView(R.layout.edit_prm_ljusmatning_columns);
                            establishEditColumns();
                            setValuesInEditTextColumns();
                            swapToLayer = 0;
                        }
                    } else {
                        toast.makeText(getApplicationContext(),
                                "Fel format ifyllt", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void establishEditRows() {

        columnWidthValue = findViewById(R.id.column_width_value);
        value1 = findViewById(R.id.edit_text_1);
        value2 = findViewById(R.id.edit_text_2);
        value3 = findViewById(R.id.edit_text_3);
        value4 = findViewById(R.id.edit_text_4);
        value5 = findViewById(R.id.edit_text_5);
        value6 = findViewById(R.id.edit_text_6);
        value7 = findViewById(R.id.edit_text_7);
        value8 = findViewById(R.id.edit_text_8);
        value9 = findViewById(R.id.edit_text_9);
        value10 = findViewById(R.id.edit_text_10);
        value11 = findViewById(R.id.edit_text_11);
        value12 = findViewById(R.id.edit_text_12);
        value13 = findViewById(R.id.edit_text_13);
        value14 = findViewById(R.id.edit_text_14);
        insertRowId = findViewById(R.id.insert_row_id);
        backArrowRowId = findViewById(R.id.back_arrow_row_id);
        forwardArrowRowId = findViewById(R.id.forward_arrow_row_id);
        infoRows = findViewById(R.id.image_view_rows);

        if (currentRowIdInTabel == 1)
            backArrowRowId.setVisibility(View.INVISIBLE);
        else
            backArrowRowId.setVisibility(View.VISIBLE);
        if (currentRowIdInTabel == 35)
            forwardArrowRowId.setVisibility(View.INVISIBLE);
        else
            forwardArrowRowId.setVisibility(View.VISIBLE);

        infoRows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(EditPRMLjusmatningActivity.this);
                LayoutInflater factory = LayoutInflater.from(EditPRMLjusmatningActivity.this);
                final View view = factory.inflate(R.layout.imageview_rows, null);
                alertadd.setView(view);

                alertadd.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }
                });
                alertadd.show();
            }
        });

        swapLayoutButton = findViewById(R.id.swap_layout_button);

        swapLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swapToLayer == 0) {
                    saveValuesFromEditTextInColumns();
                    if (changeLayoutOrNot != false) {
                        setContentView(R.layout.edit_prm_ljusmatning_rows);
                        establishEditRows();
                        setValuesInEditTextRows();
                        swapToLayer = 1;
                    }
                } else {
                    saveValuesFromEditTextInRows();
                    if (saveWentThrough == true) {
                        if (changeLayoutOrNot != false) {
                            setContentView(R.layout.edit_prm_ljusmatning_columns);
                            establishEditColumns();
                            setValuesInEditTextColumns();
                            swapToLayer = 0;
                        }
                    } else {
                        toast.makeText(getApplicationContext(),
                                "Fel format ifyllt", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        forwardArrowRowId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRowIdInTabel != 35) {
                    saveValuesFromEditTextInRows();
                    currentRowIdInTabel++;
                }
                if (currentRowIdInTabel == 35)
                    forwardArrowRowId.setVisibility(View.INVISIBLE);
                else
                    forwardArrowRowId.setVisibility(View.VISIBLE);

                if (currentRowIdInTabel > 1)
                    backArrowRowId.setVisibility(View.VISIBLE);

                setValuesInEditTextRows();
            }
        });

        backArrowRowId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentRowIdInTabel != 1) {
                    saveValuesFromEditTextInRows();
                    currentRowIdInTabel--;
                }

                if (currentRowIdInTabel == 1)
                    backArrowRowId.setVisibility(View.INVISIBLE);
                else
                    backArrowRowId.setVisibility(View.VISIBLE);

                if (currentRowIdInTabel < 35)
                    forwardArrowRowId.setVisibility(View.VISIBLE);

                setValuesInEditTextRows();
            }
        });
    }

    public void setValuesInEditTextColumns() {
        ljusmatningList = databases.recoverRowsFromOneObjectFromPRMLjusmatning(plats);

        Object firstRow = ljusmatningList.get(0);

        column1.setText(String.valueOf(firstRow.getFirstValue()));
        column2.setText(String.valueOf(firstRow.getSecondValue()));
        column3.setText(String.valueOf(firstRow.getThirdValue()));
        column4.setText(String.valueOf(firstRow.getFourthValue()));
        column5.setText(String.valueOf(firstRow.getFifthValue()));
        column6.setText(String.valueOf(firstRow.getSixthValue()));
        column7.setText(String.valueOf(firstRow.getSeventhValue()));
        column8.setText(String.valueOf(firstRow.getEightValue()));
        column9.setText(String.valueOf(firstRow.getNinthValue()));
        column10.setText(String.valueOf(firstRow.getTenthValue()));
        column11.setText(String.valueOf(firstRow.getEleventhValue()));
        column12.setText(String.valueOf(firstRow.getTwelfthValue()));
        column13.setText(String.valueOf(firstRow.getThirteenthValue()));
        column14.setText(String.valueOf(firstRow.getFourteenthValue()));
        int checkboxStatus = firstRow.getCompleted();
        if (checkboxStatus == 0)
            fardig.setChecked(false);
        else
            fardig.setChecked(true);
    }

    public void setValuesInEditTextRows() {
        ljusmatningList = databases.recoverRowsFromOneObjectFromPRMLjusmatning(plats);

        Object currentObject = ljusmatningList.get(currentRowIdInTabel);

        columnWidthValue.setText(String.valueOf(currentObject.getWidthValue()));
        value1.setText(String.valueOf(currentObject.getFirstValue()));
        value2.setText(String.valueOf(currentObject.getSecondValue()));
        value3.setText(String.valueOf(currentObject.getThirdValue()));
        value4.setText(String.valueOf(currentObject.getFourthValue()));
        value5.setText(String.valueOf(currentObject.getFifthValue()));
        value6.setText(String.valueOf(currentObject.getSixthValue()));
        value7.setText(String.valueOf(currentObject.getSeventhValue()));
        value8.setText(String.valueOf(currentObject.getEightValue()));
        value9.setText(String.valueOf(currentObject.getNinthValue()));
        value10.setText(String.valueOf(currentObject.getTenthValue()));
        value11.setText(String.valueOf(currentObject.getEleventhValue()));
        value12.setText(String.valueOf(currentObject.getTwelfthValue()));
        value13.setText(String.valueOf(currentObject.getThirteenthValue()));
        value14.setText(String.valueOf(currentObject.getFourteenthValue()));
        insertRowId.setText(String.valueOf(currentRowIdInTabel));
    }

    public void saveValuesFromEditTextInColumns() {

        String firstValue = column1.getText().toString();
        String secondValue = column2.getText().toString();
        String thirdValue = column3.getText().toString();
        String fourthValue = column4.getText().toString();
        String fifthValue = column5.getText().toString();
        String sixthValue = column6.getText().toString();
        String seventhValue = column7.getText().toString();
        String eightValue = column8.getText().toString();
        String ninthValue = column9.getText().toString();
        String tenthValue = column10.getText().toString();
        String eleventhValue = column11.getText().toString();
        String twelfthValue = column12.getText().toString();
        String thirteenthValue = column13.getText().toString();
        String fourteenthValue = column14.getText().toString();
        int completedValue;

        if (fardig.isChecked() == true)
            completedValue = 1;
        else
            completedValue = 0;

        Object updateRowObject = new Object();
        updateRowObject.setFirstValue(firstValue);
        updateRowObject.setSecondValue(secondValue);
        updateRowObject.setThirdValue(thirdValue);
        updateRowObject.setFourthValue(fourthValue);
        updateRowObject.setFifthValue(fifthValue);
        updateRowObject.setSixthValue(sixthValue);
        updateRowObject.setSeventhValue(seventhValue);
        updateRowObject.setEightValue(eightValue);
        updateRowObject.setNinthValue(ninthValue);
        updateRowObject.setTenthValue(tenthValue);
        updateRowObject.setEleventhValue(eleventhValue);
        updateRowObject.setTwelfthValue(twelfthValue);
        updateRowObject.setThirteenthValue(thirteenthValue);
        updateRowObject.setFourteenthValue(fourteenthValue);
        updateRowObject.setCompleted(completedValue);

        databases.updateOneRowFromPRMLjusmatning(updateRowObject, plats, 0, true);

        changeLayoutOrNot = true;
        Toast.makeText(this, "Värdena uppdaterades",
                Toast.LENGTH_SHORT).show();
    }

    public void saveValuesFromEditTextInRows() {

        String widthValue = String.valueOf(columnWidthValue.getText());
        String firstValue = String.valueOf(value1.getText());
        String secondValue = String.valueOf(value2.getText());
        String thirdValue = String.valueOf(value3.getText());
        String fourthValue = String.valueOf(value4.getText());
        String fifthValue = String.valueOf(value5.getText());
        String sixthValue = String.valueOf(value6.getText());
        String seventhValue = String.valueOf(value7.getText());
        String eightValue = String.valueOf(value8.getText());
        String ninthValue = String.valueOf(value9.getText());
        String tenthValue = String.valueOf(value10.getText());
        String eleventhValue = String.valueOf(value11.getText());
        String twelfthValue = String.valueOf(value12.getText());
        String thirteenthValue = String.valueOf(value13.getText());
        String fourteenthValue = String.valueOf(value14.getText());

        try {
            int value;
            if (!firstValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(firstValue));
            if (!secondValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(secondValue));
            if (!thirdValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(thirdValue));
            if (!fourthValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(fourthValue));
            if (!fifthValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(fifthValue));
            if (!sixthValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(sixthValue));
            if (!seventhValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(seventhValue));
            if (!eightValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(eightValue));
            if (!ninthValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(ninthValue));
            if (!tenthValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(tenthValue));
            if (!eleventhValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(eleventhValue));
            if (!twelfthValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(twelfthValue));
            if (!fourteenthValue.trim().isEmpty())
                value = Integer.parseInt(String.valueOf(fourteenthValue));

            saveWentThrough = true;

            Object updateRowObject = new Object();

            updateRowObject.setWidthValue(widthValue);
            updateRowObject.setFirstValue(firstValue);
            updateRowObject.setSecondValue(secondValue);
            updateRowObject.setThirdValue(thirdValue);
            updateRowObject.setFourthValue(fourthValue);
            updateRowObject.setFifthValue(fifthValue);
            updateRowObject.setSixthValue(sixthValue);
            updateRowObject.setSeventhValue(seventhValue);
            updateRowObject.setEightValue(eightValue);
            updateRowObject.setNinthValue(ninthValue);
            updateRowObject.setTenthValue(tenthValue);
            updateRowObject.setEleventhValue(eleventhValue);
            updateRowObject.setTwelfthValue(twelfthValue);
            updateRowObject.setThirteenthValue(thirteenthValue);
            updateRowObject.setFourteenthValue(fourteenthValue);

            databases.updateOneRowFromPRMLjusmatning(updateRowObject, plats, currentRowIdInTabel, false);
            changeLayoutOrNot = true;

        } catch (NumberFormatException e) {
            saveWentThrough = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case (EDIT_REQUEST): {

                if (resultCode == Activity.RESULT_OK) {

                }
            }
            break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_prm_ljusmatning_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent;

        String widthValue;
        String firstValue;
        String secondValue;
        String thirdValue;
        String fourthValue;
        String fifthValue;
        String sixthValue;
        String seventhValue;
        String eightValue;
        String ninthValue;
        String tenthValue;
        String eleventhValue;
        String twelfthValue;
        String thirteenthValue;
        String fourteenthValue;


        switch (item.getItemId()) {

            case R.id.send_pdf:

                int numberOfColumns = 0;
                int totalRowValue = 0;

                if (swapToLayer == 0) {

                    for (int i = 1; i < ljusmatningList.size(); i++) {
                        Object currentObject = ljusmatningList.get(i);

                        String value1 = String.valueOf(currentObject.getFirstValue());
                        if (!value1.equals(""))
                            totalRowValue += Integer.valueOf(value1);
                        String value2 = String.valueOf(currentObject.getSecondValue());
                        if (!value2.equals(""))
                            totalRowValue += Integer.valueOf(value2);
                        String value3 = String.valueOf(currentObject.getThirdValue());
                        if (!value3.equals(""))
                            totalRowValue += Integer.valueOf(value3);
                        String value4 = String.valueOf(currentObject.getFourthValue());
                        if (!value4.equals(""))
                            totalRowValue += Integer.valueOf(value4);
                        String value5 = String.valueOf(currentObject.getFifthValue());
                        if (!value5.equals(""))
                            totalRowValue += Integer.valueOf(value5);
                        String value6 = String.valueOf(currentObject.getSixthValue());
                        if (!value6.equals(""))
                            totalRowValue += Integer.valueOf(value6);
                        String value7 = String.valueOf(currentObject.getSeventhValue());
                        if (!value7.equals(""))
                            totalRowValue += Integer.valueOf(value7);
                        String value8 = String.valueOf(currentObject.getEightValue());
                        if (!value8.equals(""))
                            totalRowValue += Integer.valueOf(value8);
                        String value9 = String.valueOf(currentObject.getNinthValue());
                        if (!value9.equals(""))
                            totalRowValue += Integer.valueOf(value9);
                        String value10 = String.valueOf(currentObject.getTenthValue());
                        if (!value10.equals(""))
                            totalRowValue += Integer.valueOf(value10);
                        String value11 = String.valueOf(currentObject.getEleventhValue());
                        if (!value11.equals(""))
                            totalRowValue += Integer.valueOf(value11);
                        String value12 = String.valueOf(currentObject.getTwelfthValue());
                        if (!value12.equals(""))
                            totalRowValue += Integer.valueOf(value12);
                        String value13 = String.valueOf(currentObject.getThirteenthValue());
                        if (!value13.equals(""))
                            totalRowValue += Integer.valueOf(value13);
                        String value14 = String.valueOf(currentObject.getFourteenthValue());
                        if (!value14.equals(""))
                            totalRowValue += Integer.valueOf(value14);
                    }

                    if (totalRowValue > 0) {

                        firstValue = column1.getText().toString();
                        secondValue = column2.getText().toString();
                        thirdValue = column3.getText().toString();
                        fourthValue = column4.getText().toString();
                        fifthValue = column5.getText().toString();
                        sixthValue = column6.getText().toString();
                        seventhValue = column7.getText().toString();
                        eightValue = column8.getText().toString();
                        ninthValue = column9.getText().toString();
                        tenthValue = column10.getText().toString();
                        eleventhValue = column11.getText().toString();
                        twelfthValue = column12.getText().toString();
                        thirteenthValue = column13.getText().toString();
                        fourteenthValue = column14.getText().toString();

                        if (!firstValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!secondValue.trim().isEmpty()) {
                        }
                        numberOfColumns++;
                        if (!thirdValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!fourthValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!fifthValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!sixthValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!seventhValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!eightValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!ninthValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!tenthValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!eleventhValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!twelfthValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!thirteenthValue.trim().isEmpty())
                            numberOfColumns++;
                        if (!fourteenthValue.trim().isEmpty())
                            numberOfColumns++;

                        if (numberOfColumns == 0 || numberOfColumns == 1 || numberOfColumns == 2) {
                            Toast.makeText(this, "För få kolumner ifyllda",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            saveValuesFromEditTextInColumns();
                            saveValuesFromEditTextInRows();
                            if (saveWentThrough == true) {

                                try {
                                    Intent intent = new Intent(EditPRMLjusmatningActivity.this, EditPDFForPRMLjusmatningActivity.class);

                                    intent.putExtra("numberOfColumns", numberOfColumns);
                                    intent.putExtra("LatLng", latLng);
                                    intent.putExtra("plats", plats);

                                    EditPRMLjusmatningActivity.this.startActivityForResult(intent, EDIT_REQUEST);
                                } catch (Exception e) {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Något gick fel",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            } else {
                                Toast.makeText(this, "Fel format ifyllt i raderna",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "Inga värden ifyllda i raderna",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Byt sida för att skapa",
                            Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.save_edite_check:

                //Column layer
                if (swapToLayer == 0) {

                    firstValue = column1.getText().toString();
                    secondValue = column2.getText().toString();
                    thirdValue = column3.getText().toString();
                    fourthValue = column4.getText().toString();
                    fifthValue = column5.getText().toString();
                    sixthValue = column6.getText().toString();
                    seventhValue = column7.getText().toString();
                    eightValue = column8.getText().toString();
                    ninthValue = column9.getText().toString();
                    tenthValue = column10.getText().toString();
                    eleventhValue = column11.getText().toString();
                    twelfthValue = column12.getText().toString();
                    thirteenthValue = column13.getText().toString();
                    fourteenthValue = column14.getText().toString();

                    int completedValue;

                    if (fardig.isChecked() == true)
                        completedValue = 1;
                    else
                        completedValue = 0;

                    Object updateRowObject = new Object();

                    updateRowObject.setFirstValue(firstValue);
                    updateRowObject.setSecondValue(secondValue);
                    updateRowObject.setThirdValue(thirdValue);
                    updateRowObject.setFourthValue(fourthValue);
                    updateRowObject.setFifthValue(fifthValue);
                    updateRowObject.setSixthValue(sixthValue);
                    updateRowObject.setSeventhValue(seventhValue);
                    updateRowObject.setEightValue(eightValue);
                    updateRowObject.setNinthValue(ninthValue);
                    updateRowObject.setTenthValue(tenthValue);
                    updateRowObject.setEleventhValue(eleventhValue);
                    updateRowObject.setTwelfthValue(twelfthValue);
                    updateRowObject.setThirteenthValue(thirteenthValue);
                    updateRowObject.setFourteenthValue(fourteenthValue);
                    updateRowObject.setCompleted(completedValue);

                    try {
                        databases.updateOneRowFromPRMLjusmatning(updateRowObject, plats, 0, true);
                        resultIntent = new Intent();
                        resultIntent.putExtra("returnCheckboxValue", returnCheckboxValue);
                        resultIntent.putExtra("content", content);
                        resultIntent.putExtra("objectType", objectType);
                        resultIntent.putExtra("location", latLng);
                        resultIntent.putExtra("prmType", prmType);
                        resultIntent.putExtra("plats", plats);
                        resultIntent.putExtra("positionInListView", positionInListView);
                        resultIntent.putExtra("updateListView", true);

                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();

                        Toast.makeText(this, "Värdena uppdaterades",
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Värdena uppdaterades inte",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    widthValue = String.valueOf(columnWidthValue.getText());
                    firstValue = value1.getText().toString();
                    secondValue = value2.getText().toString();
                    thirdValue = value3.getText().toString();
                    fourthValue = value4.getText().toString();
                    fifthValue = value5.getText().toString();
                    sixthValue = value6.getText().toString();
                    seventhValue = value7.getText().toString();
                    eightValue = value8.getText().toString();
                    ninthValue = value9.getText().toString();
                    tenthValue = value10.getText().toString();
                    eleventhValue = value11.getText().toString();
                    twelfthValue = value12.getText().toString();
                    thirteenthValue = value13.getText().toString();
                    fourteenthValue = value14.getText().toString();

                    try {
                        int value;
                        if (!firstValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(firstValue));
                        if (!secondValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(secondValue));
                        if (!thirdValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(thirdValue));
                        if (!fourthValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(fourthValue));
                        if (!fifthValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(fifthValue));
                        if (!sixthValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(sixthValue));
                        if (!seventhValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(seventhValue));
                        if (!eightValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(eightValue));
                        if (!ninthValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(ninthValue));
                        if (!tenthValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(tenthValue));
                        if (!eleventhValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(eleventhValue));
                        if (!twelfthValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(twelfthValue));
                        if (!fourteenthValue.trim().isEmpty())
                            value = Integer.parseInt(String.valueOf(fourteenthValue));

                        saveWentThrough = true;

                        Object updateRowObject = new Object();

                        updateRowObject.setWidthValue(widthValue);
                        updateRowObject.setFirstValue(firstValue);
                        updateRowObject.setSecondValue(secondValue);
                        updateRowObject.setThirdValue(thirdValue);
                        updateRowObject.setFourthValue(fourthValue);
                        updateRowObject.setFifthValue(fifthValue);
                        updateRowObject.setSixthValue(sixthValue);
                        updateRowObject.setSeventhValue(seventhValue);
                        updateRowObject.setEightValue(eightValue);
                        updateRowObject.setNinthValue(ninthValue);
                        updateRowObject.setTenthValue(tenthValue);
                        updateRowObject.setEleventhValue(eleventhValue);
                        updateRowObject.setTwelfthValue(twelfthValue);
                        updateRowObject.setThirteenthValue(thirteenthValue);
                        updateRowObject.setFourteenthValue(fourteenthValue);

                        try {
                            databases.updateOneRowFromPRMLjusmatning(updateRowObject, plats, currentRowIdInTabel, false);

                            resultIntent = new Intent();
                            resultIntent.putExtra("returnCheckboxValue", returnCheckboxValue);
                            resultIntent.putExtra("content", content);
                            resultIntent.putExtra("objectType", objectType);
                            resultIntent.putExtra("location", latLng);
                            resultIntent.putExtra("prmType", prmType);
                            resultIntent.putExtra("plats", plats);
                            resultIntent.putExtra("positionInListView", positionInListView);
                            resultIntent.putExtra("updateListView", true);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();

                            Toast.makeText(this, "Värdena uppdaterades",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, "Värdena uppdaterades inte",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (NumberFormatException e) {
                        saveWentThrough = false;
                    }
                }
                return true;

            case android.R.id.home:
                try {
                    resultIntent = new Intent();
                    resultIntent.putExtra("returnCheckboxValue", returnCheckboxValue);
                    resultIntent.putExtra("content", content);
                    resultIntent.putExtra("objectType", objectType);
                    resultIntent.putExtra("location", latLng);
                    resultIntent.putExtra("updateListView", false);
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
