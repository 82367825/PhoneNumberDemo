package com.jiubang.phonenumberdemo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

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
    
    private static final int MSG_READ_FROM_SIM_CARD = 1;
    private static final int MSG_READ_FROM_SMS = 2;
    private static final int MSG_SHOW_NUMBER = 3;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_READ_FROM_SIM_CARD:
                    Toast.makeText(mContext, "read from sim card", Toast.LENGTH_SHORT).show();
                    threadReadSimCard();
                    break;
                case MSG_READ_FROM_SMS:
                    Toast.makeText(mContext, "read from sms", Toast.LENGTH_SHORT).show();
                    threadReadSms();
                    break;
                case MSG_SHOW_NUMBER:
                    Toast.makeText(mContext, mPhoneSpRecorder.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    
    private PhoneNumberManager(Context context) {
        mContext = context;
        mPhoneSpRecorder = new PhoneSpRecorder(context);
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    }
    
    private ExecutorService mSingleThreadExecutor;
    
    public void init() {
        mHandler.sendEmptyMessage(MSG_READ_FROM_SIM_CARD);
    }
    
    private void threadReadSimCard() {
        mSingleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String phoneNumber;
                mIStrategy = StrategyFactory.createSimCardStrategy();
                mIStrategy.initStrategy(mContext);
                phoneNumber = mIStrategy.getPhoneNumber();
                if (phoneNumber == null || phoneNumber.equals("")) {
                    mHandler.sendEmptyMessage(MSG_READ_FROM_SMS);
                } else {
                    mHandler.sendEmptyMessage(MSG_SHOW_NUMBER);
                }
            }
        });
    }
    
    private void threadReadSms() {
        mSingleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String phoneNumber;
                mIStrategy = StrategyFactory.createStrategySmsDatabase();
                mIStrategy.initStrategy(mContext);
                phoneNumber = mIStrategy.getPhoneNumber();
                if (phoneNumber == null || phoneNumber.equals(""))  {
                    mHandler.sendEmptyMessage(MSG_SHOW_NUMBER);
                } else {
                    mHandler.sendEmptyMessage(MSG_SHOW_NUMBER);
                }
            }
        });
    }
    
    public String getPhoneNumber() {
        return mPhoneSpRecorder.getPhoneNumber();
    }
    
}
