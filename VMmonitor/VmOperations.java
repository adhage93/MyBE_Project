package VMmonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.user.User;
import org.opennebula.client.user.UserPool;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class VmOperations 
{
	int MAX = 30;
    
	//New login method here---------------------------------
	public Client login(String secret)
	{
		try 
		{
			Client oneClient = new Client("oneadmin:aditya", "http://localhost:2633/RPC2");
			
			return oneClient;
		} 
		catch (ClientConfigurationException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] getLiveVms(Client oneClient)
    {
    	String []s = new String[MAX];
    	int idx=0;
    	String name;
    	try
    	{
	    	//oneClient=new Client(secret, "http://localhost:2633/RPC2");
	    	VirtualMachinePool VMP= new VirtualMachinePool(oneClient);
    		VMP.info().getMessage();
    		Iterator<VirtualMachine> ALLVMS=VMP.iterator();
    		
    		while(ALLVMS.hasNext())
    		{
    			VirtualMachine v = ALLVMS.next();
    			if(v.stateStr()=="ACTIVE")
    			{
    				int i = Integer.parseInt(v.getId());
    				s[idx] = v.getName() + "-" + v.getId();
    				
    				File file1=new File(""+i+".xml");
	 				FileOutputStream fs = new FileOutputStream(file1);
	 				fs.write(VirtualMachine.info(oneClient, i).getMessage().getBytes());
    				fs.close();
    				
    				idx++;
    			}
    			
    		}
    	}
    	catch (Exception e) 
    	{
			// TODO: handle exception
		}
    	
    	return s;
    }
    
	public String[] getDeadVms(Client oneClient)
    {
    	String []s = new String[MAX];
    	int idx=0;
    	String name;
    	try
    	{
	    	//oneClient=new Client(secret, "http://localhost:2633/RPC2");
	    	VirtualMachinePool VMP= new VirtualMachinePool(oneClient);
    		VMP.info().getMessage();
    		Iterator<VirtualMachine> ALLVMS=VMP.iterator();
    		
    		while(ALLVMS.hasNext())
    		{
    			VirtualMachine v = ALLVMS.next();
    			if(v.stateStr()!="ACTIVE")
    			{
    				int i = Integer.parseInt(v.getId());
    				s[idx] = v.getName() + "-" + v.getId();
    				
    				File file1=new File(""+i+".xml");
	 				FileOutputStream fs = new FileOutputStream(file1);
	 				fs.write(VirtualMachine.info(oneClient, i).getMessage().getBytes());
    				fs.close();
    				
    				idx++;
    			}
    			
    		}
    	}
    	catch (Exception e) 
    	{
			// TODO: handle exception
		}
    	
    	return s;
    }
   
    public static String getMac(File f1)
	{
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f1);
			NodeList nd=doc.getElementsByTagName("MAC");
			Node d1=nd.item(0);
			return d1.getTextContent();
		}
		catch(Exception e)
		{

		}
		return "";
	}
	
    public static String getOwner(File f1)
	{
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f1);
			NodeList nd=doc.getElementsByTagName("UNAME");
			Node d1=nd.item(0);
			return d1.getTextContent();
		}
		catch(Exception e)
		{

		}
		return "";
	}
    
    public static String getIPAddress(File file)
	{
		
		try
		{
			Process p=Runtime.getRuntime().exec("arp -a");
			//p.waitFor();
			p.waitFor();
			BufferedReader buffer=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s=buffer.readLine();
			String mac=VmOperations.getMac(file);
			while(s!=null)
			{
				if(s.contains(mac))
				{
					String ip=s.substring(s.indexOf('(')+1, s.indexOf(')'));
					return ip;
				}
				s=buffer.readLine();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return "";
	}
	
    public static String getNetworkInfo(int id)
	{
		String s="Network Info :-\n------------------------------\n";
		try
		{
			File file=new File(id+".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nd1=doc.getElementsByTagName("NIC");
			Node temp=nd1.item(0);
			NodeList nd=temp.getChildNodes();
			
			
			s=s+"Bridge  : "+nd.item(1).getTextContent()+"\n";
			s=s+"Static IP : "+nd.item(3).getTextContent()+"\n";
			s=s+"Dynamic IP : "+getIPAddress(file)+"\n";
			s=s+"MAC ID : "+nd.item(4).getTextContent()+"\n";
			s=s+"Network : "+nd.item(5).getTextContent()+"\n";
			s=s+"Network ID : "+nd.item(6).getTextContent()+"\n";
			s=s+"NIC ID : "+nd.item(7).getTextContent();
		}
		catch(Exception e) {e.printStackTrace();}
		return s;
	}
	public static String getGeneralInfo(int id)
	{
		String s="General Info :-\n------------------------------\n";
		try
		{
			File file=new File(id+".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nd1=doc.getElementsByTagName("UNAME");
			s=s+"USER : "+nd1.item(0).getTextContent()+"\n";
			nd1=doc.getElementsByTagName("GNAME");
			s=s+"GROUP : "+nd1.item(0).getTextContent()+"\n";
			nd1=doc.getElementsByTagName("NAME");
			s=s+"VM NAME : "+nd1.item(0).getTextContent()+"\n";
		
			String temp="PERMISSION:   \nOWNER :";
			nd1=doc.getElementsByTagName("OWNER_U");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" USE ";
			nd1=doc.getElementsByTagName("OWNER_M");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" MANAGE ";
			nd1=doc.getElementsByTagName("OWNER_A");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" ADMIN ";
			temp=temp+"\nGROUP: ";
			nd1=doc.getElementsByTagName("GROUP_A");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" ADMIN";
			
			nd1=doc.getElementsByTagName("GROUP_U");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" USE ";
			nd1=doc.getElementsByTagName("GROUP_M");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" MANAGE";
			temp=temp+"\nOTHER: ";
			nd1=doc.getElementsByTagName("OTHER_A");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" ADMIN";
			
			nd1=doc.getElementsByTagName("OTHER_U");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" USE ";
			nd1=doc.getElementsByTagName("OTHER_M");
			if(nd1.item(0).getTextContent().equals("1"))
				temp=temp+" MANAGE";
			s=s+temp;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
	
	public static String getMemoryInfo(int id)
	{
		String s="Memory Info :-\n------------------------------\n";
		try
		{
			File file=new File(id+".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nd=doc.getElementsByTagName("CPU");
			s=s+"Allocated CPU: "+nd.item(1).getTextContent()+"\n";
			NodeList nd1=doc.getElementsByTagName("MEMORY");
			double t=Double.parseDouble(nd1.item(0).getTextContent());
			t=t/(1024*1024);
			s=s+"Allocated Memory: "+t+" GB";
			nd1=doc.getElementsByTagName("ORIGINAL_SIZE");
			t=Double.parseDouble(nd1.item(0).getTextContent());
			t=t/1024;
			s=s+"\nAllocated DISK: "+t+" GB";
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		return s;
	}
	public String getOwner(int id)
	{
		String us="";
		try {
			File file=new File(id+".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			NodeList nd1=doc.getElementsByTagName("UNAME");
			us=nd1.item(0).getTextContent();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return us;
		
	}
	
	public void createMongo(Client oneClient)
	{
		try
		{
			
			MongoClient mc = new MongoClient("localhost",27017);
			DB VMinfo = mc.getDB("VMinfo");
			//System.out.println("Connection :)");
			
			DBCollection basic = VMinfo.getCollection("basic");
			//System.out.println("Collection...");
			
			String []s = this.getLiveVms(oneClient);
			String [] d=this.getDeadVms(oneClient);
			for(int i=0; i<s.length; i++)
			{
				String val = s[i];
				//System.out.println(val);
				if(val != null)
				{
					BasicDBObject dob=new BasicDBObject();
					dob.append("VmName", s[i]);
					DBCursor dc=basic.find(dob);
					System.out.println(dc.count());
					if(dc.count()==0) 
					{
						System.out.println(dc.count());
						int id = Integer.parseInt(val.substring(val.indexOf("-")+1,val.length()));
						File f1 = new File(id+".xml");
						BasicDBObject doc = new BasicDBObject("VmName",s[i]).append("MAC",VmOperations.getMac(f1)).append("IP", VmOperations.getIPAddress(f1)).append("Owner",VmOperations.getOwner(f1));
						basic.insert(doc);
					}
					else
					{
						int id = Integer.parseInt(val.substring(val.indexOf("-")+1,val.length()));
						File f1 = new File(id+".xml");
						BasicDBObject doc = new BasicDBObject("VmName",s[i]).append("MAC",VmOperations.getMac(f1)).append("IP", VmOperations.getIPAddress(f1)).append("Owner",VmOperations.getOwner(f1));
						basic.update(dob,doc);
					}
				}
				else
				{
					break;
				}
			}
			for(int i=0; i<d.length; i++)
			{
				String val = d[i];
				//System.out.println(val);
				if(val != null)
				{
					BasicDBObject dob=new BasicDBObject();
					dob.append("VmName", d[i]);
					DBCursor dc=basic.find(dob);
					System.out.println(dc.count());
					if(dc.count()==0) 
					{
						int id = Integer.parseInt(val.substring(val.indexOf("-")+1,val.length()));
						File f1 = new File(id+".xml");
						BasicDBObject doc = new BasicDBObject("VmName",d[i]).append("MAC",VmOperations.getMac(f1)).append("IP","NO IP").append("Owner",VmOperations.getOwner(f1));
						basic.insert(doc);
					}
					else
					{
						int id = Integer.parseInt(val.substring(val.indexOf("-")+1,val.length()));
						File f1 = new File(id+".xml");
						BasicDBObject doc = new BasicDBObject("VmName",d[i]).append("MAC",VmOperations.getMac(f1)).append("IP", "NO IP").append("Owner",VmOperations.getOwner(f1));
						basic.update(dob,doc);
					}
				}
				else
				{
					break;
				}
			}
			mc.close();
		}
		catch(MongoException e)
		{
			
		}
	}
	
}