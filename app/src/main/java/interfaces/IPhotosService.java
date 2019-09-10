package interfaces;


import java.util.List;

import models.Photos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPhotosService {

    @GET("/photos?")
    Call<List<Photos>> getPhotos(@Query("_start") int start, @Query("_limit") int limit);
}
