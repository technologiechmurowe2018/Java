package mvc.rmi;


import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class LoginServer {

	public static void main(String[] args) {
		ValidatorImpl aValidator;
		try {
			//System.setSecurityManager(new RMISecurityManager());
			//System.setProperty("java.security.policy", "file:///C:/Users/Ja/Desktop/Prog%20Java/java/Programowanie/src/policy.policy");
			System.setProperty("java.rmi.server.hostname", "127.0.0.1");
			System.setProperty("java.rmi.server.codebase", "file:///C:/Users/Ja/Desktop/Prog%20Java/java/Programowanie/src/");
			Registry rgsty = LocateRegistry.createRegistry(1888);
			aValidator = new ValidatorImpl();
			rgsty.rebind("validator", aValidator);
			
			System.out.println("Login server open for bussiness");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
