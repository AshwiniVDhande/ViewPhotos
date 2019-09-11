/*
 * Created by Onkar Nene on 16-05-2017.
 */
package networks;

import androidx.annotation.NonNull;

import java.util.List;

import interfaces.IHttpOperationCallback;
import models.Photos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constant;

/**
 * Handles HTTP call created by respective API Wrapper.
 */
public class HttpOperationWrapper {

    private IHttpOperationCallback m_callback;

    /**
     * Initialize HTTP call.
     * Check for internet connection.
     *
     * @param call     to be execute.
     * @param callback of registered API wrapper.
     */
    public void initCall(@NonNull Call<List<Photos>> call, @NonNull IHttpOperationCallback callback) {
        m_callback = callback;
        try {
            if (NetworkUtil.isNetworkAvailable()) {
                m_enqueueRequest(call);
            } else {
                m_callback.onFailure(call, new Throwable(Constant.NO_NETWORK), null);
            }
        } catch (Exception e) {
            m_callback.onFailure(call, new Throwable(Constant.HTTP_REQUEST_FAIL), null);
        }
    }

    /**
     * Enqueue Http Request and return response/failure using registered callback function.
     *
     * @param call to be executed.
     */
    private void m_enqueueRequest(@NonNull Call<List<Photos>> call) {
        call.enqueue(new Callback<List<Photos>>() {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<List<Photos>> call, @NonNull Response<List<Photos>> response) {
                if (m_callback != null) {
                    m_callback.onSuccess(call, response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Photos>> call, @NonNull Throwable throwable) {
                if (m_callback != null) {
                    m_callback.onFailure(call, new Throwable(throwable.getMessage()), null);
                }
            }
        });
    }
}