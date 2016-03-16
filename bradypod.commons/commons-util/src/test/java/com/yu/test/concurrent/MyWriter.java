package com.yu.test.concurrent;

public class MyWriter {
    private StringBuilder sb = new StringBuilder();
    private int counter = 0;
 
    public synchronized void write(String s) {
        sb.append(++counter + "-" + s + " ");
    }
     
    public synchronized String toString(){
		return sb.toString();
	}
}
