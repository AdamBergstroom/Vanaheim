package se.vanaheim.vanaheim.ui.subsystem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubsystemViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SubsystemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is subsystem fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}