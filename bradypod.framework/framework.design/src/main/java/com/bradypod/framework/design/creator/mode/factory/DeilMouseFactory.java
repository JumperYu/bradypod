package com.bradypod.framework.design.creator.mode.factory;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-13:22
         * Desc:
         */
public class DeilMouseFactory implements MouseFactory {
    @Override
    public Mouse produceMouse() {
        return new DeilMouse();
    }
}
