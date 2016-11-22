package com.jiubang.phonenumberdemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author linzewu
 * @date 16-11-22
 */
public class PhoneSpRecorder {
    
    private static final String SP_NAME = "Phone Number";
    private SharedPreferences mSharedPreferences;
    
    public PhoneSpRecorder(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
        }
    }
    
    public String getPhoneNumber() {
        return mSharedPreferences.getString("phone number", "");
    }
    
    public void setPhoneNumber(String phoneNumber) {
        mSharedPreferences.edit().putString("phone number", phoneNumber).commit();
    }
    
}
