package com.wufeng.factory;

/**
 * @Author wangkai
 * @Create 2019/3/19-20:28.
 */
public class ApplePhone implements IPhone {
    @Override
    public void getName() {
        System.out.println("苹果手机");
    }
}
