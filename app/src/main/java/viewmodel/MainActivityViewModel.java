package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import models.Photos;

/**
 * ViewModel for photo repository
 */
public class MainActivityViewModel extends AndroidViewModel {

    public MainRepository mainRepository = null;


    /**
     * Initialized repository
     * @param application
     */
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance(application);
    }

    /**
     * Get the photoList
     * @param start
     * @return
     */
    public MutableLiveData<ArrayList<Photos>> getPhotoList(int start) {
        return mainRepository.callPhotoAPI(start);
    }
}
