package com.doitua.doittest.activity.upload;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import android.util.Log;

import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.model.uploadimage.UploadImage;
import com.doitua.doittest.retrofit.RetrofitService;
import com.doitua.doittest.util.Constants;
import com.doitua.doittest.util.DMSParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by batynchuk on 9/22/17.
 */

public class UploadImageActivityPresenterImpl implements UploadImageActivityPresenter {

    private final UploadImageActivityView mUploadImageActivityView;
    private final RetrofitService mRetrofitService;
    private final PrefHelper mPrefHelper;
    private final ContentResolver mContentResolver;
    private final File mCacheDir;

    private UploadImage mUploadImage;

    public UploadImageActivityPresenterImpl(UploadImageActivityView uploadImageActivityView,
                                            RetrofitService retrofitService,
                                            PrefHelper prefHelper,
                                            ContentResolver contentResolver,
                                            File cacheDir) {
        mUploadImageActivityView = uploadImageActivityView;
        mRetrofitService = retrofitService;
        mPrefHelper = prefHelper;
        mContentResolver = contentResolver;
        mCacheDir = cacheDir;

        mUploadImage = new UploadImage();
    }

    @Override
    public void onImagePicked(Uri data) {

        try {

            String fileName = data.getLastPathSegment();
            mUploadImage.setImageFile(File.createTempFile(fileName, null, mCacheDir));

            Bitmap yourSelectedImage = MediaStore.Images.Media.getBitmap(mContentResolver, data);

            if (yourSelectedImage.getWidth() < 600) {
                mUploadImageActivityView.showToSmallImage();
            } else {

                FileOutputStream fos = new FileOutputStream(mUploadImage.getImageFile());
                yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, Constants.IMAGE_COMPRESS_QUALITY, fos);
                fos.close();

                yourSelectedImage.recycle();
                yourSelectedImage = null;

                mUploadImageActivityView.setImage(mUploadImage.getImageFile());

                ExifInterface exifInterface = new ExifInterface(mContentResolver.openInputStream(data));

                DMSParser dmsParser = new DMSParser(exifInterface);

                if (dmsParser.isValid()) {
                    mUploadImage.setLatitude(dmsParser.getLatitude());
                    mUploadImage.setLongitude(dmsParser.getLongitude());

                    Log.e("TAG", "onImagePicked: " + dmsParser.toString());
                } else {
                    mUploadImageActivityView.getLastKnownCoordinates();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            // Error while creating file
        }

    }

    @Override
    public void onSendClick(String description, String hashTag) {

        if (mUploadImage.getImageFile() != null) {
            if (mUploadImage.getLatitude() != 0
                    && mUploadImage.getLongitude() != 0) {

                mUploadImageActivityView.showUploading();

                RequestBody imageReq = RequestBody.create(MediaType.parse("image/*"), mUploadImage.getImageFile());

                mRetrofitService.uploadImage(mPrefHelper.getToken(),
                        MultipartBody.Part.createFormData("image", mUploadImage.getImageFile().getName(), imageReq),
                        stringToRequestBody(description),
                        stringToRequestBody(hashTag),
                        stringToRequestBody((String.valueOf(mUploadImage.getLatitude()))),
                        stringToRequestBody(String.valueOf(mUploadImage.getLongitude())))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseBodyResponse -> {
                            if (responseBodyResponse.isSuccessful()) {
                                mUploadImageActivityView.setSuccessfulResult();
                            } else {
                                mUploadImageActivityView.hildeUploading();
                            }
                        });
            } else {
                mUploadImageActivityView.showImageLocationRequired();
            }
        } else {
            mUploadImageActivityView.showNoFileSelected();
        }
    }


    private RequestBody stringToRequestBody(String string) {
        return RequestBody.create(MediaType.parse("text/plain"), string);
    }

    @Override
    public void onCoordinatesGot(float lat, float lon) {

        mUploadImage.setLatitude(lat);
        mUploadImage.setLongitude(lon);

    }

}
