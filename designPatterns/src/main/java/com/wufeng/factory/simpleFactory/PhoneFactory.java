package com.wufeng.factory.simpleFactory;

import com.wufeng.vip3.student.factory.ApplePhone;
import com.wufeng.vip3.student.factory.HuaWeiPhone;
import com.wufeng.vip3.student.factory.IPhone;

/**
 * @Author wangkai
 * @Create 2019/3/19-20:31.
 */
public class PhoneFactory {
    public IPhone create1(String name) {
        if ("iPhone".equals(name)) {
            return new ApplePhone();
        } else if ("HuaWei".equals(name)) {
            return new HuaWeiPhone();
        }
        return null;
    }


    public IPhone create2(String className) {
        try {
            if (!(null == className) || "".equals(className)) {
                return (IPhone) Class.forName(className).newInstance();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
