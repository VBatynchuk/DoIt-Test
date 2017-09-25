package com.doitua.doittest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doitua.doittest.R;
import com.doitua.doittest.model.image.ImageModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by batynchuk on 9/22/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<ImageModel> mImageList;
    private Context mContext;

    public ImageAdapter(Context context, List<ImageModel> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageModel imageModel = mImageList.get(position);

        Glide.with(mContext)
                .load(imageModel.getSmallImageUrl())
                .into(holder.ivImage);

        holder.tvAddress.setText(imageModel.getImageParametersList().getAddress());
        holder.tvWeather.setText(imageModel.getImageParametersList().getWeather());
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_weather)
        TextView tvWeather;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
