package mvc.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Objects;

import mvc.model.Model;
import mvc.model.Worker.Worker;
import mvc.controller.Server;

public class DataDownloader extends Thread {
	protected Socket socket;
	
	public DataDownloader(Socket soc) {
		socket = soc;
	}
	@Override
	public void run() {
		ObjectOutputStream oos;
		try {
			Reader r = new InputStreamReader(socket.getInputStream());
			char[] cbuf = new char[128];
			r.read(cbuf);
			String cred = String.valueOf(cbuf);

			synchronized (Token.tokens) {
				TreeMap<String, Worker> t = null;
				String tok = cred.substring(1);
				Iterator<Entry<String,Long>> i = Token.tokens.entrySet().iterator();
				while(i.hasNext())
				{
					Map.Entry<String,Long> node = i.next();
					if(Objects.equals(node.getKey(),tok.trim())) {
						t=Model.Control.getWorkers();
						Token.tokens.remove(node.getKey());
						break;
					}
				}
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(t);
				oos.flush();
			}
			
		}catch(IOException e) {
			return;
		}
	}
	
}
