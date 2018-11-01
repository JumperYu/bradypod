package com.bradypod.framework.design.creator.mode.state;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/31-1:32
 * Desc:
 */
public class Context {

    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
