package viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.viewphotos.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import interfaces.IPhotosService;
import models.Photos;
import networks.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    MutableLiveData<ArrayList<Photos>> data;
    RequestQueue requestQueue;
    private static MainRepository mainRepository;
    private Context mContext;
    private Gson gson;

    /**
     * Create SingleTon object
     * @param application
     */
    private MainRepository(Application application) {
        mContext = application;
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(mContext);
        gson = new Gson();
    }

    /**
     * Initialized repository instance
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
     * @param Start starting of index of page
     * @return Mutable list
     */
    public MutableLiveData<ArrayList<Photos>> callPhotoAPI(int Start) {
        data = new MutableLiveData<>();

        /*Create handle for the RetrofitInstance interface*/
        IPhotosService service = RetrofitClientInstance.getRetrofitInstance().create(IPhotosService.class);
        Call<List<Photos>> call = service.getPhotos(Start, 15);
        call.enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {
                data.postValue((ArrayList<Photos>) response.body());
            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
                Toast.makeText(mContext, mContext.getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }
}
