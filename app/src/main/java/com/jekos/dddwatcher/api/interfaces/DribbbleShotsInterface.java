package com.jekos.dddwatcher.api.interfaces;

import com.jekos.dddwatcher.models.Shot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by жекос on 21.08.2017.
 */

public interface DribbbleShotsInterface {
    @GET("shots")
    Call<List<Shot>> getShots(@Query("access_token") String key, @Query("page") int page);

    @GET("shots/{id}/")
    Call<Shot> getShot (@Path("id") int id,@Query("access_token") String key);
}
