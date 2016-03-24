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


## Lisence
```
Copyright 2016 FkinH

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
