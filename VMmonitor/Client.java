package VMmonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;

public class Client
{

	public Client() throws ClientConfigurationException
	{
		setOneAuth(null);
		setOneEndPoint(null);
	}
	
	public Client(String secret, String endpoint)throws ClientConfigurationException
	{
		setOneAuth(secret);
		setOneEndPoint(endpoint);
	}
	
	public OneResponse call(String action, Object...args)
	{
		boolean success = false;
		String msg = null;

		try
		{
			Object[] params = new Object[args.length + 1];
			params[0] = oneAuth;
			for(int i=0; i<args.length; i++)
				params[i+1] = args[i];
			Object[] result = (Object[]) client.execute("one."+action, params);
			success = (Boolean) result[0];
			// 	In some cases, the xml-rpc response only has a boolean
			// 	OUT parameter
			
			if(result.length > 1)
			{
				try
				{
					msg = (String) result[1];
				}
				catch (ClassCastException e)
				{
					// The result may be an Integer
					msg = ((Integer) result[1]).toString();
			    }
			}
		
		}
		catch (XmlRpcException e)
		{
			msg = e.getMessage();
		}
		
	 return new OneResponse(success, msg);

    }//end CALL
	/**
	* Calls OpenNebula and retrieves oned version
	*
	* @return The server's xml-rpc response encapsulated
	*/
	
	public OneResponse get_version()
	{
		return call("system.version");
	}
	//--------------------------------------------------------------------------
	// PRIVATE ATTRIBUTES AND METHODS
	//--------------------------------------------------------------------------
	
	private String oneAuth;
	private String oneEndPoint;
	private XmlRpcClient client;
	
	private void setOneAuth(String secret) throws ClientConfigurationException
	{
		//oneAuth = "oneadmin:Roityigrorc4";
		oneAuth = secret;
		
		try
		{
			if(oneAuth == null)
			{
				String oneAuthEnv = System.getenv("ONE_AUTH");
				File authFile;
				if ( oneAuthEnv != null && oneAuthEnv.length() != 0)
				{
					authFile = new File(oneAuthEnv);
				}	
				else
				{
					authFile = new File(System.getenv("HOME")+"/.one/one_auth");
				}
							
				oneAuth =(new BufferedReader(new FileReader(authFile))).readLine();
			
			}//endif oneauth=null
			
			oneAuth = oneAuth.trim();
	    }
		catch (FileNotFoundException e)
		{
			// 	This comes first, since it is a special case of IOException
			throw new ClientConfigurationException("ONE_AUTH file not present");
		}
		catch (IOException e)
		{
			// You could have the file but for some reason the program can not
			// 	read it
			throw new ClientConfigurationException("ONE_AUTH file unreadable");
		}
	}//end set oneAuth
	
	private void setOneEndPoint(String endpoint) throws ClientConfigurationException
	{
		oneEndPoint = "http://localhost:2633/RPC2";
		if(endpoint != null)
		{
			oneEndPoint = endpoint;
		}
		else
		{
			String oneXmlRpcEnv = System.getenv("ONE_XMLRPC");
			if ( oneXmlRpcEnv != null && oneXmlRpcEnv.length() != 0 )
			{
				oneEndPoint = oneXmlRpcEnv;
			}
		}
	
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			
		try
		{
			config.setServerURL(new URL(oneEndPoint));
		}
	
		catch (MalformedURLException e)
		{
			throw new ClientConfigurationException("The URL "+oneEndPoint+" is malformed.");
		}
		
		client = new XmlRpcClient();
		client.setConfig(config);
		
	}//end set endpoint

}
