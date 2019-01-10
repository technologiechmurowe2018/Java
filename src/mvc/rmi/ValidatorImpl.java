package mvc.rmi;


import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mvc.controller.Token;

public class ValidatorImpl extends UnicastRemoteObject implements Validator {
	private static final long serialVersionUID = 1L;
	Map<String, String> memberMap;
	
	protected ValidatorImpl() throws RemoteException {
		super();
		memberMap = new HashMap<>();
		memberMap.put("John","Appleseed");
	}

	@Override
	public String validate(String login, String password) throws RemoteException {
		if(getMemberMap().containsKey(login) && getMemberMap().get(login).equals(password)) {
			String toHash = login+password+ String.valueOf(Calendar.getInstance().getTimeInMillis());
			byte[] bye;
			try {
				bye = toHash.getBytes("UTF-8");
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(bye);
				String hash = new String(Base64.getEncoder().encode(md.digest()));
				synchronized(Token.tokens) {
					Token.tokens.put(hash, Calendar.getInstance().getTimeInMillis());
				}
				return hash;
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;		
	}
	public Map<String, String> getMemberMap() {
		return memberMap;
	}
}
