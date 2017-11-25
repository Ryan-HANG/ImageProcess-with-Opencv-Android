package com.example.ryan.imagedection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView = null;
    Button btnNDK = null;
    Button btnRestore = null;
    Button btnGray = null;
    Bitmap img = null;
    Bitmap resultImg = null;
    private static final String TAG = "MainActivity";

    //OpenCV类库加载并初始化成功后的回调函数，在此我们不进行任何操作
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    System.loadLibrary("Image_proc");
                    Log.i(TAG, "成功加载");
                }
                break;
                default: {
                    super.onManagerConnected(status);
                    Log.i(TAG, "加载失败");
                }
                break;
            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRestore = (Button) findViewById(R.id.btnRestore);
        btnNDK = (Button) findViewById(R.id.btnNDK);
        btnGray = (Button) findViewById(R.id.btnGray);
        imageView = (ImageView) findViewById(R.id.ImageView01);
        img = ((BitmapDrawable) getResources().getDrawable(R.drawable.syz)).getBitmap();
        imageView.setImageBitmap(img);
        btnRestore.setOnClickListener(this);
        btnNDK.setOnClickListener(this);
        btnGray.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void procSrc2Gray() {
        int w = img.getWidth();
        int h = img.getHeight();
        int[] pixels = new int[w * h];
        img.getPixels(pixels, 0, w, 0, 0, w, h);
        int[] resultInt = LibImgFun.grayProc(pixels, w, h);
        resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);
    }

//    /**
//     * 图片按比例大小压缩方法
//     *
//     * @param srcPath （根据路径获取图片并压缩）
//     * @return
//     */
//    public static Bitmap getimage(String srcPath) {
//
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
//
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;// 这里设置高度为800f
//        float ww = 480f;// 这里设置宽度为480f
//        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;// be=1表示不缩放
//        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        newOpts.inSampleSize = be;// 设置缩放比例
//        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
//    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnNDK) {
            //  long current = System.currentTimeMillis();
            Bitmap img1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.syz)).getBitmap();

//            BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
//            bfoOptions.inScaled = false;
//            Bitmap img1 = BitmapFactory.decodeResource(getResources(), R.drawable.syz, bfoOptions);

            Matrix matrix = new Matrix();
            matrix.setScale(0.5f, 0.5f);
            Bitmap img2 = Bitmap.createBitmap(img1, 0, 0, img1.getWidth(),
                    img1.getHeight(), matrix, true);


            int w = img2.getWidth(), h = img2.getHeight();
            int[] pix = new int[w * h];
            img2.getPixels(pix, 0, w, 0, 0, w, h);
            int[] resultInt = LibImgFun.imgFun(pix, w, h);
            Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);
            //  long performance = System.currentTimeMillis() - current;
            imageView.setImageBitmap(resultImg);
            //  MainActivity.this.setTitle("w:" + String.valueOf(img1.getWidth()) + ",h:" + String.valueOf(img1.getHeight()) + "NDK耗时" + String.valueOf(performance) + " 毫秒");
            MainActivity.this.setTitle("OpenCV的Canny检测图");
        } else if (v == btnGray) {
            procSrc2Gray();
            imageView.setImageBitmap(resultImg);
            MainActivity.this.setTitle("OpenCV灰度化");
        } else if (v == btnRestore) {
            Bitmap img2 = ((BitmapDrawable) getResources().getDrawable(R.drawable.syz)).getBitmap();
            imageView.setImageBitmap(img2);
            MainActivity.this.setTitle("原始图像");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //通过OpenCV引擎服务加载并初始化OpenCV类库，所谓OpenCV引擎服务即是
        //OpenCV_2.4.3.2_Manager_2.4_*.apk程序包，存在于OpenCV安装包的apk目录中
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ryan.imagedection/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ryan.imagedection/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //  public  native String getStringFromJNI();
}
