package com.doitua.doittest.util;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by batynchuk on 9/21/17.
 */

public class ImageUtils {

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());


        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width,
                height, filter);
    }

    public static File createImageFile(File imageFolder, String stationId) {

        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }

        return new File(imageFolder,
                stationId);
    }

}
