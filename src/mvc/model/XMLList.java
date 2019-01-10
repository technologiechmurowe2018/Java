package mvc.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mvc.model.Worker.Worker;

@XmlRootElement
public class XMLList{
	private ArrayList<Worker> work;
	
	public XMLList () {
		work = new ArrayList<Worker>();
	}
	@XmlJavaTypeAdapter(DyrektorAdapter.class)
	public ArrayList<Worker> getDyrektorList(){
		return work;
	}
	@XmlJavaTypeAdapter(HandlowiecAdapter.class)
	public ArrayList<Worker> getHandlowiecList(){
		return work;
	}
	
	public void setHandlowiecList(ArrayList<Worker> Han){
		work.addAll(Han);
	}
	
	public void setDyrektorList(ArrayList<Worker> Han){
		work.addAll(Han);
	}
}