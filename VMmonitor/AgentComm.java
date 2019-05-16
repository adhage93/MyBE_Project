package VMmonitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AgentComm extends Thread
{
	int port = 49466;
	String server_ip = DashBoard.mainIP;
	ServerSocket server_socket;
	
	static boolean RESULT = false;
	
	public Socket chattingServer()
	{		
		try 
		{
			
			server_socket = new ServerSocket(port);
			System.out.println("Server waiting for client on port " + 
					   server_socket.getLocalPort());
	
			// server infinite loop
			while(true) 
			{
				Socket socket = server_socket.accept();
				System.out.println("\n@@@@@@New connection accepted "+socket.getInetAddress()+" : " + socket.getPort());
				
				/*
				BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                // sending to client (pwrite object)
				OutputStream ostream = socket.getOutputStream(); 
				PrintWriter pwrite = new PrintWriter(ostream, true);
				
				// receiving from server ( receiveRead  object)
				InputStream istream = socket.getInputStream();
				BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
				
				String receiveMessage, sendMessage;               
				while(true)
				{
					if((receiveMessage = receiveRead.readLine()) != null)  
					{
						System.out.println(receiveMessage);         
					}         
					sendMessage = "Server: "+keyRead.readLine(); 
					pwrite.println(sendMessage);             
					pwrite.flush();
				} 
				*/
				return socket;
			}
		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
		return null;
    }
	
	public void run() //isAgentAlive() 
	{
		RESULT = false;
		try
		{
			AgentComm c1 = new AgentComm();
			Socket s1 = c1.chattingServer();
			OutputStream out = s1.getOutputStream();
			out.write(50);
			InputStream in = s1.getInputStream();
			int val = in.read();
			if(val == 100)
			{
				RESULT = true;
				System.out.println("true------");
			}
			else
			{
				RESULT = false;
				System.out.println("false-----");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

