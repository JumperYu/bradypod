package com.bradypod.framework.design.creator.mode.singleton;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/27-14:25
 * Desc: 单例-懒汉 多种写法 善于利用java的机制
 */
public class SingletonLazy {

    private static SingletonLazy instance = null;

    private SingletonLazy(){
    }

    /**
     *  线程不安全的一种写法
     */
    public static SingletonLazy getInstanceNotThreadSafe() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }

    /**
     *  引入锁 + 双重检测的一种优化写法
     */
    public static SingletonLazy getInstanceThreadSafe() {
        if (instance == null) {
            synchronized (SingletonLazy.class) {
                if (instance == null) {
                    instance = new SingletonLazy();
                }
            }
        }
        return instance;
    }

    /**
     *  类加载是线程互斥的 当第一次调用getInstance的时候可以保证实例只被初始化一次
     */
    private static class SingletonHolder {
        private static SingletonLazy instanceShadow = new SingletonLazy();
    }

    public static SingletonLazy getInstance() {
        return SingletonHolder.instanceShadow;
    }
}
