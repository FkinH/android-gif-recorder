# android-gif-recorder
An android gif recoder, help you make a gif on android device with or without root.

This project is based on [android-gif-encoder](https://github.com/nbadal/android-gif-encoder)

## Usage

project required
```
android:sharedUserId="android.uid.system"
```


```
GifRecorder recorder = new GifRecorder("gif file path"); // set path here
recorder.start();

// TODO do something on your device

recorder.stop(true); // true/false save/unsave this gif file
```
