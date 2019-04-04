package com.wong.diskiooptimize;

import android.content.SharedPreferences;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeToExternalStorageInMainThread();//在主线程中执行

    }

    public void writeToExternalStorageInMainThread() {


        //在主线程中对文件进行读写操作出现违例的代码
//        File externalStorage = Environment.getExternalStorageDirectory();
//        File destFile = new File(externalStorage, "hello.txt");
//        OutputStream output = null;
//        try {
//            output = new FileOutputStream(destFile, true);
//            output.write("I am testing io".getBytes());
//            output.flush();
//            output.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(output != null){
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


        //解决违例
        new Thread(new Runnable() {
            @Override
            public void run() {
                File externalStorage = Environment.getExternalStorageDirectory();
                File destFile = new File(externalStorage, "hello.txt");
                OutputStream output = null;
                try {
                    output = new FileOutputStream(destFile, true);
                    output.write("I am testing io".getBytes());
                    output.flush();
                    output.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(output != null){
                        try {
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


    }
}
