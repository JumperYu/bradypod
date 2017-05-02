package com.seewo.demo;

public class ServerStarter {
	public static void main(String[] args) {
		try {
			Class.forName("com.seewo.modules.WMZServer").newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}