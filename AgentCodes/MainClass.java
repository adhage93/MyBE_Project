package AgentCodes;

import java.io.*;
import java.time.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainClass 
{	
	public static String mainIP = "192.168.0.16";
	
	void createLogs()
	{
		try
		{
			System.out.println("\n(*) Creating log files...script.sh");
			Process p = Runtime.getRuntime().exec("sh script.sh");	
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str=br.readLine();
			while(str!=null)
			{
				System.out.println(str);
				str=br.readLine();
			}
			System.out.println("\nDone");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void runLoggingScript()
	{
		try
		{
			System.out.println("\n(*) Updating mongodb with logs...logScript.py");
			System.out.println("_______________________________________________________________________\n");
			Process p = Runtime.getRuntime().exec("python logScript.py");	
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str=br.readLine();
			while(str!=null)
			{
				System.out.println(str);
				str=br.readLine();
			}
			System.out.println("_______________________________________________________________________");
			System.out.println("\nDone");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void runModifiedFileScript()
	{
		System.out.println("\n(*) Running modified files program...");
		ModifiedFiles obj=new ModifiedFiles();
		obj.getUpdatedFile();
		obj.copyFiles();
		obj.compressBKP();
		obj.getJson();
		System.out.println("\nDone");
	}
	
	void sendCollectedFiles()
	{
		SocketClient cli = new SocketClient();
		cli.FileSender();
	}
	
	void startChatting()
	{
		ServerComm sc1 = new ServerComm();
		sc1.start();
	}
	
	double getCpuUtilization()
	{
		double d=0.0;
		try
		{
			Process p=Runtime.getRuntime().exec("grep 'cpu ' /proc/stat | awk '{cpu_usage=($2+$4)*100/($2+$4+$5)} END {print cpu_usage \"%\"}'");	
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str=br.readLine();
			String user=str;
			d=Double.parseDouble(user);
		}
		catch(Exception e)
		{
			
		}
		return d;
	}
	void removelogs()
	{
			try
			{
				Process p = Runtime.getRuntime().exec("sh remove.sh");	
				BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
				String str=br.readLine();
				while(str!=null)
				{
					System.out.println(str);
					str=br.readLine();
				}
			}
			catch(Exception e){}
	}
	
	static int cnt = 0;
	public static void main(String [] args)
	{	
		MainClass m1 = new MainClass();
		System.out.println("(@) Instance:- "+cnt+"   Exact Time:- "+LocalDateTime.now());
		System.out.println("-----------------------------------------------------------------------");
		m1.createLogs();
		m1.runLoggingScript();
		m1.runModifiedFileScript();
				
		System.out.println("\nSending collected files....");
		m1.sendCollectedFiles();
		
		System.out.println("\nRemoving trash....");
		//m1.removelogs();
		System.out.println("-----------------------------------------------------------------------\n");
		cnt++;
	}
}
