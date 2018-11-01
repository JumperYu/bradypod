package com.bradypod.framework.design.creator.mode.state;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/31-1:33
 * Desc:
 */
public class StopState implements State{

    @Override
    public void action(Context context) {

        System.out.println("stop");

        context.setState(this);
    }

    @Override
    public String toString() {
        return "stop state";
    }
}
