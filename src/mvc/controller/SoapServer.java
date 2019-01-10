package mvc.controller;

import java.util.TreeMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mvc.model.Model;
import mvc.model.Worker.Worker;

@WebService
public class SoapServer {
	public SoapServer() {}
	
	@WebMethod
	public TreeMap<String, Worker> getWorkers(String token){
		return Model.Control.getWorkers();
	}
}
