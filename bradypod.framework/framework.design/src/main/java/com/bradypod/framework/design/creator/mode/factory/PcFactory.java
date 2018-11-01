package com.bradypod.framework.design.creator.mode.factory;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-13:23
 * Desc: 抽象工厂模式, 区别于前2者区别在于将所有生产方式抽象
 */
public interface PcFactory {

    public Mouse produceMouse();

    public Keybo produceKeybo();

}
