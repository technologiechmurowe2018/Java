package mvc.controller;

import java.util.TreeMap;

import mvc.model.Worker.Worker;

public class LocalController extends DataController {
	private static TreeMap<String, Worker> persons = new TreeMap<String, Worker>();

	@Override
	public TreeMap<String, Worker> getWorkers() {
		return persons;
	}

	@Override
	public void setWorkers(TreeMap<String, Worker> workers) {
		persons = workers;
	}

	@Override
	public void removeWorker(String Pesel) {
		// TODO Auto-generated method stub
		persons.remove(Pesel);
	}

	@Override
	public void addWorker(Worker worker) {
		persons.put(worker.getPesel(), worker);
	}
	
}
