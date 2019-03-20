package com.wufeng.factory.abstractFactory;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:02.
 */
public class HuaWeiPhoneFactory implements PhoneFactory{
    @Override
    public IBattery createBattery() {
        return new HuaWeiBattery();
    }

    @Override
    public IHull createHull() {
        return new HuaWeiHull();
    }
}
