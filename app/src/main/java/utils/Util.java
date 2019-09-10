package utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import models.Photos;
import retrofit2.Response;


/*
 * Created by Ashwini Dhande on 03-11-2017.
 */

/**
 * Util class contains common methods used all over app
 */
public class Util {

    /**
     * Check if string is Null or Empty.
     *
     * @param input string to be examined.
     * @return true if string is Null or Empty, otherwise false.
     */
    public static boolean isNullOrEmpty(@Nullable String input) {
        return TextUtils.isEmpty(input);
    }

    /**
     * Check for response validity with following conditions:
     * NotNull, Non-empty body, {{@link Response#isSuccessful()}}
     *
     * @param response object to be examined.
     * @return true if response is valid, otherwise false.
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isValidResponse(@NonNull Response<Photos> response) {
        return response.body() != null && response.isSuccessful();
    }
}