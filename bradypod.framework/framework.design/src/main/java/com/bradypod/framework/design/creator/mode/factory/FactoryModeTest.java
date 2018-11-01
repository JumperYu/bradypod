package com.bradypod.framework.design.creator.mode.factory;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-13:26
 * Desc:
 */
public class FactoryModeTest {

    public static void main(String[] args) {
        // 01 简单工厂 由入参决定是哪个厂商的实现
        Mouse mouse = new SimpleFactory().produceMouse(1);
        mouse.click();

        // 02 工厂模式/工厂方法
        MouseFactory mouseFactory = new DeilMouseFactory();
        Mouse deilMouse = mouseFactory.produceMouse();
        deilMouse.click();

        // 03 抽象工厂
        PcFactory deilPcFactory = new DeilPcFactory();
        deilPcFactory.produceMouse().click();
    }

}
