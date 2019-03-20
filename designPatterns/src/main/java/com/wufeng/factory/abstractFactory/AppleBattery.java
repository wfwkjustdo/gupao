package com.wufeng.factory.abstractFactory;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:01.
 */
public class AppleBattery implements IBattery {
    @Override
    public void chargeBattery() {
        System.out.println("Apple手机充电");
    }
}
