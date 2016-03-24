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

import android.os.Environment;

import java.io.BufferedOutputStream;
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
        this.delay = 500;
        this.path = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/test.gif");
        this.encoder = new AnimatedGifEncoder();
    }

    public GifRecorder(String path){
        this.delay = 5000;
        this.path = path;
        this.encoder = new AnimatedGifEncoder();
    }

    public GifRecorder(String path, long delay){
        this.delay = delay;
        this.path = path;
        this.encoder = new AnimatedGifEncoder();
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
            encoder.addFrame(Screenshot.getScreenshot(0.3f));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        encoder.finish();
    }

    public void release(boolean save){
        if(encoder != null){
            busy.set(false);
        }
    }

}
