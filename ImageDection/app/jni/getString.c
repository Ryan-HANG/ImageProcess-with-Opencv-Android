//
// Created by Ryan on 2017/11/24.
//
#include <jni.h>
//#include "com_example_ryan_imagedection_MainActivity.h"

JNIEXPORT jstring JNICALL Java_com_example_ryan_imagedection_MainActivity_getStringFromJNI (JNIEnv *env, jobject obj)
  {
     return (*env)->NewStringUTF(env, "I`m Str from jni libs!");
  }
