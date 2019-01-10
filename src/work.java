import mvc.controller.*;
import mvc.rmi.LoginServer;
public class work {
	public static void main(String[] args) {
		new Server().start();
		LoginServer.main(new String[]{"work"});
		Controller.Menu();
		Server.End();
	}
}
