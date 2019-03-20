package com.wufeng.factory.factoryMethod;

import com.wufeng.vip3.student.factory.IPhone;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:49.
 */
public class PhoneFactoryTest {
    public static void main(String[] args) {
        PhoneFactory phoneFactory = new HuaWeiPhoneFactory();
        IPhone iPhone = phoneFactory.create();
        iPhone.getName();


        phoneFactory = new ApplePhoneFactory();
        iPhone = phoneFactory.create();
        iPhone.getName();
    }
}
