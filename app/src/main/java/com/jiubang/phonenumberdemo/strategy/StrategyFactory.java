package com.jiubang.phonenumberdemo.strategy;

/**
 * @author linzewu
 * @date 16-11-15
 */
public class StrategyFactory {
    
    public StrategySimCard createSimCardStrategy() {
        return new StrategySimCard();
    }
    
    public StrategySmsDatabase createStrategySmsDatabase() {
        return new StrategySmsDatabase();
    }
    
}
