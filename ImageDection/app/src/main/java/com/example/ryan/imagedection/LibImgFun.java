package com.example.ryan.imagedection;

import java.lang.annotation.Native;

/**
 * Created by Ryan on 2017/11/24.
 */
public class LibImgFun {
    public static native int[] imgFun(int[] buf, int w, int h);
    public static native int[] grayProc(int[] buf, int w, int h);
}
