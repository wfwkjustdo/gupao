package com.wufeng.factory.abstractFactory;

/**
 * @Author wangkai
 * @Create 2019/3/20-12:54.
 */
public interface PhoneFactory {
    IBattery createBattery();
    IHull createHull();
}
