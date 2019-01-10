package mvc.model.Worker;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public abstract class Worker implements Serializable {
	@XmlElement protected static final long serialVersionUID = 1L;
	@XmlElement protected String pesel;
	@XmlElement protected String name;
	@XmlElement protected String lastName;
	@XmlElement protected BigDecimal income;
	@XmlElement protected int phone;
	@XmlElement protected BigDecimal limit;
	public Worker(){}
	
	public Worker(String _pesel,String Name, String LastName, BigDecimal Income, BigDecimal Limit,int Phone){
		pesel=_pesel;
		name=Name;
		lastName=LastName;
		income=Income;
		phone=Phone;
		limit=Limit;
	}
	public String toString()
	{
		String output="";
		output+="Identyfikator Pesel\t\t:\t"+pesel+"\n";
		output+="Imie\t\t\t\t:\t"+name+"\n";
		output+="Nazwisko\t\t\t:\t"+lastName+"\n";
		output+="Stanowisko\t\t\t:\t"+this.getClass().getSimpleName()+"\n";
		output+="Wynagrodzenie (zl)\t\t:\t"+income+"\n";
		output+="Telefon sluzbowy numer\t\t:\t";
			if (phone!=-1)
				output+=phone+"\n";
			else
				output+="-brak-\n";
		return output;
	}
	public String getPesel(){return pesel;}
	
	public String insertQueryString() {
		return null;
	}
}
