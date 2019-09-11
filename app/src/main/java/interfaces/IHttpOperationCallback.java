/*
 * Created by Onkar Nene on 28-07-2017.
 */
package interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import models.Photos;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Handles HTTP Operation callbacks
 */
public interface IHttpOperationCallback {
    /**
     * Callback function
     * Called if response is successful
     *
     * @param call            instance.
     * @param contentResponse contains response body
     */
    void onSuccess(@NonNull Call<List<Photos>> call, @NonNull List<Photos> contentResponse);

    /**
     * Callback function
     * Called if response is not successful
     *
     * @param call         instance.
     * @param t            contains error body
     * @param responseBody contains error body.
     */
    void onFailure(@Nullable Call<List<Photos>> call, @NonNull Throwable t, @Nullable ResponseBody responseBody);
}