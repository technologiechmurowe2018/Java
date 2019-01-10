package mvc.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeMap;

import mvc.model.Model;
import mvc.model.Worker.Worker;

public class SOAPController extends DataController{
	Socket connection;
	ObjectInputStream brinp; 
	String Token;
	public SOAPController(String Adres,String Port,String token) throws NumberFormatException, UnknownHostException, IOException{
			Token=token;
			connection = new Socket(InetAddress.getByName(Adres),Integer.parseInt(Port));
	}
	
	public TreeMap<String, Worker> getWorkers() {
		try {
			Writer w = new OutputStreamWriter(connection.getOutputStream());
			w.write(Token);
			w.flush();
			
			brinp = new ObjectInputStream(connection.getInputStream());
			TreeMap<String, Worker> t = (TreeMap<String, Worker>)brinp.readObject();
			if(t!=null) {
				Model.Control.setWorkers(t);
				return t;
			}
		}
		catch (Exception ex) {
		}
		return null;

	}

	@Override
	public void setWorkers(TreeMap<String, Worker> workers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeWorker(String Pesel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}
}
