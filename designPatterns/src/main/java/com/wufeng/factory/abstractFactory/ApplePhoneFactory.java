package com.wufeng.factory.abstractFactory;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:04.
 */
public class ApplePhoneFactory implements PhoneFactory {
    @Override
    public IBattery createBattery() {
        return new AppleBattery();
    }

    @Override
    public IHull createHull() {
        return new AppleHull();
    }
}
