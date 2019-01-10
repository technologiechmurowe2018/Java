package mvc.model.Worker;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Dyrektor extends Worker {
	private static final long serialVersionUID = 1L;
	@XmlElement private BigDecimal addictonal;
	@XmlElement private String card;
	public Dyrektor() {}
	
	public Dyrektor(String _pesel,String Name, String LastName, BigDecimal Income, BigDecimal Limit,int Phone, String Card, BigDecimal Addictonal)
	{
		super(_pesel,Name, LastName, Income, Limit, Phone);
		card=Card;
		addictonal=Addictonal;
	}
	
	public Dyrektor(ResultSet rs) throws SQLException {
		super(  rs.getString("Pesel"),
				rs.getString("Name"),
				rs.getString("LastName"),
				rs.getBigDecimal("Income"),
				rs.getBigDecimal("Limite"),
				rs.getInt("Phone")
				);
		card= rs.getString("Card");
		addictonal = rs.getBigDecimal("Addictonal");
	}
	public String toString()
	{
		String output=super.toString();
		output+="Dodatek sluzbowy (zl)\t\t:\t"+addictonal+"\n";
		output+="Karta sluzbowa numer\t\t:\t"+card+"\n";
		output+="Limit kosztow/miesiac (zl)\t:\t"+limit+"\n";
		return output;
	}
	@Override
	public String insertQueryString() {
		String ret = "INSERT INTO `allofthem` (`Pesel`, `Type`, `Name`, `LastName`, `Income`, `Limite`, `Phone`, `Provision`, `Card`, `Addictonal`) VALUES ('"+pesel+"', '1', '"+name+"', '"+lastName+"', '"+income.toString()+"', '"+limit.toString()+"', '"+String.valueOf(phone)+"', '0.00', '"+card+"', '"+addictonal.toString()+"');";
		return ret;
	}
}
