package mvc.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import mvc.controller.*;

import mvc.model.Worker.*;



public final class Model {
	public static DataController Control = new DBController();
	public static String localToken = null;
	

	
	public static void marshal(String path) {
		try {
			TreeMap<String, Worker> t = Model.Control.getWorkers();
			JAXBContext jc = JAXBContext.newInstance(XMLList.class);
			Marshaller m = jc.createMarshaller();
			OutputStream out = new FileOutputStream(path);
			XMLList d = new XMLList();
			d.setHandlowiecList(new ArrayList<Worker>(t.values()));
			m.marshal(d, out);
			out.flush();
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static TreeMap<String, Worker> unmarshal(String path) {
		TreeMap<String, Worker> t = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(XMLList.class);
			Unmarshaller m = jc.createUnmarshaller();
			InputStream in = new FileInputStream(path);
			
			XMLList d = (XMLList)m.unmarshal(in);
			t= new TreeMap<String,Worker>();
			for(Worker n :  d.getDyrektorList()) {
				t.put(n.getPesel(), n);
			}

		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	
	
	public static Boolean Compress(String filePath, char compresion)
	{
		Boolean output=false;
		ObjectOutputStream oos =null;
		ZipOutputStream zos=null;
		try  
		{
            if (compresion=='G')
            {
            	oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File(filePath))));
            }
            else if (compresion=='Z')
            {
                 zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
                 zos.putNextEntry(new ZipEntry("Object"));
                 oos= new ObjectOutputStream(zos);
            }
            else{
            	marshal(filePath);
            	return true;
            }
            oos.writeObject(Model.Control.getWorkers());
			oos.flush();
			oos.close();
			output=true;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return output;
	}
	
	public static Boolean Decompress(String filePath)
	{
		Boolean output=false;
		ObjectInputStream ois=null;
		ZipInputStream zis=null;
		try{
			String[] temp=filePath.split("\\.");
			char compresion=temp[temp.length-1].charAt(0);
			if (compresion=='g'||compresion=='G')
			{
				 ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File(filePath))));
			}
			else if (compresion=='z'||compresion=='Z')
			{
		            zis = new ZipInputStream(new FileInputStream(filePath));
		            zis.getNextEntry();
		            ois= new ObjectInputStream(zis);
			}
			else if (compresion=='x'||compresion=='X')
			{
				Model.Control.setWorkers(unmarshal(filePath));
				return true;
			}
			else 
				throw new ArrayIndexOutOfBoundsException();
			Model.Control.setWorkers((TreeMap<String, Worker>) ois.readObject());
			output=true;
		}
		catch (ArrayIndexOutOfBoundsException | IOException | ClassNotFoundException e) {System.out.println("Brak rozszerzenia");}
		return output;			
	}
}
