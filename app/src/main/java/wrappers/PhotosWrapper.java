package wrappers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import interfaces.IHttpEventTracker;
import interfaces.IHttpOperationCallback;
import interfaces.IPhotosService;
import models.Photos;
import networks.HttpOperationWrapper;
import networks.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import utils.Constant;

/*
 * Created by Ashwini Dhande on 08-09-2019.
 */

/**
 * Wrapper class to render list of photos
 */
public class PhotosWrapper implements IHttpOperationCallback {
    private IPhotosService mService;
    private IHttpEventTracker<List<Photos>> m_eventTracker;
    private HttpOperationWrapper m_httpOperationWrapper;
    private Call<List<Photos>> m_call;

    /**
     * Constructor function.
     * Set a listener to respond to HTTP events.
     *
     * @param eventTracker to be register.
     */
    public PhotosWrapper(@NonNull IHttpEventTracker<List<Photos>> eventTracker) {
        m_httpOperationWrapper = new HttpOperationWrapper();
        m_eventTracker = eventTracker;
    }

    /**
     * Creates single instance of {{@link IPhotosService}}
     */
    private IPhotosService m_getServiceInstance() {
        try {
            mService = RetrofitClientInstance.getRetrofitInstance().create(IPhotosService.class);
        } catch (Exception e) {
            mService = null;
        }
        return mService;
    }

    /**
     * To get list of photos
     *
     * @param start of photo index
     * @param total of photos
     */
    public void mCallAllPhotos(@NonNull int start, int total) {
        if (m_getServiceInstance() != null && m_httpOperationWrapper != null) {
            m_call = m_getServiceInstance().getPhotos(start, total);
            m_httpOperationWrapper.initCall(m_call, this);
        } else {
            m_eventTracker.onCallFail(Constant.BAD_REQUEST, new Throwable("Something went wrong, Try again later!"), null);
        }
    }

    @Override
    public void onSuccess(@NonNull Call<List<Photos>> call, @NonNull List<Photos> contentResponse) {
        m_eventTracker.onCallSuccess(contentResponse);
    }

    @Override
    public void onFailure(@Nullable Call<List<Photos>> call, @NonNull Throwable t, @Nullable ResponseBody responseBody) {
        if (m_eventTracker != null) {
            m_eventTracker.onCallFail(t.getLocalizedMessage(), t, responseBody);
        }
    }
}