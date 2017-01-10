package bradypod.framework.agent.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class GaClient {

	public static void main(String[] args) throws IOException {

		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 4321);
			OutputStream os = socket.getOutputStream();
			final PrintWriter out = new PrintWriter(os);
			InputStream in = socket.getInputStream();

			final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Thread readThread = new Thread(new Runnable() {

				@Override
				public void run() {
					String line = null;
					try {
						while (true) {
							if ((line = br.readLine()) != null) {
								out.println(line);
								out.flush();
							} else {
								out.println("....");
								out.flush();
							}
							try {
								TimeUnit.MILLISECONDS.sleep(10L);
							} catch (InterruptedException e) {
							}
						}
					} catch (IOException e) {
					}
				}
			});
			readThread.setDaemon(true);
			readThread.start();

			byte[] buf = new byte[1024];
			while (true) {
				int n = -1;
				while ((n = in.read(buf)) > 0) {
					System.out.println(new String(buf, 0, n));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		}

	}

}
