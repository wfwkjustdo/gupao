package com.wufeng.factory.abstractFactory;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:02.
 */
public class HuaWeiBattery implements IBattery {
    @Override
    public void chargeBattery() {
        System.out.println("HuaWei手机充电");
    }
}
