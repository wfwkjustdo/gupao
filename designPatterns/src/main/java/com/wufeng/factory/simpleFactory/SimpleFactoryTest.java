package com.wufeng.factory.simpleFactory;

import com.wufeng.vip3.student.factory.ApplePhone;
import com.wufeng.vip3.student.factory.IPhone;

/**
 * @Author wangkai
 * @Create 2019/3/19-20:41.
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        IPhone iPhone1 = new ApplePhone();
        iPhone1.getName();

        PhoneFactory phoneFactory = new PhoneFactory();
        IPhone iPhone2 = phoneFactory.create1("iPhone");
        iPhone2.getName();


        IPhone iPhone3=phoneFactory.create2("com.wufeng.vip3.student.factory.HuaWeiPhone");
        iPhone3.getName();
    }
}
