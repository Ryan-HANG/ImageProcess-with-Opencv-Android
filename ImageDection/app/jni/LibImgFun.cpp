//
// Created by Ryan on 2017/11/24.
//

#include <LibImgFun.h>
#include <opencv2/opencv.hpp>
#include<highgui.h>
#include <string>
#include <vector>
#include<stdio.h>
#include<stdlib.h>
#include<iostream>
using namespace cv;
using namespace std;

//改写的canny检测代码，返回值为存储canny检测图像数据的int数组jintArray，传递的参数存储原始图像数据的int数组，以及w和h
JNIEXPORT jintArray JNICALL Java_com_example_ryan_imagedection_LibImgFun_imgFun(JNIEnv* env, jclass obj, jintArray buf, jint w, jint h){
    jint *cbuf;
    cbuf = env->GetIntArrayElements(buf, false);//JNI传递int数组的方法
    if (cbuf == NULL)
    {
        return 0;
    }
    Mat myimg(h, w, CV_8UC4, (unsigned char*) cbuf);//java中读入的图像都是4通道的，所以这里myimg声明定义为CV_8UC4,以一个数组中的数据来建立Mat
    Mat grayimg;
    cvtColor(myimg,grayimg,CV_BGRA2GRAY);//将4通道的彩色图转为灰度图
    Mat pCannyImage;
    Canny(grayimg,pCannyImage,50,150,3);//canny检测

    uchar* ptr = myimg.ptr(0);//因为灰度图最终以4通道bmp形式显示，所以将得到的灰度图grayimg的数据赋值到4通道的imageData中，
      for(int i = 0; i < w*h; i ++){
         //得到的canny图像原本是单通道，但java中显示bmp时只能4通道或三通道，为了使显示的图像时灰度的，把canny图像的数据值赋给一个4通道的myimg
          //对于一个int四字节，其彩色值存储方式为：BGRA
          ptr[4*i+1] = pCannyImage.data[i];
          ptr[4*i+2] = pCannyImage.data[i];
          ptr[4*i+0] = pCannyImage.data[i];
      }

        //以下部分是将得到canny图像存在数组中，以数组的形式返回
        int size = w * h;
        jintArray result = env->NewIntArray(size);
        env->SetIntArrayRegion(result, 0, size, cbuf);
        env->ReleaseIntArrayElements(buf, cbuf, 0);
        return result;
}



JNIEXPORT jintArray JNICALL Java_com_example_ryan_imagedection_LibImgFun_grayProc(JNIEnv* env, jclass obj, jintArray buf, jint w, jint h){
    jint *cbuf;
    cbuf = env->GetIntArrayElements(buf, false);
    if(cbuf == NULL){
        return 0;
    }
    Mat imgData(h, w, CV_8UC4, (unsigned char*)cbuf);

    Mat grayimg;
    cvtColor(imgData,grayimg,CV_BGRA2GRAY);//将4通道的彩色图转为灰度图
    uchar* ptr = imgData.ptr(0);//因为灰度图最终以4通道bmp形式显示，所以将得到的灰度图grayimg的数据赋值到4通道的imageData中，
    for(int i = 0; i < w*h; i ++){
      //计算公式：Y(亮度) = 0.299*R + 0.587*G + 0.114*B//去掉了原始程序中转换的公式
      //对于一个int四字节，其彩色值存储方式为：BGRA
      ptr[4*i+1] = grayimg.data[i];
      ptr[4*i+2] = grayimg.data[i];
      ptr[4*i+0] = grayimg.data[i];
    }

    //以下部分是将得到canny图像存在数组中，以数组的形式返回
    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, cbuf);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}