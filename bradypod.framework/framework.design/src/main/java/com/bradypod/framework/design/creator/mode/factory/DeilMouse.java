package com.bradypod.framework.design.creator.mode.factory;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-13:17
 * Desc: 戴尔厂商实现鼠标的标准
 */
public class DeilMouse implements Mouse{
    @Override
    public void click() {
        System.out.println("Deil mouse click");
    }
}
