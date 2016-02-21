package com.yu.test.alias;

class A {
	public void methodParent() {
		System.out.println("method in Parent");
	}
}

class B extends A {
	public void methodParent() {
		System.out.println("override method in Child");
	}

	public void methodChild() {
		System.out.println("method in Child");
	}
}

public class Main {

	public static void main(String[] args) {

		B[] b = new B[10];
		A[] a = b;

		/**
		 * The reason is that Java handles aliasing during run-time. During
		 * run-time, it knows that the first element should be a B object,
		 * instead of A.
		 * 
		 * Therefore, it only runs correctly if it is changed to:
		 * 
		 * B[] b = new B[10]; A[] a = b;
		 * 
		 * a[0] = new B(); 
		 * b[0].methodParent();
		 */
		a[0] = new B();
		b[0].methodParent();
	}
}