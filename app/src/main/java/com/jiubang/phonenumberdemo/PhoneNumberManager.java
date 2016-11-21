package com.jiubang.phonenumberdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linzewu
 * @date 16-11-15
 */
public class PhoneNumberManager {
    
    private static PhoneNumberManager sInstance;
    public static synchronized PhoneNumberManager getsInstance() {
        if (sInstance == null) {
            sInstance = new PhoneNumberManager();
        }
        return sInstance;
    }
    
    private PhoneNumberManager() {
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    }
    
    private ExecutorService mSingleThreadExecutor;
    
    public void init() {
        mSingleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                readPhoneNumberRunnable();
            }
        });
    }
    
    private void readPhoneNumberRunnable() {
        
    }
}
