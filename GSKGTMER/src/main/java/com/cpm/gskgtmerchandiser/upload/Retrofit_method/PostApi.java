package com.cpm.gskgtmerchandiser.upload.Retrofit_method;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by upendrak on 16-05-2017.
 */

public interface PostApi {

    @POST("Logindetail")
    Call<ResponseBody> getLogindetail(@Body RequestBody request);

    @POST("uploadImages")
    Call<ResponseBody> getUploadImage(@Body RequestBody request);

    @POST("DownloadAll")
    Call<ResponseBody> getDownloadAll(@Body RequestBody request);

    @POST("GET_Store_SecondaryWindowImage")
    Call<ResponseBody> getSecondaryWindowImage(@Body RequestBody request);

    @POST("Upload_StoreGeoTag_IMAGES")
    Call<ResponseBody> getGeoTagImage(@Body RequestBody request);

}
