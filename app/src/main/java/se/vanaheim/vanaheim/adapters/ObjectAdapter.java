package se.vanaheim.vanaheim.adapters;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se.vanaheim.vanaheim.models.Object;
import se.vanaheim.vanaheim.R;

public class ObjectAdapter extends ArrayAdapter<Object> {

    private int objectType;

    public ObjectAdapter(Activity context, ArrayList<Object> objectList, int objectType) {
        super(context, 0, objectList);
        this.objectType = objectType;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object currentObject = getItem(position);
        int completedStatus = currentObject.getCompleted();

        View listItemView = convertView;

        if (listItemView == null) {
            if (completedStatus == 1) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_object_ready_item, parent, false);
            } else {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_object_item, parent, false);
            }
        }
        if (listItemView != null) {
            if (completedStatus == 1) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_object_ready_item, parent, false);
            } else {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_object_item, parent, false);
            }
        }

        TextView objectsName = listItemView.findViewById(R.id.area_object_name);
        TextView objectsType = listItemView.findViewById(R.id.area_object_type);
        if (objectType == 0) {
            objectsName.setText("Km-tal: " + "\n" + currentObject.getKmNummer());
        }
        if (objectType == 1) {
            objectsName.setText("Stolpnummer: " + "\n" + currentObject.getStolpnummer());
        }
        if (objectType == 2) {
            objectsName.setText("Plats: " + "\n" + currentObject.getPlats());
            int prmType = currentObject.getPrmType();
            if (prmType == 0)
                objectsType.setText("Ljudmätning");
            else
                objectsType.setText("Ljusmätning");
        }

        return listItemView;
    }
}