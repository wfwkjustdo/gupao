package com.wufeng.factory.abstractFactory;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:05.
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        ApplePhoneFactory applePhoneFactory = new ApplePhoneFactory();
        applePhoneFactory.createBattery().chargeBattery();
        applePhoneFactory.createHull().dressHull();

        HuaWeiPhoneFactory huaWeiPhoneFactory=new HuaWeiPhoneFactory();
        huaWeiPhoneFactory.createBattery().chargeBattery();
        huaWeiPhoneFactory.createHull().dressHull();
    }
}
