package com.jiubang.phonenumberdemo.strategy;

import android.support.v4.util.ArrayMap;
import java.util.Map;

/**
 * 运营商映射表
 * @author linzewu
 * @date 16-11-18
 */
public class StrategySmsMappingTable {
    
    private static final String MCC_CHINA = "460";
    
    private static final String NUMBER_CHINA_MOBILE = "10086";
    private static final String NUMBER_CHINA_UNICOM = "10010";
    private static final String NUMBER_CHINA_TELECOM = "10000";
    
    private Map<String, String> mSmsMappingTable = new ArrayMap<String, String>();
    public StrategySmsMappingTable() {
        mSmsMappingTable.put(MCC_CHINA + "00", NUMBER_CHINA_MOBILE);
        mSmsMappingTable.put(MCC_CHINA + "01", NUMBER_CHINA_UNICOM);
        mSmsMappingTable.put(MCC_CHINA + "02", NUMBER_CHINA_MOBILE);
        mSmsMappingTable.put(MCC_CHINA + "03", NUMBER_CHINA_TELECOM);
        mSmsMappingTable.put(MCC_CHINA + "05", NUMBER_CHINA_TELECOM);
        mSmsMappingTable.put(MCC_CHINA + "06", NUMBER_CHINA_UNICOM);
        mSmsMappingTable.put(MCC_CHINA + "07", NUMBER_CHINA_MOBILE);
        mSmsMappingTable.put(MCC_CHINA + "11", NUMBER_CHINA_TELECOM);
    }

    /**
     * 通过MCC+MNC号码获取运营商号码
     * @param MCCMNC MCC+MNC号码
     * @return 运营商号码
     */
    public String getOperatorsCode(String MCCMNC) {
        return mSmsMappingTable.get(MCCMNC);
    }
    
}
