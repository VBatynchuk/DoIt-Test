package com.doitua.doittest.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doitua.doittest.R;
import com.doitua.doittest.activity.feed.ImagesFeedActivity;
import com.doitua.doittest.activity.login.di.DaggerLoginActivityComponent;
import com.doitua.doittest.activity.login.di.LoginActivityModule;
import com.doitua.doittest.app.DoItApp;
import com.doitua.doittest.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    @BindView(R.id.iv_user_image)
    ImageView ivUserImage;
    @BindView(R.id.et_email)
    TextView etEmail;
    @BindView(R.id.et_password)
    TextView etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Inject
    LoginActivityPresenter mLoginActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        injectActivity();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile);
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);

        ivUserImage.setImageDrawable(roundedBitmapDrawable);

        btnLogin.setOnClickListener(v -> mLoginActivityPresenter
                .onLoginClick(etEmail.getText().toString(), etPassword.getText().toString()));

    }

    private void injectActivity() {
        DaggerLoginActivityComponent.builder()
                .doItAppComponent(((DoItApp) getApplication()).getDoItAppComponent())
                .loginActivityModule(new LoginActivityModule(this))
                .build()
                .injectActivity(this);
    }

    @Override
    public void startFeedActivity() {
        startActivity(new Intent(this, ImagesFeedActivity.class));
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.sign_up_problem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
