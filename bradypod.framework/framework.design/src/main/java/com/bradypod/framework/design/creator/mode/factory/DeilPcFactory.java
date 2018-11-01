package com.bradypod.framework.design.creator.mode.factory;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-13:25
 * Desc:
 */
public class DeilPcFactory implements PcFactory{

    @Override
    public Mouse produceMouse() {
        return new DeilMouse();
    }

    @Override
    public Keybo produceKeybo() {
        return null;
    }
}
