package com.doitua.doittest.activity.upload;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doitua.doittest.R;
import com.doitua.doittest.activity.upload.di.DaggerUploadImageActivityComponent;
import com.doitua.doittest.activity.upload.di.UploadImageActivityModule;
import com.doitua.doittest.app.DoItApp;
import com.doitua.doittest.base.BaseActivity;
import com.doitua.doittest.util.PermissionChecker;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadImageActivity extends BaseActivity implements UploadImageActivityView {

    @BindView(R.id.iv_image_to_upload)
    ImageView ivImageToUpload;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.et_hashtag)
    EditText etHashtag;
    @BindView(R.id.pb_image_uploading)
    ProgressBar pbImageUploading;

    @Inject
    UploadImageActivityPresenter mUploadImageActivityPresenter;
    @Inject
    PermissionChecker mPermissionChecker;
    @Inject
    FusedLocationProviderClient mProvideClient;

    private static final int SELECT_IMAGE_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        ButterKnife.bind(this);
        injectActivity();

        ivImageToUpload.setOnClickListener(v -> pickImage());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.upload_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_upload) {
            mUploadImageActivityPresenter.onSendClick(etDescription.getText().toString(),
                    etHashtag.getText().toString());
        }

        return super.onOptionsItemSelected(item);
    }

    private void pickImage() {

        if (mPermissionChecker.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            startPickImageIntent();
        } else {
            mPermissionChecker.requestForWriteStoragePermission();
        }

    }

    private void startPickImageIntent() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mUploadImageActivityPresenter.onImagePicked(data.getData());
            }
        }
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionChecker.WRITE_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startPickImageIntent();
            } else {
                Toast.makeText(this, R.string.storage_permission_needed, Toast.LENGTH_LONG).show();
                //show user that we need this permission
            }
        } else if (requestCode == PermissionChecker.LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownCoordinates();
            } else {
                //show user that we need this permission
                showImageLocationRequired();
            }
        }
    }

    @Override
    public void setImage(File file) {
        Glide.with(this)
                .load(file)
                .into(ivImageToUpload);
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void getLastKnownCoordinates() {
        if (mPermissionChecker.isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            mProvideClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location.getLatitude() != 0 && location.getLongitude() != 0) {
                    mUploadImageActivityPresenter.onCoordinatesGot((float) location.getLatitude(), (float) location.getLongitude());
                } else {
                    showToast(R.string.problem_getting_coord);
                }
            });
        } else {
            mPermissionChecker.requestForLocationPermission();
        }
    }

    @Override
    public void showNoFileSelected() {
        showToast(R.string.no_image_selected);
    }

    @Override
    public void showImageLocationRequired() {
        Toast.makeText(this, R.string.location_permission_needed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSuccessfulResult() {
        hildeUploading();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showToSmallImage() {
        showToast(R.string.image_too_small);
    }

    @Override
    public void showUploading() {
        pbImageUploading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hildeUploading() {
        pbImageUploading.setVisibility(View.GONE);
    }


    void injectActivity() {

        DaggerUploadImageActivityComponent.builder()
                .doItAppComponent(((DoItApp) getApplication()).getDoItAppComponent())
                .uploadImageActivityModule(new UploadImageActivityModule(this))
                .build()
                .injectActivity(this);
    }

    private void showToast(@StringRes int intRes) {
        Toast.makeText(this, intRes, Toast.LENGTH_SHORT).show();
    }

}
