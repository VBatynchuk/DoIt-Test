package com.doitua.doittest.activity.signup;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doitua.doittest.R;
import com.doitua.doittest.activity.feed.ImagesFeedActivity;
import com.doitua.doittest.activity.login.LoginActivity;
import com.doitua.doittest.activity.signup.di.DaggerSignUpActivityComponent;
import com.doitua.doittest.activity.signup.di.SignUpActivityModule;
import com.doitua.doittest.app.DoItApp;
import com.doitua.doittest.base.BaseActivity;
import com.doitua.doittest.model.PrefHelper;
import com.doitua.doittest.util.PermissionChecker;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpActivityView {

    @BindView(R.id.iv_user_image)
    ImageView ivUserImage;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_send)
    Button btnSend;

    @OnClick(R.id.tv_login)
    void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private static final int SELECT_IMAGE_CODE = 101;

    @Inject
    SignUpActivityPresenter mSignUpActivityPresenter;

    @Inject
    PermissionChecker mPermissionChecker;

    @Inject
    PrefHelper mPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        injectActivity();

        if (!mPrefHelper.getToken().equals("")) {
            startFeedActivity();
        }

        mSignUpActivityPresenter.onCreated();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile);
        setIvUserImage(bitmap);

        ivUserImage.setOnClickListener(v -> pickImage());

        btnSend.setOnClickListener(v -> mSignUpActivityPresenter.onSendClick(
                etUserName.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString()
        ));

    }

    private void setIvUserImage(Bitmap bitmap) {

        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedBitmapDrawable.setCircular(true);

        ivUserImage.setImageDrawable(roundedBitmapDrawable);
    }

    public void pickImage() {
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
                mSignUpActivityPresenter.onImagePicked(data.getData());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            startPickImageIntent();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.sign_up_problem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startFeedActivity() {
        startActivity(new Intent(this, ImagesFeedActivity.class));
    }

    @Override
    public void setImage(File file) {

        Glide.with(this)
                .load(file)
                .apply(RequestOptions.circleCropTransform())
                .into(ivUserImage);

    }

    private void injectActivity() {
        DaggerSignUpActivityComponent.builder()
                .doItAppComponent(((DoItApp) getApplication()).getDoItAppComponent())
                .signUpActivityModule(new SignUpActivityModule(this))
                .build()
                .injectActivity(this);
    }
}
