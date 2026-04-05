package com.example.parikshan1;
import android.util.Log;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImagePropertiesService {
    @GET("predict_terrain")
    Call<ImageProperties> predict_terrain(@Query("image_url") String imageUri);

}
