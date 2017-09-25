package com.doitua.doittest.retrofit;

import com.doitua.doittest.model.gif.Gif;
import com.doitua.doittest.model.image.ImageResponseList;
import com.doitua.doittest.model.token.SignInResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by batynchuk on 9/20/17.
 */

public interface RetrofitService {

    @Multipart
    @POST("create")
    Observable<Response<SignInResponse>> createUser(@Part("username") RequestBody userName,
                                                    @Part("email") RequestBody email,
                                                    @Part("password") RequestBody password,
                                                    @Part("avatar") RequestBody file);

    @FormUrlEncoded
    @POST("login")
    Observable<Response<SignInResponse>> login(@Field("email") String email,
                                               @Field("password") String password);

    @GET("all")
    Observable<Response<ImageResponseList>> getAllImages(@Header("token") String token);

    @GET("gif")
    Observable<Response<Gif>> getGif(@Header("token") String token);

    @Multipart
    @POST("image")
    Observable<Response<ResponseBody>> uploadImage(@Header("token") String token,
                                                   @Part MultipartBody.Part file,
                                                   @Part("description") RequestBody description,
                                                   @Part("hashtag") RequestBody hashTag,
                                                   @Part("latitude") RequestBody latitude,
                                                   @Part("longitude") RequestBody longitude);

}
