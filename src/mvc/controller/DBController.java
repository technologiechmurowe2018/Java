package mvc.controller;
import java.sql.*;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import mvc.model.Model;
import mvc.model.Worker.Dyrektor;
import mvc.model.Worker.Handlowiec;
import mvc.model.Worker.Worker;

public class DBController extends LocalController{
	Connection conn = null;
	public DBController(){
		Properties conp = new Properties();
		conp.put("user", "root");
		conp.put("password", "");
		conp.put("useJDBCCompliantTimezoneShift", "true");
		conp.put("serverTimezone", "UTC");
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workers",conp);
		} catch ( SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public TreeMap<String, Worker> getWorkers() {
		TreeMap<String, Worker> t = new TreeMap<String, Worker>();
		try {
			Statement replaceAll = conn.createStatement();
			ResultSet rs = replaceAll.executeQuery("SELECT * FROM `allofthem`;");
			while(rs.next()) {
				if(rs.getInt("Type")==1) {
					Worker tt = new Dyrektor(rs);
					t.put(tt.getPesel(),tt);
				}
				else {
					Worker tt = new Handlowiec(rs);
					t.put(tt.getPesel(),tt);
				}
			}
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setWorkers(TreeMap<String, Worker> workers) {
		try {
			Statement replaceAll = conn.createStatement();
			replaceAll.executeUpdate("DELETE FROM `allofthem` WHERE 1;");
			for(Entry<String, Worker> node : workers.entrySet()) 
			{
				Worker person= node.getValue();
				replaceAll.executeUpdate(person.insertQueryString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//replaceAll.addBatch(");
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void removeWorker(String Pesel) {
		try {
			Statement rm = conn.createStatement();
			rm.executeUpdate("DELETE FROM `allofthem` WHERE `Pesel` = " + Pesel+";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void addWorker(Worker worker) {
		try {
			Statement rm = conn.createStatement();
			rm.executeUpdate(worker.insertQueryString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}


	
}
