package com.doitua.doittest.activity.signup;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.model.user.User;
import com.doitua.doittest.retrofit.RetrofitService;
import com.doitua.doittest.util.Constants;
import com.doitua.doittest.util.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by batynchuk on 9/20/17.
 */

public class SignUpActivityPresenterImpl implements SignUpActivityPresenter {

    private SignUpActivityView mSignUpActivityView;
    private RetrofitService mRetrofitService;
    private ContentResolver mContentResolver;
    private File mImageDir;
    private PrefHelper mPrefHelper;

    private User mUser;

    public SignUpActivityPresenterImpl(SignUpActivityView loginActivityView,
                                       RetrofitService retrofitService,
                                       ContentResolver contentResolver,
                                       File imageDir,
                                       PrefHelper prefHelper) {
        mSignUpActivityView = loginActivityView;
        mRetrofitService = retrofitService;
        mContentResolver = contentResolver;
        mImageDir = imageDir;
        mPrefHelper = prefHelper;
    }

    @Override
    public void onCreated() {
        mUser = new User();
    }

    @Override
    public void onImagePicked(Uri data) {

        Bitmap selectedBitmap = null;
        try {
            selectedBitmap = MediaStore.Images.Media.getBitmap(mContentResolver, data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap newBitmap = ImageUtils.scaleDown(selectedBitmap, 1000, true);
        selectedBitmap.recycle();
        selectedBitmap = null;

        File newImageFile = ImageUtils.createImageFile(mImageDir, Constants.AVATAR_FILE_NAME);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newImageFile);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
        }

        mUser.setAvatar(newImageFile);
        mSignUpActivityView.setImage(newImageFile);
    }

    @Override
    public void onSendClick(@Nullable String userName, String email, String password) {

        mUser.setUserName(userName);
        mUser.setEmail(email);
        mUser.setPassword(password);

//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("avatar",
//                mUser.getAvatar().getName(),
//                RequestBody.create(MediaType.parse("image/*"), mUser.getAvatar()));

        mRetrofitService.createUser(
                stringToRequestBody(mUser.getUserName()),
                stringToRequestBody(mUser.getEmail()),
                stringToRequestBody(mUser.getPassword()),
                RequestBody.create(MediaType.parse("image/*"), mUser.getAvatar()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(signInResponseResponse -> {
                    if (signInResponseResponse.isSuccessful()) {
                        mPrefHelper.setToken(signInResponseResponse.body().getToken());
                        mPrefHelper.setAvatarUrl(signInResponseResponse.body().getAvataLink());
                        mSignUpActivityView.startFeedActivity();
                    } else {
                        mSignUpActivityView.showError();
                    }
                });


    }

    private RequestBody stringToRequestBody(String string) {
        return RequestBody.create(MediaType.parse("text/plain"), string);
    }


}
