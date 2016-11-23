package com.jiubang.phonenumberdemo.strategy;

import android.content.Context;

/**
 * @author linzewu
 * @date 16-11-15
 */
public interface IStrategy {
    
    void initStrategy(Context context);
    
    String getPhoneNumber();
    
}
