package com.bradypod.framework.design.creator.mode.decorator;

/**
 * Github
 * User: previous_yu
 * Date: 2018/10/29-3:59
 * Desc:
 */
public class DecoractorTest {

    public static void main(String[] args) {
       Source source = new Source();
       Sourceable sourceable = new Decorator(source);
       sourceable.method();
    }
}