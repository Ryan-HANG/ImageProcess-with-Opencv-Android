#include <jni.h>


extern "C" {
/*
 * Class:     com_example_ryan_imagedection_LibImgFun
 * Method:    imgFun
 * Signature: ([III)[I
 */
JNIEXPORT jintArray JNICALL Java_com_example_ryan_imagedection_LibImgFun_imgFun
  (JNIEnv *, jclass, jintArray, jint, jint);

/*
 * Class:     com_example_ryan_imagedection_LibImgFun
 * Method:    grayProc
 * Signature: ([III)[I
 */
JNIEXPORT jintArray JNICALL Java_com_example_ryan_imagedection_LibImgFun_grayProc
  (JNIEnv *, jclass, jintArray, jint, jint);

}
