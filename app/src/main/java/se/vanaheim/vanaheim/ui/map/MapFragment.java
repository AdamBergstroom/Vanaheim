package se.vanaheim.vanaheim.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import se.vanaheim.vanaheim.R;

public class MapFragment extends Fragment {

    private SupportMapFragment mapFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng latLng = new LatLng(1.289545, 103.849972);
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                            .title("Singapore"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            });
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return rootView;
    }

/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_map_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
                System.exit(1);
                break;
        }
        return true;

    }

    */

}