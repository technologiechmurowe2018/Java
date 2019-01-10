package mvc.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Validator extends Remote{
	 String validate(String login,String password) throws RemoteException;
}
