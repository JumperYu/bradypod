package com.bradypod.framework.design.creator.mode.factory;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-13:40
 * Desc:
 */
public class HpMouseFactory implements MouseFactory{
    @Override
    public Mouse produceMouse() {
        return new HpMouse();
    }
}
