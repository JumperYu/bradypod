package com.seewo.demo;

import java.util.ArrayList;
import java.util.List;

public class MenuModule implements Module {
	private List<String> menus;

	@Override
	public void destroy() {
	}

	@Override
	public void start() {
		menus = new ArrayList<String>();
		System.out.println("�˵�ģ��������");
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					printMenu(); // ��ӡ�˵��ķ���
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();

	}

	private void printMenu() {
		System.out.println("����ָ7��ĸ��ָ����˵�");
		System.out.println("************************************");
		for (int i = 0; i < menus.size(); i++) {
			System.out.println("\t" + (i + 1) + "��" + menus.get(i));
		}
		System.out.println("************************************");
	}

	/**
	 * ��Ӳ˵��ķ���
	 */
	public void addMenu(String menu) {
		this.menus.add(menu);
	}

	
	/**
	 * ɾ���˵��ķ���
	 */
	public void removeMenu(String menu){
		this.menus.remove(menu);
	}
	
	/**
	 * ���ò˵��ķ���
	 */
	public void resetMenu(){
		this.menus.clear();
	}
	
	@Override
	public void stop() {
	}

}
