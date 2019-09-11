/*
 * Created by Onkar Nene on 14-07-2017.
 */
package interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.ResponseBody;

/**
 * Keep Track of {{@link IHttpOperationCallback}} HTTP events.
 *
 * @param <T> model type.
 */
public interface IHttpEventTracker<T>
{
    /**
     * Callback function.
     * Call when current HTTP request executes successfully.
     *
     * @param model contains T object.
     */
    void onCallSuccess(@NonNull T model);

    /**
     * Callback function.
     * Call when current HTTP request fails or response code is not 200 (HTTP OK).
     *
     * @param cause        of the request failure.
     * @param t            contains cause of the failure.
     * @param responseBody contains error body of response.
     */
    void onCallFail(@NonNull String cause, @NonNull Throwable t, @Nullable ResponseBody responseBody);
}