package com.yu.test.forandswitch;

import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

public class ForAndSwitchTest {

	@Test
	public void testDate() {
		Date date = new Date(1444700265814L); //1444700265814
		System.out.println(date);
	}

	@Test
	public void testFor() {
		long start = System.currentTimeMillis();
		ThreadPool threadPool = new ThreadPool(1000);
		threadPool.executeThread(new ThreadWorker() {

			@Override
			public void execute() {
				System.out.println(setBySwitch(RandomUtils.nextInt(6))); // 75
				// System.out.println(setByIf(RandomUtils.nextInt(6))); // > 80
			}
		});
		long end = System.currentTimeMillis();
		System.out.println("------------ finish: " + (end - start));
	}

	public Star setBySwitch(int starNum) {
		Star star = new Star();
		switch (starNum) {
		case 1:
			star.setOne(1);
			break;
		case 2:
			star.setTwo(2);
			break;
		case 3:
			star.setThree(3);
			break;
		case 4:
			star.setFour(4);
			break;
		case 5:
			star.setFive(5);
			break;
		default:
			break;
		}
		return star;
	}

	public Star setByIf(int starNum) {
		Star star = new Star();
		if (starNum == 1) {
			star.setOne(1);
		} else if (starNum == 2) {
			star.setTwo(2);
		} else if (starNum == 2) {
			star.setTwo(2);
		} else if (starNum == 3) {
			star.setThree(3);
		} else if (starNum == 4) {
			star.setFour(4);
		} else if (starNum == 5) {
			star.setFive(5);
		}
		return star;
	}
}

class Star {

	private int one;
	private int two;
	private int three;
	private int four;
	private int five;

	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}

	public int getTwo() {
		return two;
	}

	public void setTwo(int two) {
		this.two = two;
	}

	public int getThree() {
		return three;
	}

	public void setThree(int three) {
		this.three = three;
	}

	public int getFour() {
		return four;
	}

	public void setFour(int four) {
		this.four = four;
	}

	public int getFive() {
		return five;
	}

	public void setFive(int five) {
		this.five = five;
	}

	@Override
	public String toString() {
		return "Star [one=" + one + ", two=" + two + ", three=" + three + ", four=" + four
				+ ", five=" + five + "]";
	}

}
