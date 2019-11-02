package se.vanaheim.vanaheim.adapters;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se.vanaheim.vanaheim.models.PropertyListObjects;
import se.vanaheim.vanaheim.R;

public class PropertyListAdapter extends ArrayAdapter<PropertyListObjects> {

    public PropertyListAdapter(Activity context, ArrayList<PropertyListObjects> propertyListArray) {
        super(context, 0, propertyListArray);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view_for_property_list_objects, parent, false);
        }

        PropertyListObjects currentPropertyLitObject = getItem(position);

        TextView kapITSD = listItemView.findViewById(R.id.kap_i_tsd);
        kapITSD.setText(currentPropertyLitObject.getKapITSD());

        TextView title = listItemView.findViewById(R.id.title_for_property_list);
        title.setText(currentPropertyLitObject.getTitel());

        return listItemView;
    }

}
