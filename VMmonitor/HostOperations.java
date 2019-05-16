package VMmonitor;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.opennebula.client.Client;
import org.opennebula.client.OneException;
import org.opennebula.client.host.Host;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.opennebula.client.Client;

public class HostOperations 
{	
	public String getDetails(Client oneClient)
	{
		String s="";
		try 
		{
			//Client oneClient=new Client(secret, "http://localhost:2633/RPC2");
			Host host=new Host(5,oneClient);
			File file=new File("Host5.xml");
			FileOutputStream fos=new FileOutputStream(file);
			String s1=Host.info(oneClient, 5).getMessage();
			fos.write(s1.getBytes());
			fos.close();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nd=doc.getElementsByTagName("ID");
			 
			s=s+"ID : "+nd.item(0).getTextContent()+"\n";
			nd=doc.getElementsByTagName("HOSTNAME");
			s=s+"HOSTNAME : "+nd.item(0).getTextContent()+"\n";
			nd=doc.getElementsByTagName("ARCH");
			s=s+"ARCH : "+nd.item(0).getTextContent()+"\n";
			nd=doc.getElementsByTagName("HYPERVISOR");
			s=s+"HYPERVISOR : "+nd.item(0).getTextContent()+"\n";
			nd=doc.getElementsByTagName("MODELNAME");
			s=s+"MODELNAME : "+nd.item(0).getTextContent()+"\n";
			nd=doc.getElementsByTagName("VERSION");
			s=s+"VERSION : "+nd.item(0).getTextContent()+"\n";
		}
		catch (Exception e1) 
		{
			e1.printStackTrace();	
		}
		return s;
	}
	
	public int getUsedMem()
	{
		try
		{
			File file=new File("Host5.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nd1=doc.getElementsByTagName("MAX_MEM");
			NodeList nd2=doc.getElementsByTagName("USED_MEM");
			int maxmem = Integer.parseInt(nd1.item(0).getTextContent());
			int usedmem = Integer.parseInt(nd2.item(0).getTextContent());
			return Math.round(((100*usedmem)/maxmem));
		}
		catch(Exception e)
		{
			
		}
		return 0;
	}
	
	public int getUsedCPU()
	{
		try
		{
			File file=new File("Host5.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nd1=doc.getElementsByTagName("MAX_CPU");
			NodeList nd2=doc.getElementsByTagName("CPU_USAGE");
			int maxcpu = Integer.parseInt(nd1.item(0).getTextContent());
			int cpuusage = Integer.parseInt(nd2.item(0).getTextContent());
			return Math.round(((100*cpuusage)/maxcpu));
		}
		catch(Exception e)
		{
			
		}
		return 0;
	}
}
