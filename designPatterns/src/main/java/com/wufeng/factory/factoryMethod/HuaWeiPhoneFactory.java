package com.wufeng.factory.factoryMethod;

import com.wufeng.vip3.student.factory.HuaWeiPhone;
import com.wufeng.vip3.student.factory.IPhone;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:47.
 */
public class HuaWeiPhoneFactory implements PhoneFactory {
    @Override
    public IPhone create() {
        return new HuaWeiPhone();
    }
}
