package com.jiubang.phonenumberdemo.strategy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author linzewu
 * @date 16-11-15
 */
public class StrategySmsDatabase implements IStrategy {

    private Context mContext;
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    private static final String DATABASE_KEY_ID = "_id"; //短信序号，如100
    private static final String DATABASE_KEY_BODY = "body";  //短信内容
    private static final String DATABASE_KEY_ADDRESS = "address"; //发件人地址，即手机号，如+86138138000
    private static final String DATABASE_KEY_PERSON = "person"; //发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
    private static final String DATABASE_KEY_DATE = "date";  //日期
    private static final String DATABASE_KEY_TYPE = "type";  //短信类型1是接收到的，2是已发出
    
//    public void getSmsFromPhone() {
//        ContentResolver cr = getContentResolver();
//        String[] projection = new String[] { "body" };//"_id", "address", "person",, "date", "type  
//        String where = " address = '1066321332' AND date >  "
//                + (System.currentTimeMillis() - 10 * 60 * 1000);
//        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
//        if (null == cur)
//            return;
//        if (cur.moveToNext()) {
//            String number = cur.getString(cur.getColumnIndex("address"));//手机号  
//            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表  
//            String body = cur.getString(cur.getColumnIndex("body"));
//            //这里我是要获取自己短信服务号码中的验证码~~  
//            Pattern pattern = Pattern.compile(" [a-zA-Z0-9]{10}");
//            Matcher matcher = pattern.matcher(body);
//            if (matcher.find()) {
//                String res = matcher.group().substring(1, 11);
//                mobileText.setText(res);
//            }
//        }
//    }

    private StrategySmsMappingTable mStrategySmsMappingTable;
    private TelephonyManager mTelephonyManager;
    
    @Override
    public void initStrategy(Context context) {
        this.mContext = context;
        mStrategySmsMappingTable = new StrategySmsMappingTable();
        mTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Override
    public void getPhoneNumber() {
        String targetAddress = mStrategySmsMappingTable.getOperatorsCode(
                mTelephonyManager.getSubscriberId().substring(0, 5)
        );
        
        ContentResolver contentResolver = mContext.getContentResolver();
        String[] projection = new String[] {DATABASE_KEY_ID, DATABASE_KEY_BODY, 
                DATABASE_KEY_DATE, DATABASE_KEY_ADDRESS, DATABASE_KEY_PERSON, DATABASE_KEY_TYPE};
//        String where = " person = \'" + targetAddress + "\'";
        Cursor cursor = contentResolver.query(SMS_INBOX, projection, null, null, "date desc");
        try {
            while (cursor.moveToNext()) {
                Log.d("Sms", cursor.getString(cursor.getColumnIndex(DATABASE_KEY_ADDRESS)) + "/"
                        + cursor.getString(cursor.getColumnIndex(DATABASE_KEY_TYPE)) + "/"
                        + cursor.getString(cursor.getColumnIndex(DATABASE_KEY_BODY))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }
}
