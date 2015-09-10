package com.yu.nio;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeServerHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close(true);
			return;
		}

		Date date = new Date();
		session.write(date.toString());
		log.info("Message written...");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		log.info("IDLE " + session.getIdleCount(status));
	}

	private Logger log = LoggerFactory.getLogger(TimeServerHandler.class);
}
