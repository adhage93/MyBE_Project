package AgentCodes;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class SocketClient 
{
	int port = 49465;
	String server = MainClass.mainIP;
    
//FILE SENDER START-----------------------------------------------------------------------
    public void FileSender()
    {
    	try 
    	{
			Selector selector = Selector.open();
			SocketChannel connClient = SocketChannel.open();
	    	connClient.configureBlocking(false);
	    	connClient.connect(new InetSocketAddress(server, port));
	    	connClient.register(selector, SelectionKey.OP_CONNECT);
	    	
	    	while(true)
			{
				selector.select();
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while(iterator.hasNext())
				{
					SelectionKey key = iterator.next();
					iterator.remove();
					SocketChannel client = (SocketChannel) key.channel();
					
					if(key.isConnectable())
					{
						if(client.isConnectionPending())
						{
							System.out.println("Trying to finish connection");
							client.finishConnect();
						}
						client.register(selector, SelectionKey.OP_WRITE);
						continue;
					}
					
					if(key.isWritable())
					{
						long start = System.currentTimeMillis();
						sendFile(client);
						long finish = System.currentTimeMillis();
						System.out.println("\nTotal time to send the file: "+(finish-start)+" mSec.");
						client.close();
						return;
					}
				}
			}
	    	
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	
    }
    
    private void sendFile(SocketChannel client) throws Exception 
    { 	
	    String data = "BKP.tar.gz";
		int BufferSize = 1048576;//1MB
		Path path = Paths.get(data);
		FileChannel fileChannel = FileChannel.open(path);
		ByteBuffer buf = ByteBuffer.allocate(BufferSize);
		int noOfBytesRead = 0;
		int counter = 0;
		do
		{
			noOfBytesRead = fileChannel.read(buf);
			if(noOfBytesRead <=0 )
			{
				break;
			}
			counter += noOfBytesRead;
			buf.flip();
			do
			{
				noOfBytesRead -= client.write(buf);
			
			}while(noOfBytesRead > 0);
			buf.clear();
		
		}while(true);
		
		fileChannel.close();
		System.out.println("Total bytes send: "+counter);
	}
//END----------------------------------------------------------------------
}
