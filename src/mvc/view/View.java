package mvc.view;
import java.util.TreeMap;

import java.util.Map.Entry;
import java.io.Console;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import mvc.model.*;
import mvc.model.Worker.*;
import mvc.rmi.Validator;
import mvc.controller.*;
import mvc.external.*;

public class View {
	public static void printMenu()
	{
		System.out.println("MENU");
		System.out.println("\t1.Lista pracownikow");
		System.out.println("\t2.Dodaj pracownika");
		System.out.println("\t3.Usun pracownika");
		System.out.println("\t4.Kopia zapasowa");
		System.out.println("\t5.Pobierz dane z sieci");
		System.out.print("\nWybor>");
	}
	
	public static void downloadData()
	{
		System.out.println("5.Lista pracownikow:");
		System.out.print("Login\t:\t");
		String Login=Controller.getChoice();
		System.out.print("Password\t:\t");
		Console con = System.console();
		String Password;
		if(con!=null) {
			char[] ps = con.readPassword();
			Password = new String(ps);
		}
		else {
			Password = Controller.getChoice();
		}
		
		if(Login!="") {
			Object remote;
			try {
				Registry registry = LocateRegistry.getRegistry(1888);
				remote = registry.lookup("validator");
				Validator reply = (Validator)remote;
				Model.localToken = reply.validate(Login, Password);
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Model.localToken!=null) {
				System.out.print("Sukces\n");
			}
			else {
				System.out.print("Porazka\n");
				return;
			}
		}
		
		System.out.print("Protokol [T]CP/IP czy [S]OAP\t:\t");
		String[] options={"T","S"};
		boolean tcpip = Controller.getChoice(options).compareToIgnoreCase("T") == 0;
			
		
		System.out.print("Adres\t:\t");
		String Adres=Controller.getChoice();
		System.out.print("Port\t:\t");
		String Port=Controller.getChoice();
		printStrip();
		System.out.print("Ustanawianie polaczenia...\t:\t");
		DataController concontrol;
		try {
			if(Model.localToken == null)
				throw new Exception();
			if(tcpip)
				concontrol = new TCPIPController(Adres, Port,Model.localToken);
			else
				concontrol = new SOAPController(Adres, Port,Model.localToken);
			
			System.out.print("Sukces\n");
		}
		catch(Exception ex) {
			System.out.print("Porazka\n");
			return;
		}

		System.out.print("Pobieranie danych...\t:\t");
		TreeMap<String, Worker> temp = concontrol.getWorkers();
		if(temp!=null) {
			System.out.print("Sukces\n");
		}
		else {
			Model.localToken = null;
			System.out.print("Porazka\n");
			return;
		}
		System.out.print("Czy zapisac pobrane dane? [T]/[N]\t:\t");
		String[] options2={"T","N"};
		if(Controller.getChoice(options2).compareToIgnoreCase("N") == 0)
			return;

		System.out.print("Zapisywanie...\t:\t");
		Model.Control.setWorkers(temp);
		System.out.print("Sukces\n");
		Model.localToken = null;
	}
	
	public static void printWorkers()
	{
		System.out.println("1.Lista pracownikow:");
		int licznik=1;
		for(  Entry<String, Worker> node : Model.Control.getWorkers().entrySet()) 
		{
			Worker person= node.getValue();
			System.out.print(person);
			System.out.println("\n[Pozycja:"+ licznik++ +"/"+Model.Control.getWorkers().size()+"]");
			System.out.println("[Enter]-nastepny");
			System.out.println("[q]-powrot");
			String[] options={"q",""};
			if (Controller.getChoice(options).compareToIgnoreCase("q")==0)
				break;
		}
	}
	public static void printStrip()
	{
		System.out.println("-----------------------------");
	}
	public static void deleteWorker()
	{
		System.out.println("3.Usun pracownika:\n");
		System.out.print("Podaj pesel\t: ");
		try
		{
			String pesel=Controller.getChoice();
			printStrip();
			if (!Model.Control.getWorkers().containsKey(pesel))
				throw new NumberFormatException();
			System.out.println(Model.Control.getWorkers().get(pesel));
			printStrip();
			System.out.println("[Enter]-potwierdz");
			System.out.println("[q]-powrot");
			String[] options={"","q"};
				if (Controller.getChoice(options).compareToIgnoreCase("")==0)
					Model.Control.removeWorker(pesel);
		}
		catch(Exception e)
		{
			printStrip();
			System.out.println("Nie ma takiego pracownika");
		}
	}
	public static void addWorker()
	{
		try
		{
			System.out.println("2.Dodaj pracownika:\n");
			System.out.print("[D]yrektor/[H]andlowiec\t:\t");
			String[] options={"D","H"};
			String type=Controller.getChoice(options);
			printStrip();
			System.out.print("Identyfikator pesel\t\t:\t");
			String pesel = Controller.getChoice();

			if (!PeselValidation.byString(pesel) || Model.Control.getWorkers().containsKey(pesel))
				throw new NumberFormatException();
			System.out.print("Imie\t\t\t\t:\t"); 
			String name=Controller.getChoice();
			System.out.print("Nazwisko\t\t\t:\t");
			String lastName=Controller.getChoice();
			System.out.print("Wynagrodzenie (zl)\t\t:\t");
			BigDecimal income=new BigDecimal(Controller.getChoice());
			System.out.print("Telefon\t\t\t\t:\t");
			String sphone=Controller.getChoice();
			int phone;
			if (sphone.compareToIgnoreCase("")==0)
				phone=-1;
			else
				phone=Integer.parseInt(sphone); 
			if (type.compareToIgnoreCase("D")==0)
			{
				System.out.print("Dodatek sluzbowy (zl)\t\t:\t");
				BigDecimal addictonal=new BigDecimal(Controller.getChoice());
				System.out.print("Karta sluzbowa\t\t\t:\t");
				String card=Controller.getChoice();
				System.out.print("Limit kosztow (zl)\t\t:\t");
				BigDecimal limit= new BigDecimal(Controller.getChoice());
				printStrip();
				System.out.println("[Enter]-zapisz");
				System.out.println("[q]-odrzuc");
				String[] options2={"","q"};
				if (Controller.getChoice(options2).compareToIgnoreCase("")==0)
				{
					Worker d = new Dyrektor(pesel,name, lastName, income, limit, phone, card, addictonal);
					Model.Control.addWorker(d);
				}
			}
			else
			{
				System.out.print("Prowizja (%)\t\t\t:\t");
				int provision=Integer.parseInt(Controller.getChoice());
				System.out.print("Limit prowizji/miesiac (zl)\t:\t");
				BigDecimal limit= new BigDecimal(Controller.getChoice());
				printStrip();
				System.out.println("[Enter]-zapisz");
				System.out.println("[q]-odrzuc");
				String[] options2={"","q"};
				if (Controller.getChoice(options2).compareToIgnoreCase("")==0)
				{
					Worker d = new Handlowiec(pesel,name, lastName, income, limit, phone, provision);
					Model.Control.addWorker(d);
				}
			}
		}
		catch(NumberFormatException e)
		{
			printStrip();
			System.out.println("Bledne dane");
		}
	}
	public static void Serialize()
	{
		System.out.println("4.Kopia zapasowa:\n");
		System.out.print("[Z]achowaj/[O]dtworz\t:\t");
		String[] options={"O","Z"};
		String type=Controller.getChoice(options);
		printStrip();
		if (type.compareToIgnoreCase("Z")==0)
		{
			System.out.print("Kompresja [G]zip/[Z]zip/[X]ML\t:\t");
			String[] options2={"G","Z","X"};
			String compresion=Controller.getChoice(options2);
			System.out.print("Nazwa pliku\t:\t");
			String filePath = Controller.getChoice();
			printStrip();
			System.out.println("[Enter]-zapisz");
			System.out.println("[q]-odrzuc");
			String[] options3={"","q"};
			if (Controller.getChoice(options3).compareToIgnoreCase("")==0)
			{
				if (!Model.Compress(filePath, compresion.charAt(0)))
					System.out.println("Blad pliku");
			}
		}
		else
		{
			System.out.print("Nazwa pliku\t:\t");
			String filePath = Controller.getChoice();
			printStrip();
			System.out.println("[Enter]-odzyskaj");
			System.out.println("[q]-odrzuc");
			String[] options3={"","q"};
			if (Controller.getChoice(options3).compareToIgnoreCase("")==0)
			{
				if (!Model.Decompress(filePath))
					System.out.println("Blad pliku");
			}
		}
	}
}
