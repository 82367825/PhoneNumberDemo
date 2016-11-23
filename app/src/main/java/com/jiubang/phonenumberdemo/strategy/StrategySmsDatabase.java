package com.jiubang.phonenumberdemo.strategy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

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
        if (targetAddress == null) {
            return ;
        }
        ContentResolver contentResolver = mContext.getContentResolver();
        String[] projection = new String[] {DATABASE_KEY_ID, DATABASE_KEY_BODY, 
                DATABASE_KEY_DATE, DATABASE_KEY_ADDRESS, DATABASE_KEY_PERSON, DATABASE_KEY_TYPE};
        String where = " address = \'" + targetAddress + "\'";
        final Cursor cursor = contentResolver.query(SMS_URI, projection, where, null, "date desc");
        try {
            while (cursor.moveToNext()) {
                String logString =
                        cursor.getString(cursor.getColumnIndex(DATABASE_KEY_PERSON)) + "/"
                        + cursor.getString(cursor.getColumnIndex(DATABASE_KEY_ADDRESS)) + "/"
                        + cursor.getString(cursor.getColumnIndex(DATABASE_KEY_TYPE)) + "/"
                        + cursor.getString(cursor.getColumnIndex(DATABASE_KEY_BODY));
                Log.d("Sms", logString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }
}
