package com.bradypod.framework.design.creator.mode.decorator;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/29-3:54
 * Desc:
 */
public class Decorator implements Sourceable{

    private Sourceable sourceable;

    public Decorator(Sourceable sourceable){
        this.sourceable = sourceable;
    }

    @Override
    public void method() {
        System.out.println("before decorator");
        sourceable.method();
        System.out.println("after decorator");
    }
}
