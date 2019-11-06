package se.vanaheim.vanaheim.ui.map;

import android.app.Application;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import se.vanaheim.vanaheim.R;
import se.vanaheim.vanaheim.databinding.MapFragmentBinding;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;
    private MapFragmentBinding binding;
    private SupportMapFragment mapFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Application appCtx = getActivity().getApplication();
        mapViewModel = new MapViewModel(appCtx);
        binding = MapFragmentBinding.inflate(getLayoutInflater());
        initBinding();
        initMap();

        return binding.getRoot();
    }

    private void initBinding() {
        binding.profileImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019-11-03 Open navigation drawer from HomeActivity. 
            }
        });

        binding.helpImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.searchInGoogleMapsEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(getActivity(), "Du har sökt: " + v.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initMap() {
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
    }

}