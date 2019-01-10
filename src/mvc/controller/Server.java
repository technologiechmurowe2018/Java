package mvc.controller;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	static final int PORT = 1978;
	static boolean running = false;
	static ServerSocket serverSocket = null;
	@Override
	public void run() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			running = true;
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(running) {
						Long curTime = Calendar.getInstance().getTimeInMillis();
						synchronized (Token.tokens) {
							Iterator<Entry<String,Long>> i = Token.tokens.entrySet().iterator();
							while(i.hasNext())
							{
								Map.Entry<String,Long> node = i.next();
								if(curTime - node.getValue() > 120000) {
									Token.tokens.remove(node.getKey());
								}
							}
						}
					}
				}
			}).start();
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		while(running) {
			try {
				socket = serverSocket.accept();
			}catch(Exception e) {
				continue;
			}
			new DataDownloader(socket).start();
		}
		
	}
	public static void End() {
		running = false;
		try {
			if(serverSocket!=null)
				serverSocket.close();
		}
		catch(Exception e) {
			
		}
	}
}
