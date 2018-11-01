package com.bradypod.framework.design.creator.mode.singleton;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-14:34
 * Desc: 单例-饿汉
 */
public class SingletonHunge {

    private static final SingletonHunge INSTANCE = new SingletonHunge(); // 核心

    private SingletonHunge(){}

    public static SingletonHunge getInstance() {
        return SingletonHunge.INSTANCE;
    }
}
