package EvidenceMngr;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MngrOperations 
{
	public MongoClient mc = null;
	
	public MngrOperations() 
	{
		mc = new MongoClient("localhost",27017);
		if(mc != null)
		{
			System.out.println("Mongo Connection Established...");
		}
	}
	
	Iterator<String> getDatabases()
	{
		try
		{
			List<String> l1 = mc.getDatabaseNames();
			Iterator<String> i1 = l1.iterator();
			
			return i1;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	Iterator<String> getCollections(String dbname)
	{
		try
		{
			DB d1 = mc.getDB(dbname);
			Set<String> s1 = d1.getCollectionNames();
			Iterator<String> i1 = s1.iterator();
			return i1;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void main(String[] args) 
	{
		
	}
}
