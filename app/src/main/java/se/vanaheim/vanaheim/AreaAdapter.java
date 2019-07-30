package se.vanaheim.vanaheim;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AreaAdapter extends ArrayAdapter<Area>{

    public AreaAdapter(Activity context, ArrayList<Area> areaList){
        super(context,0, areaList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view_area_info,parent,false);
        }

        Area currentArea = getItem(position);

        TextView objectsName = listItemView.findViewById(R.id.area_name);
        objectsName.setText(currentArea.getAreaName());

        TextView objectsAreaOT = listItemView.findViewById(R.id.area_type);
        int objectType = currentArea.getAreaObjectTypeNumber();
        if(objectType == 0)
         objectsAreaOT.setText("INF");
        else if(objectType == 1)
            objectsAreaOT.setText("ENE");
        else
            objectsAreaOT.setText("PRM");

        /**
        TextView numberOfObjects = listItemView.findViewById(R.id.number_of_objects);
        String nmbr = Integer.toString(currentArea.getNumberOfObject());
        numberOfObjects.setText("Antal objekt: " + nmbr);
        */
        return listItemView;
    }

}
