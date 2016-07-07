package framework.config.schedule;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {

	public static void main(String[] args) {
		final Timer timer = new Timer(false);
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				System.out.println(1);
				timer.cancel();
			}
		};
		timer.schedule(timerTask, 3);
	}

}
