package com.wufeng.factory.factoryMethod;

import com.wufeng.vip3.student.factory.ApplePhone;
import com.wufeng.vip3.student.factory.IPhone;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:48.
 */
public class ApplePhoneFactory implements PhoneFactory {
    @Override
    public IPhone create() {
        return new ApplePhone();
    }
}
