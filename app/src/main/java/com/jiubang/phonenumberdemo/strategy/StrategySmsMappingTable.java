package com.jiubang.phonenumberdemo.strategy;

import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * 运营商映射表
 * @author linzewu
 * @date 16-11-18
 */
public class StrategySmsMappingTable {
    
    private Map<String, String> mSmsMappingTable = new ArrayMap<String, String>();
    public StrategySmsMappingTable() {
        mSmsMappingTable.put("460" + "", "");
        mSmsMappingTable.put("460" + "", "");
        mSmsMappingTable.put("460" + "", "");
    }

    /**
     * 通过MCC+MNC号码获取运营商号码
     * @param MCCMNC MCC+MNC号码
     * @return 运营商号码
     */
    public String getOperatorsCode(String MCCMNC) {
        return null; 
    }
    
}
