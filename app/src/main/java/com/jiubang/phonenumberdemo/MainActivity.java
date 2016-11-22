package com.jiubang.phonenumberdemo;

import android.app.Activity;
import android.os.Bundle;

import com.jiubang.phonenumberdemo.strategy.IStrategy;

/**
 * @author linzewu
 * @date 16-11-22
 */
public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        PhoneNumberManager.getsInstance(this).init();
    }
}
