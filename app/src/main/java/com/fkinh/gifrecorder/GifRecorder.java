/**
 * Copyright 2016 FkinH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fkinh.gifrecorder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: jinghao
 * Email: jinghao@meizu.com
 * Date: 2016-03-23
 */
public class GifRecorder extends Thread {

    private String path;

    private AnimatedGifEncoder encoder;

    private long delay;

    private AtomicBoolean busy = new AtomicBoolean(false);

    public GifRecorder(){
        this(Environment.getExternalStorageDirectory().getAbsolutePath().concat("/test.gif"), 500);
    }

    public GifRecorder(String path){
        this(path, 500);
    }

    public GifRecorder(String path, long delay){
        this.delay = delay;
        this.path = path;
        this.encoder = new AnimatedGifEncoder();
    }

    public void setDelay(long delay){
        this.delay = delay;
    }

    public AnimatedGifEncoder getEncoder(){
        return encoder;
    }

    public void setLowQualityMode(){
        encoder.setDelay(500);
        encoder.setQuality(5);
    }

    @Override
    public void run() {
        try {
            record();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void record() throws FileNotFoundException {
        busy.set(true);
        encoder.start(new BufferedOutputStream(new FileOutputStream(new File(path))));
        while (busy.get()){
            long start = System.currentTimeMillis();
            Bitmap bmp = Screenshot.getDecodedScreenshot(0.3f, Bitmap.CompressFormat.JPEG, 50);
            encoder.addFrame(bmp);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bmp != null) {
                bmp.recycle();
            }
            Log.i("SCREENSHOT", "it takes " + start + "ms to get last screenshot.");
        }
        encoder.finish();
    }

    public void release(boolean save){
        if(encoder != null){
            busy.set(false);
        }
    }

}
