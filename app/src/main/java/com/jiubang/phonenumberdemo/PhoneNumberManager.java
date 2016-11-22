package com.jiubang.phonenumberdemo;

import android.content.Context;

import com.jiubang.phonenumberdemo.strategy.IStrategy;
import com.jiubang.phonenumberdemo.strategy.StrategyFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linzewu
 * @date 16-11-15
 */
public class PhoneNumberManager {
    
    private static PhoneNumberManager sInstance;
    public static synchronized PhoneNumberManager getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PhoneNumberManager(context.getApplicationContext());
        }
        return sInstance;
    }
    private Context mContext;
    private IStrategy mIStrategy;
    private PhoneSpRecorder mPhoneSpRecorder;
    
    private PhoneNumberManager(Context context) {
        mContext = context;
        mPhoneSpRecorder = new PhoneSpRecorder(context);
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();
        mIStrategy = StrategyFactory.createStrategySmsDatabase();
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
    
    public String getPhoneNumber() {
        return mPhoneSpRecorder.getPhoneNumber();
    }
    
    private void readPhoneNumberRunnable() {
        mIStrategy.initStrategy(mContext);
        mIStrategy.getPhoneNumber();
    }
}
