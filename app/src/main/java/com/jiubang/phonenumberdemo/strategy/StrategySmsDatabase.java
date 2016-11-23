package com.jiubang.phonenumberdemo.strategy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jiubang.phonenumberdemo.PhoneSpRecorder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author linzewu
 * @date 16-11-15
 */
public class StrategySmsDatabase implements IStrategy {

    private Context mContext;
    private Uri SMS_URI = Uri.parse("content://sms/");
    private static final String DATABASE_KEY_ID = "_id"; //短信序号，如100
    private static final String DATABASE_KEY_BODY = "body";  //短信内容
    private static final String DATABASE_KEY_ADDRESS = "address"; //发件人地址，即手机号，如+86138138000
    private static final String DATABASE_KEY_PERSON = "person"; //发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
    private static final String DATABASE_KEY_DATE = "date";  //日期
    private static final String DATABASE_KEY_TYPE = "type";  //短信类型1是接收到的，2是已发出
    
    private StrategySmsMappingTable mStrategySmsMappingTable;
    private TelephonyManager mTelephonyManager;
    private PhoneSpRecorder mPhoneSpRecorder;
    
    @Override
    public void initStrategy(Context context) {
        this.mContext = context;
        mStrategySmsMappingTable = new StrategySmsMappingTable();
        mTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneSpRecorder = new PhoneSpRecorder(context);
    }

    @Override
    public String getPhoneNumber() {
        String targetAddress = mStrategySmsMappingTable.getOperatorsCode(
                mTelephonyManager.getSubscriberId().substring(0, 5)
        );
        if (targetAddress == null) {
            return null;
        }
        ContentResolver contentResolver = mContext.getContentResolver();
        String[] projection = new String[] {DATABASE_KEY_ID, DATABASE_KEY_BODY, 
                DATABASE_KEY_DATE, DATABASE_KEY_ADDRESS, DATABASE_KEY_PERSON, DATABASE_KEY_TYPE};
        String where = " address = \'" + targetAddress + "\'";
        final Cursor cursor = contentResolver.query(SMS_URI, projection, where, null, "date desc");
        try {
            while (cursor.moveToNext()) {
                String phoneNumber = 
                    parseSmsBody(cursor.getString(cursor.getColumnIndex(DATABASE_KEY_BODY)));
                if (phoneNumber != null && !phoneNumber.equals("")) {
                    mPhoneSpRecorder.setPhoneNumber(phoneNumber);
                    return phoneNumber;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }
    
    private String parseSmsBody(String smsBody) {
        if (smsBody == null || smsBody.equals("")) {
            return null;
        }
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(smsBody);
        StringBuffer bf = new StringBuffer(64);
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }
}
