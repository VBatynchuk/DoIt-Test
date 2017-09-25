package com.doitua.doittest.activity.feed;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doitua.doittest.R;
import com.doitua.doittest.activity.feed.di.DaggerImagesFeedActivityComponent;
import com.doitua.doittest.activity.feed.di.ImagesFeedActivityModule;
import com.doitua.doittest.activity.upload.UploadImageActivity;
import com.doitua.doittest.adapter.ImageAdapter;
import com.doitua.doittest.app.DoItApp;
import com.doitua.doittest.base.BaseActivity;
import com.doitua.doittest.model.image.ImageModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by batynchuk on 9/21/17.
 */

public class ImagesFeedActivity extends BaseActivity implements ImagesFeedActivityView {

    @BindView(R.id.rv_images)
    RecyclerView rvImages;
    @BindView(R.id.pb_images_loading)
    ProgressBar pbImageLoading;

    public static final int UPLOAD_IMAGE_CODE = 101;

    @Inject
    ImagesFeedActivityPresenter mImagesFeedActivityPresenter;

    @Inject
    Dialog mGifDialog;

    private ProgressBar pbGifLoading;
    private GifDrawable mGifDrawable;

    private final RequestListener<GifDrawable> mGifRequestListener = new RequestListener<GifDrawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            pbGifLoading.setVisibility(View.GONE);
            mGifDrawable = resource;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_feed);
        ButterKnife.bind(this);

        rvImages.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvImages.setHasFixedSize(true);

        injectActivity();

        mImagesFeedActivityPresenter.onCreated();

    }

    @Override
    public void showProgress() {
        rvImages.setVisibility(View.GONE);
        pbImageLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbImageLoading.setVisibility(View.GONE);
        rvImages.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(List<ImageModel> items) {
        rvImages.setAdapter(new ImageAdapter(this, items));
    }

    @Override
    public void playGif(String url) {

        ImageView imageView = mGifDialog.findViewById(R.id.iv_gif);

        Glide.with(this)
                .asGif()
                .load(url)
                .listener(mGifRequestListener)
                .into(imageView);

        mGifDialog.setOnDismissListener(dialogInterface -> {
            mGifDrawable.stop();
            mGifDrawable.recycle();
        });

        mGifDialog.show();

    }

    @Override
    public void showGifProgressDialog() {

        mGifDialog.setContentView(getLayoutInflater().inflate(R.layout.gif_dialog, null));
        pbGifLoading = mGifDialog.findViewById(R.id.pb_gif_loading);

    }


    void injectActivity() {
        DaggerImagesFeedActivityComponent.builder()
                .doItAppComponent(((DoItApp) getApplication()).getDoItAppComponent())
                .imagesFeedActivityModule(new ImagesFeedActivityModule(this))
                .build()
                .injectActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.images_feed_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_IMAGE_CODE && resultCode == RESULT_OK) {
            mImagesFeedActivityPresenter.onUpdate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivityForResult(new Intent(this, UploadImageActivity.class), UPLOAD_IMAGE_CODE);
        } else if (item.getItemId() == R.id.action_play_gif) {
            mImagesFeedActivityPresenter.onPlayGifClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
