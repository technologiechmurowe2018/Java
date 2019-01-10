package mvc.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import mvc.model.Worker.Handlowiec;
import mvc.model.Worker.Worker;

public class HandlowiecAdapter extends XmlAdapter<Handlowiec[],ArrayList<Worker>>{

	@Override
	public Handlowiec[] marshal(ArrayList<Worker> arg0) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Handlowiec> han = new ArrayList<Handlowiec>();
		for (Worker n : arg0) {
			try {
				han.add((Handlowiec)n);
			}
			catch(ClassCastException e) {
			}
		}
		
		Handlowiec[] t = new Handlowiec[han.size()];
		int i=0;
		for (Handlowiec n : han) {
			t[i++]=n;
		}
		return t;
	}

	@Override
	public ArrayList<Worker> unmarshal(Handlowiec[] arg0) throws Exception {
		// TODO Auto-generated method stub
		return new ArrayList<Worker>(Arrays.asList(arg0));
	}

}
