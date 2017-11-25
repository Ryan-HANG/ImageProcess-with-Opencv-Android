LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE=STATIC

ifeq ("$(wildcard $(OPENCV_MK_PATH))","")
include G:\workspace\OpenCV-3.1.0-android-sdk\OpenCV-android-sdk\sdk\native\jni\OpenCV.mk
else
include $(OPENCV_MK_PATH)
endif

LOCAL_SRC_FILES  := LibImgFun.cpp
LOCAL_MODULE     := Image_proc
include $(BUILD_SHARED_LIBRARY)