package VMmonitor;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Iterator;

import javax.swing.plaf.synth.SynthScrollBarUI;

import org.omg.Messaging.SyncScopeHelper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import java.io.*;

public class SocketServer extends Thread
{   
	int port = 49465;
	String server_ip = DashBoard.mainIP;
	ServerSocket server_socket;

//FILE SENDER START-----------------------------------------------------------------------	
	public void run() 
    {
		try 
		{
			System.out.println("Server started...");
			System.out.println("-----------------------------------------------------------------------");
			Selector selector = Selector.open();
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.socket().bind(new InetSocketAddress(server_ip, port));
			server.register(selector, SelectionKey.OP_ACCEPT);
			while(true)
			{
				selector.select();
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while(iterator.hasNext())
				{
					SelectionKey key = iterator.next();
					iterator.remove();
					
					if(key.isAcceptable())
					{
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						client.register(selector, SelectionKey.OP_READ);
						continue;
					}
					
					if(key.isReadable())
					{
						long start = System.currentTimeMillis();
						SocketChannel channel = (SocketChannel) key.channel();
						
						receiveFile(channel);
						long finish = System.currentTimeMillis();
						System.out.println("\nTotal time to receive the file: "+(finish-start)+" mSec.");
						System.out.println("-----------------------------------------------------------------------");
						channel.close();
						//return;
					}
				}
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    }
	
	private void receiveFile(SocketChannel channel) throws IOException 
	{
		System.out.println("Reciving files...");
		System.out.println("-----------------------------------------------------------------------");
		String mac=returnMac(channel);
		mac = mac.replace(":", "");
		String ts = LocalDateTime.now().toString();
		ts = ts.replace(":", "-");
		ts = ts.substring(0,ts.length()-7);
		String folder = mac+"_"+ts;
	    String data = "/home/adisd/tushar/"+folder+".tar.gz";
	    		
		int bufferSize = 1048576;//1MB
		Path path = Paths.get(data);
		FileChannel fileChannel = FileChannel.open(path, 
				EnumSet.of(StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE)
				);
		
		ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
		int res = 0;
		int counter = 0;
		do
		{
			buffer.clear();
			res = channel.read(buffer);
			//System.out.println(res);
			buffer.flip();
			if(res > 0)
			{
				fileChannel.write(buffer);
				counter += res;
			}
		
		}while(res >= 0);
		channel.close();
		fileChannel.close();
		System.out.println("Total Bytes Received: "+counter);
		
		try
		{
			Process p = Runtime.getRuntime().exec("mkdir /home/adisd/tushar/"+folder);
			p.waitFor();
			String c = "tar -xzf "+data+" -C /home/adisd/tushar/"+folder;
			p = Runtime.getRuntime().exec(c);
			System.out.println(c);
			p.waitFor();
			
			p = Runtime.getRuntime().exec("rm "+data);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		
	}
	
	public String returnMac(SocketChannel s1)
	{
		MongoClient mc = new MongoClient("localhost",27017);
		DB VMinfo = mc.getDB("VMinfo");
		System.out.println("Connection :)");
		
		DBCollection basic = VMinfo.getCollection("basic");
		System.out.println("Collection...");
		
		String ipaddr = s1.socket().getInetAddress().toString();
		DBCursor dc=basic.find(new BasicDBObject("IP", ipaddr.substring(1,ipaddr.length())));
		String mac=dc.next().get("MAC").toString();
		System.out.println(mac);
		return mac;
	}
//END-----------------------------------------------------------------------
	
}
