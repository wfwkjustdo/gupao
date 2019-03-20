package com.wufeng.factory.abstractFactory;

/**
 * @Author wangkai
 * @Create 2019/3/20-13:00.
 */
public class AppleHull implements IHull {
    @Override
    public void dressHull() {
        System.out.println("苹果手机戴上外壳");
    }
}
