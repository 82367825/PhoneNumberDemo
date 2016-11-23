package com.jiubang.phonenumberdemo.strategy;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.jiubang.phonenumberdemo.PhoneSpRecorder;

/**
 * @author linzewu
 * @date 16-11-15
 */
public class StrategySimCard implements IStrategy {

    private TelephonyManager mTelephonyManager;
    private PhoneSpRecorder mPhoneSpRecorder;
    
    @Override
    public void initStrategy(Context context) {
        mTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneSpRecorder = new PhoneSpRecorder(context);
    }

    @Override
    public String getPhoneNumber() {
        String phoneNumber = mTelephonyManager.getLine1Number();
        if (phoneNumber != null && !phoneNumber.equals("")) {
            mPhoneSpRecorder.setPhoneNumber(phoneNumber);
        }
        return phoneNumber;
    }
}
