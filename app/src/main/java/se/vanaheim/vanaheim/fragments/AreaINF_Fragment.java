package se.vanaheim.vanaheim.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import se.vanaheim.vanaheim.models.Area;
import se.vanaheim.vanaheim.ObjectViewActivity;
import se.vanaheim.vanaheim.R;
import se.vanaheim.vanaheim.adapters.AreaAdapter;
import se.vanaheim.vanaheim.data.HandleDatabases;

public class AreaINF_Fragment extends Fragment {

    private ArrayList<Area> areaList;
    private ListView listView;
    private AreaAdapter areaAdapter;
    private HandleDatabases databases;
    private static final int EDIT_REQUEST = 1;
    private int objectType;
    private int currentAreaPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        databases = new HandleDatabases(getActivity());
        areaList = databases.recoverAreaMarkers(0);
        areaAdapter = new AreaAdapter(getActivity(), areaList);
        View rootView = inflater.inflate(R.layout.list_view_for_areas, container, false);
        listView = rootView.findViewById(R.id.listAreas);
        listView.setAdapter(areaAdapter);
        setClickListeners();
        return rootView;
    }

    public void setClickListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Area areaObject = areaList.get(position);
                double lat = areaObject.getLatitude();
                double lon = areaObject.getLongitude();
                LatLng latLng = new LatLng(lat, lon);

                Intent intent = new Intent(getActivity(), ObjectViewActivity.class);
                intent.putExtra("location", latLng);
                intent.putExtra("objectType", 0);
                intent.putExtra("currentAreaPosition", position);
                AreaINF_Fragment.this.startActivityForResult(intent, EDIT_REQUEST);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Area areaObject = areaList.get(position);
                double lat = areaObject.getLatitude();
                double lon = areaObject.getLongitude();
                LatLng latLng = new LatLng(lat, lon);

                currentAreaPosition = position;

                showDialog(latLng,objectType,currentAreaPosition);
                return true;
            }
        });
    }

    public void showDialog(final LatLng latLng, final int objectType, final int currentAreaPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Ta bort område");
        String message = "Är du säker på att du vill ta bort det här området?";
        builder.setMessage(message);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Area selectedArea = areaList.get(currentAreaPosition);

                areaList.remove(currentAreaPosition);
                areaAdapter.notifyDataSetChanged();

                double lat = selectedArea.getLatitude();
                double lon = selectedArea.getLongitude();
                LatLng latLng = new LatLng(lat, lon);
                databases.deleteAreaMarker(latLng);
                databases.deleteObjectsFromAreaMarker(latLng, objectType);
            }
        });
        builder.setNegativeButton("Avbryt", null);
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case (EDIT_REQUEST): {

                if (resultCode == Activity.RESULT_OK) {

                    LatLng latLng = data.getParcelableExtra("location");
                    objectType = data.getIntExtra("objectType", 0);
                    int currentAreaPosition = data.getIntExtra("currentAreaPosition",0);

                    Area updatedArea = databases.recoverOneAreaMarker(objectType,latLng);
                    areaList.set(currentAreaPosition,updatedArea);
                    areaAdapter.notifyDataSetChanged();
                }
            }
            break;
        }
    }

}
