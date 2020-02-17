package com.example.threads;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyFirstThread extends Thread{
    private Handler mMainThreadHandler = null;
    private boolean isRunning = false;
    private CustomHandler mCustomHandler;
    public MyFirstThread(Handler mainThreadHandler) {
        mMainThreadHandler = mainThreadHandler;
        isRunning = true;
    }
    public void quit() {
        isRunning = false;
        mMainThreadHandler = null;
    }
    public void sendMessageToFirstThread(Message message) {
        while (true) {
            try {
                mCustomHandler.sendMessage(message);
                break;
            } catch (NullPointerException e) {
                Log.d("TAG", "sendMessageToFirstThread: " + e.getMessage());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        if (isRunning) {
            Looper.prepare();
            mCustomHandler = new CustomHandler(Looper.myLooper());
            Looper.loop();
        }
    }

    private class CustomHandler extends Handler{
        public CustomHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

        }
    }
}
