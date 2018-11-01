package com.bradypod.framework.design.creator.mode.state;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/31-1:35
 * Desc: 我认为这是一种很棒的设计模式，用于传递对象的上下文
 */
public class StateTest {

    public static void main(String[] args) {
        Context context = new Context();

        StartState startState = new StartState();
        startState.action(context);

        System.out.println(context.getState());

        StopState stopState = new StopState();
        stopState.action(context);

        System.out.println(context.getState());

    }
}
