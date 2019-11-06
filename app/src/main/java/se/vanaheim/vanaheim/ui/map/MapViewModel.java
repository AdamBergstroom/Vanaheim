package se.vanaheim.vanaheim.ui.map;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import se.vanaheim.vanaheim.data.HandleDatabase;

public class MapViewModel extends AndroidViewModel {

    private HandleDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private float zoom;
    private GoogleMap mMap;
    private LatLng currentLocation;
    private int objectTypeToDelete;
    private MutableLiveData<Boolean> showInfProjects,showEneProjects,showPrmProjects;

    public MapViewModel(@NonNull Application application) {
        super(application);
        database = new HandleDatabase(application);
        showInfProjects = new MutableLiveData<>();
        showEneProjects = new MutableLiveData<>();
        showPrmProjects = new MutableLiveData<>();
    }

    public void saveAreaMarker(String projectName, int objectType, Double latitude, Double longitude){
     database.saveAreaMarker(projectName,objectType,latitude,longitude);
    }

    public void createVaxlarOchSpar(Double latitude, Double longitude){
        database.createVaxlarOchSpar(latitude,longitude);
    }

    public MutableLiveData<Boolean> getShowInfProjects() {
        return showInfProjects;
    }

    public void setShowInfProjects(MutableLiveData<Boolean> showInfProjects) {
        this.showInfProjects = showInfProjects;
    }

    public MutableLiveData<Boolean> getShowEneProjects() {
        return showEneProjects;
    }

    public void setShowEneProjects(MutableLiveData<Boolean> showEneProjects) {
        this.showEneProjects = showEneProjects;
    }

    public MutableLiveData<Boolean> getShowPrmProjects() {
        return showPrmProjects;
    }

    public void setShowPrmProjects(MutableLiveData<Boolean> showPrmProjects) {
        this.showPrmProjects = showPrmProjects;
    }
}