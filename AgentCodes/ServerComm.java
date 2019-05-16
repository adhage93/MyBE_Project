package AgentCodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerComm extends Thread
{
	int port = 49466;
	String server = MainClass.mainIP;
	
	public void run()
	{
		try 
		{
			ServerComm c1 = new ServerComm();
			Socket s1 = c1.socketClient();
			//c1.chattingClient(s1);
			InputStream in = s1.getInputStream();
			int val = in.read();
			if(val == 50)
			{
				OutputStream out = s1.getOutputStream();
				out.write(100);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Socket socketClient() 
    {
		Socket socket = null;
		int ERROR = 1;
	
		// connect to server
		try 
		{
			socket = new Socket(server, port);
			System.out.println("Connected with server "+socket.getInetAddress()+":"+socket.getPort());
		}
		catch (UnknownHostException e) 
		{
			System.out.println(e);
			System.exit(ERROR);
		}
		catch (IOException e) 
		{
			System.out.println(e);
			System.exit(ERROR);
		}
		
		return socket;
    }
	
	public void chattingClient(Socket socket)
    {
		try 
		{
			BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
            
			// sending to client (pwrite object)
			OutputStream ostream = socket.getOutputStream(); 
			PrintWriter pwrite = new PrintWriter(ostream, true);

            // receiving from server ( receiveRead  object)
			InputStream istream = socket.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

			System.out.println("Start the chitchat, type and press Enter key");

			String receiveMessage, sendMessage;               
			while(true)
			{
				sendMessage = "Client: "+keyRead.readLine();  // keyboard reading
				pwrite.println(sendMessage);       // sending to server
				pwrite.flush();                    // flush the data
				if((receiveMessage = receiveRead.readLine()) != null) //receive from server
				{
					System.out.println(receiveMessage); // displaying at DOS prompt
				}         
			}               
		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
    }    
}
