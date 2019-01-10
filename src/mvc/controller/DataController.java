package mvc.controller;

import java.util.TreeMap;
import mvc.model.Worker.Worker;

public abstract class DataController {
	public abstract TreeMap<String, Worker> getWorkers();
	public abstract void setWorkers(TreeMap<String, Worker> workers);
	public abstract void removeWorker(String Pesel);
	public abstract void addWorker(Worker worker);
}
