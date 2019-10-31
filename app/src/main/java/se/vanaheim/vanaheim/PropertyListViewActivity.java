package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import se.vanaheim.vanaheim.adapters.PropertyListAdapter;
import se.vanaheim.vanaheim.data.HandleDatabases;
import se.vanaheim.vanaheim.models.PropertyListObjects;
import se.vanaheim.vanaheim.viewmodels.HandlePDF;

public class PropertyListViewActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<PropertyListObjects> propertyListArray;
    private PropertyListAdapter propertyListAdapter;
    private static final int EDIT_REQUEST = 1;
    private int currentPosition;
    private HandleDatabases databases;
    private PropertyListObjects propertyList;
    private HandlePDF pdfHandler;
    private int listSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            setContentView(R.layout.list_view_for_property_list);
            pdfHandler = new HandlePDF(this);
            databases = new HandleDatabases(this);

            propertyList = databases.recoverPropertyList();
            boolean isEmpty = propertyList.getIsEmpty();
            if (isEmpty == true)
                databases.createPropertyList();
            propertyListArray = new ArrayList<>();
            restorePropertyList();
            createListView();

            listSize = propertyListArray.size();
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Gick inte att skapa",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
    }

    private void restorePropertyList() {
        try {
            //Objekt för att visa i listView med Kap i TSD och titlar
            PropertyListObjects propertyListObject1 = new PropertyListObjects();
            PropertyListObjects propertyListObject2 = new PropertyListObjects();
            PropertyListObjects propertyListObject3 = new PropertyListObjects();
            PropertyListObjects propertyListObject4 = new PropertyListObjects();
            PropertyListObjects propertyListObject5 = new PropertyListObjects();
            PropertyListObjects propertyListObject6 = new PropertyListObjects();
            PropertyListObjects propertyListObject7 = new PropertyListObjects();
            PropertyListObjects propertyListObject8 = new PropertyListObjects();
            PropertyListObjects propertyListObject9 = new PropertyListObjects();
            PropertyListObjects propertyListObject10 = new PropertyListObjects();
            PropertyListObjects propertyListObject11 = new PropertyListObjects();
            PropertyListObjects propertyListObject12 = new PropertyListObjects();
            PropertyListObjects propertyListObject13 = new PropertyListObjects();
            PropertyListObjects propertyListObject14 = new PropertyListObjects();
            PropertyListObjects propertyListObject15 = new PropertyListObjects();

            //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************1
            propertyListObject1.setRowID(1);
            propertyListObject1.setKapITSD("4.2.1.1");
            propertyListObject1.setTitel("Parkeringsmöjligheter för funktionshindrade");

            //************ 4.2.1.2 Hinderfri gångväg*************2
            propertyListObject2.setRowID(2);
            propertyListObject2.setKapITSD("4.2.1.2");
            propertyListObject2.setTitel("Hinderfri gångväg");

            //************ 4.2.1.2.1 Horisontell förflyttning************3
            propertyListObject3.setRowID(3);
            propertyListObject3.setKapITSD("4.2.1.2.1");
            propertyListObject3.setTitel("Horisontell förflyttning");

            //************ 4.2.1.2.2 Vertikal förflyttning************4
            propertyListObject4.setRowID(4);
            propertyListObject4.setKapITSD("4.2.1.2.2");
            propertyListObject4.setTitel("Vertikal förflyttning");

            //************ 4.2.1.2.3 Gångvägsmarkering************5
            propertyListObject5.setRowID(5);
            propertyListObject5.setKapITSD("4.2.1.2.3");
            propertyListObject5.setTitel("Gångvägsmarkering");

            //************ 4.2.1.4 Golvytor************6
            propertyListObject6.setRowID(6);
            propertyListObject6.setKapITSD("4.2.1.4");
            propertyListObject6.setTitel("Golvytor");

            //************ 4.2.1.5 Markering av genomskinliga hinder************7
            propertyListObject7.setRowID(7);
            propertyListObject7.setKapITSD("4.2.1.5");
            propertyListObject7.setTitel("Markering av genomskinliga hinder");

            //************ 4.2.1.6 Toaletter och skötplatser************8
            propertyListObject8.setRowID(8);
            propertyListObject8.setKapITSD("4.2.1.6");
            propertyListObject8.setTitel("Toaletter och skötplatser");

            //************ 4.2.1.7 Inredning och fristående enheter************9
            propertyListObject9.setRowID(9);
            propertyListObject9.setKapITSD("4.2.1.7");
            propertyListObject9.setTitel("Inredning och fristående enheter");

            //************4.2.1.9 Belysning************10
            propertyListObject10.setRowID(10);
            propertyListObject10.setKapITSD("4.2.1.9");
            propertyListObject10.setTitel("Belysning");

            //************4.2.1.10 Visuell information************11
            propertyListObject11.setRowID(11);
            propertyListObject11.setKapITSD("4.2.1.10");
            propertyListObject11.setTitel("Visuell information");

            //************4.2.1.11 Talad information Sidoplattform***********12
            propertyListObject12.setRowID(12);
            propertyListObject12.setKapITSD("4.2.1.11");
            propertyListObject12.setTitel("Talad information Sidoplattform");

            //************4.2.1.12 Plattformsbredd och plattformskant************13
            propertyListObject13.setRowID(13);
            propertyListObject13.setKapITSD("4.2.1.12");
            propertyListObject13.setTitel("Plattformsbredd och plattformskant");

            //************4.2.1.13 Plattformens slut************14
            propertyListObject14.setRowID(14);
            propertyListObject14.setKapITSD("4.2.1.13");
            propertyListObject14.setTitel("Plattformens slut");

            //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************15
            propertyListObject15.setRowID(15);
            propertyListObject15.setKapITSD("4.2.1.15");
            propertyListObject15.setTitel("Spårkorsning för passagerare påväg till plattformar");

            propertyListArray.add(propertyListObject1);
            propertyListArray.add(propertyListObject2);
            propertyListArray.add(propertyListObject3);
            propertyListArray.add(propertyListObject4);
            propertyListArray.add(propertyListObject5);
            propertyListArray.add(propertyListObject6);
            propertyListArray.add(propertyListObject7);
            propertyListArray.add(propertyListObject8);
            propertyListArray.add(propertyListObject9);
            propertyListArray.add(propertyListObject10);
            propertyListArray.add(propertyListObject11);
            propertyListArray.add(propertyListObject12);
            propertyListArray.add(propertyListObject13);
            propertyListArray.add(propertyListObject14);
            propertyListArray.add(propertyListObject15);
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Gick inte att hämta värdena",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
    }

    public void createListView() {

        try {
            listView = findViewById(R.id.list_view_for_property_list);
            propertyListAdapter = new PropertyListAdapter(this, propertyListArray);
            listView.setAdapter(propertyListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    PropertyListObjects propertyListObject = propertyListArray.get(position);

                    int rowID = propertyListObject.getRowID();

                    Intent intent = new Intent(PropertyListViewActivity.this, EditPopertyListObjectActivity.class);
                    intent.putExtra("rowId", rowID);
                    PropertyListViewActivity.this.startActivityForResult(intent, EDIT_REQUEST);
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
                    "Listview misslyckades",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
    }

    public void showDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ta bort del temporärt från listan");
        String message = "Är du säker på att du vill ta bort delen temporärt från listan? " +
                "Den borttagna delen kommer inte att skrivas ut i PDF-dokumentet. " +
                "Återställ alla delar i listan genom att gå tillbaka till tidigare fönster.";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                propertyListArray.remove(currentPosition);
                propertyListAdapter.notifyDataSetChanged();
                //createListView();

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Delen är tillfälligt borttaget från listan",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    public void showDialogCleanList() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Återställ alla värden i listan");
        String message = "Alla sparade värden i listans kvarstående delar kommer att återställas på nytt. " +
                "Tidigare borttagna delar återställs inte. " +
                "Gå tillbaka till tidigare fönster för återställning av alla delar i listan.";
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                try {
                    databases.deletePropertyList();
                    databases.createPropertyList();
                    propertyList = databases.recoverPropertyList();
                    createListView();

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Alla värden i listan är återställd",
                            Toast.LENGTH_SHORT);

                    toast.show();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Gick inte att återställa värdena i listan",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    public void showDialogForListSize() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String message = "Alla borttagna Kap i TSD PRM kommer att återställas på nytt";
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_property_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.email_support:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"adam.bergstroom@hotmail.com"});
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Förklara problemet så tydligt som möjligt.");
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                return true;*/
            case R.id.send_pdf:
                PropertyListObjects propertyList = databases.recoverPropertyList();
                pdfHandler.deletePDFFile();
                pdfHandler.createPDFForPropertyList(propertyListArray, propertyList, "1_1");
                pdfHandler.sendPDF();
                return true;
            case R.id.format_list:
                showDialogCleanList();
                return true;
            case android.R.id.home:
                int newListSize = propertyListArray.size();

                if (newListSize < listSize) {
                    showDialogForListSize();
                } else {
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

