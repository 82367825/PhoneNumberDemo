package com.jiubang.phonenumberdemo;

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
    
    
    
}
