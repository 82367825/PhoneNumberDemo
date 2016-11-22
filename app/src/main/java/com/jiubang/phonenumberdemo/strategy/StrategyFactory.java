package com.jiubang.phonenumberdemo.strategy;

/**
 * @author linzewu
 * @date 16-11-15
 */
public class StrategyFactory {
    
    public static StrategySimCard createSimCardStrategy() {
        return new StrategySimCard();
    }
    
    public static StrategySmsDatabase createStrategySmsDatabase() {
        return new StrategySmsDatabase();
    }
    
}
