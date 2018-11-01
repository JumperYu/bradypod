package com.bradypod.framework.design.creator.mode.template;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/31-0:12
 * Desc:
 */
public abstract class Game {

    abstract void init();

    abstract void start();

    abstract void stop();

    final void play() {
        init();
        start();
        stop();
    }

}
