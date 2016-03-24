package com.fkinh.gifrecorder;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Screenshot {

    public static Bitmap getScreenshot(float scale) {
        try {
            InputStream raw = Runtime.getRuntime().exec("screencap").getInputStream();
            ByteArrayOutputStream ous = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 100];
            int read = 0;
            while ((read = raw.read(buffer, 0, buffer.length)) != -1) {
                ous.write(buffer, 0, read);
            }
            buffer = ous.toByteArray();
            return bitmap(buffer, scale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap bitmap(byte[] raw, float scale) {
        int width;
        int height;
        width = ((raw[1] & 0xff) << 8) | (raw[0] & 0xff);
        height = ((raw[5] & 0xff) << 8) | (raw[4] & 0xff);
        if (raw.length != width * height * 4 + 12) {
            throw new RuntimeException("Bitmap error");
        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.copyPixelsFromBuffer(ByteBuffer.wrap(Arrays.copyOfRange(raw, 12, raw.length)));
        if (scale > 0) {
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            bmp = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        }
        return bmp;
    }
}