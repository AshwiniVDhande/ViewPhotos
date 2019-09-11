package viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.viewphotos.R;

import java.util.ArrayList;
import java.util.List;

import interfaces.IHttpEventTracker;
import models.Photos;
import okhttp3.ResponseBody;
import wrappers.PhotosWrapper;

public class MainRepository implements IHttpEventTracker<List<Photos>> {
    MutableLiveData<ArrayList<Photos>> data;
    private static MainRepository mainRepository;
    private PhotosWrapper mPhotosWrapper;
    private Context mContext;

    /**
     * Create SingleTon object
     *
     * @param application
     */
    private MainRepository(Application application) {
        mContext = application;
        if (mPhotosWrapper == null)
            mPhotosWrapper = new PhotosWrapper(this);
    }

    /**
     * Initialized repository instance
     *
     * @param application context
     * @return repository instance
     */
    public synchronized static MainRepository getInstance(Application application) {
        if (mainRepository == null) {
            mainRepository = new MainRepository(application);
        }
        return mainRepository;
    }

    /**
     * Network call to get ALL photos
     *
     * @param Start starting of index of page
     * @return Mutable list
     */
    public MutableLiveData<ArrayList<Photos>> callPhotoAPI(int Start) {
        data = new MutableLiveData<>();

        /*Create handle for the RetrofitInstance interface*/
        mPhotosWrapper.mCallAllPhotos(Start, 15);
        return data;
    }

    @Override
    public void onCallSuccess(@NonNull List<Photos> photosList) {
        data.postValue((ArrayList<Photos>) photosList);
    }

    @Override
    public void onCallFail(@NonNull String cause, @NonNull Throwable t, @Nullable ResponseBody responseBody) {
        Toast.makeText(mContext, mContext.getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }
}
