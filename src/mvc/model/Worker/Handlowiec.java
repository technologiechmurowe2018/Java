package mvc.model.Worker;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mvc.controller.Server;
@XmlRootElement
public class Handlowiec extends Worker {
	private static final long serialVersionUID = 1L;
	@XmlElement private int provision;
	public Handlowiec() {}
	
	public Handlowiec(String _pesel,String Name, String LastName, BigDecimal Income, BigDecimal Limit,int Phone, int Provision)
	{
		super(_pesel,Name,LastName,Income, Limit,Phone);
		provision=Provision;
	}
	public Handlowiec(ResultSet rs) throws SQLException {
		super(  rs.getString("Pesel"),
				rs.getString("Name"),
				rs.getString("LastName"),
				rs.getBigDecimal("Income"),
				rs.getBigDecimal("Limite"),
				rs.getInt("Phone")
				);
		provision = rs.getInt("Provision");
	}
	public String toString()
	{
		String output=super.toString();
		output+="Prowizja (%)\t\t\t:\t"+provision+"\n";
		output+="Limit prowizji/miesiac (zl)\t:\t"+limit+"\n";
		return output;
	}
	@Override
	public String insertQueryString() {
		String ret = "INSERT INTO `allofthem` (`Pesel`, `Type`, `Name`, `LastName`, `Income`, `Limite`, `Phone`, `Provision`, `Card`, `Addictonal`) VALUES ('"+pesel+"', '0', '"+name+"', '"+lastName+"', '"+income.toString()+"', '"+limit.toString()+"', '"+String.valueOf(phone)+"', '"+String.valueOf(provision)+"', '', '0.00');";
		return ret;
	}
}
