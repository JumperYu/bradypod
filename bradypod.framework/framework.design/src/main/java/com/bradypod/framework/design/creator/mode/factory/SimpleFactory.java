package com.bradypod.framework.design.creator.mode.factory;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-13:19
 * Desc: 简单工厂模式, 使用输入指令区别是哪个厂家的实现
 */
public class SimpleFactory {

    public Mouse produceMouse(int type) {
        if(type == 1) {
            return new DeilMouse();
        } else if(type == 2) {
            return new HpMouse();
        } else {
            System.out.println("未知类型");
            return null;
        }
    }
}
