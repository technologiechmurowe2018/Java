package mvc.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import mvc.model.Worker.Dyrektor;
import mvc.model.Worker.Handlowiec;
import mvc.model.Worker.Worker;

public class DyrektorAdapter extends XmlAdapter<Dyrektor[],ArrayList<Worker>>{

	@Override
	public Dyrektor[] marshal(ArrayList<Worker> arg0) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Dyrektor> han = new ArrayList<Dyrektor>();
		for (Worker n : arg0) {
			try {
				han.add((Dyrektor)n);
			}
			catch(ClassCastException e) {
			}
		}
		
		Dyrektor[] t = new Dyrektor[han.size()];
		int i=0;
		for (Dyrektor n : han) {
			t[i++]=n;
		}
		return t;
	}

	@Override
	public ArrayList<Worker> unmarshal(Dyrektor[] arg0) throws Exception {
		// TODO Auto-generated method stub
		return new ArrayList<Worker>(Arrays.asList(arg0));
	}

}