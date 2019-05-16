package EvidenceMngr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Organize 
{
	MngrOperations mop1 = new MngrOperations(); 
	
	List<DBObject> timeStamp(String dname,String pdname)
	{
		DB d1 = mop1.mc.getDB(dname);
		DB d2=mop1.mc.getDB(pdname);
		DBCollection col1 = d1.getCollection("total_stat");
		DBCollection col2 =d2.getCollection("processed_total_stat");
		BasicDBObject o1 = new BasicDBObject();
		BasicDBObject o2 = new BasicDBObject();
		o2.put("TimeStamp",1);
		
		DBCursor result = col1.find(o1,o2);
		o2.put("TimeStamp",1);
		List<DBObject> l1 = result.toArray();
		DBCursor result1=col2.find(o1,o2);
		List<DBObject>l2=result1.toArray();
		List<DBObject>l3=new ArrayList();
	//	System.out.println(l1.size());
//		System.out.println(l2.size());
		int flg=0;
		for(int i=0; i<l1.size(); i++)
		{
			for(int j=0;j<l2.size();j++)
			{
				
				if(l2.get(j).get("TimeStamp").equals(l1.get(i).get("TimeStamp")))
					flg=1;
			}
			if(flg==0)
				l3.add(l1.get(i));
		}
		System.out.println(l3.size());
		return l3;
	}
	
	/*void fun(String dname, String cname)
	{
		DB d2 = mop1.mc.getDB(dname);
		DBCollection col2 = d2.getCollection(cname);
		
		List<DBObject> l1 = timeStamp(dname);	
		
		String s1 = l1.get(1).get("TimeStamp").toString();
		//System.out.println(s1);
		
		DBObject o1 = new BasicDBObject("TimeStamp", s1);		
		
		DBCursor result = col2.find(o1);
		List<DBObject> l2 = result.toArray();
		
	//	System.out.println(result.toString());
	//	System.out.println(l2.size());
		
		for(int i=0; i<l2.size(); i++)
		{
	//		System.out.println(l2.get(i).toString());
		}
	}*/

	public static void main(String[] args) 
	{
		
		Organize a1 = new Organize();
		
		List <DBObject>l=a1.timeStamp("root_0200c0a80001","processed_root_0200c0a80001");
		
		a1.getCount("root_0200c0a80001","root_0200c0a80001");
	    a1.getPS("root_0200c0a80001","processed_root_0200c0a80001");
		a1.returnSortedProcess("root_0200c0a80001","processed_root_0200c0a80001",2);
		a1.processedFileStat("root_0200c0a80001","processed_root_0200c0a80001");
		a1.processedTotalStat("root_0200c0a80001","processed_root_0200c0a80001");
		a1.processedOpenPort("root_0200c0a80001","processed_root_0200c0a80001");
}
	public void getPS(String dname,String pdname)
	{
		DB d2 = mop1.mc.getDB(dname);
		DB d3 = mop1.mc.getDB(pdname);
		List<DBObject> l1=timeStamp(dname,pdname);
		DBCollection ps=d2.getCollection("ps_aux");
		
		DBCollection lsof=d3.getCollection("lsofmapped");
		List<DBObject>l3;
		System.out.println(l1.size());
		for(int i=0;i<l1.size();i++)
		{
			DBCursor dc=ps.find(new BasicDBObject("TimeStamp",l1.get(i).get("TimeStamp")));
			List<DBObject>l2=dc.toArray();
			for(int j=0;j<l2.size();j++)
			{
				DBCollection ps2=d2.getCollection("lsof_user");
				DBCursor dc1=ps2.find(new BasicDBObject("TimeStamp",l1.get(i).get("TimeStamp")).append("PID",l2.get(j).get("PID")));
				
				l3=dc1.toArray();
				if(l3.size()!=0)
				{
					BasicDBObject [] arrays=new BasicDBObject[l3.size()];
					for(int k=0;k<l3.size();k++)
					{
						BasicDBObject temp=new BasicDBObject();
						
						
						temp.append("USER", l3.get(k).get("USER"));
						temp.append("NAME", l3.get(k).get("NAME"));
						temp.append("COMMAND", l2.get(j).get("COMMANDS"));
						temp.append("SIZE_OFF", l2.get(j).get("SIZE_OFF"));
						temp.append("TYPE", l2.get(j).get("TYPE"));
						arrays[k]=temp;
					}
					BasicDBObject insert1=new BasicDBObject();
					insert1.append("TIMESTAMP",l2.get(j).get("TimeStamp"));
					insert1.append("PID",l2.get(j).get("PID"));
					insert1.append("FILES", arrays);
					lsof.insert(insert1);
				}
				else
				{
					
				}
				
				
			}
			System.out.println("NEW TIMESTAMP");
		}
		
		System.out.println("\ngetPS Done!");
		
	}
	
	public void getCount(String dname,String pdname)
	{
		DB d2 = mop1.mc.getDB(dname);
		DB d3 = mop1.mc.getDB(pdname);
		DBCollection dc=d2.getCollection("tcpdump");
		DBCollection dc2=d3.getCollection("processed_netstat_pntu");
		
		
		List <DBObject>l3=timeStamp(dname,pdname);
		System.out.println(l3.size());
		ArrayList<String>a1=new <String>ArrayList();
		for(int j=0;j<l3.size();j++)
		{
		
			String time=l3.get(j).get("TimeStamp").toString();
			System.out.println(time);
			List l1=dc.distinct("source_addr",new BasicDBObject().append("TimeStamp", time));
			System.out.println(l1.size());
			BasicDBObject o1=new BasicDBObject();
			BasicDBObject o2=new BasicDBObject();
			o2.append("TimeStamp", time);
			//o1.append("source_addr",1);
			List <DBObject>l2=dc.find(o2).toArray();
			System.out.println(l2.size());
			if(l2.size()!=0) 
			{
				
			for(int i=0;i<l1.size();i++)
			{
				String temp=(String)l1.get(i);
				
				int ocu=0;
				for(int k=0;k<temp.length();k++)
				{	if(temp.charAt(k)=='.')
						ocu++;
				}
				if(ocu==4) {
				  temp=temp.substring(0,temp.lastIndexOf('.'));
				}
				if(a1.size()==0)
					a1.add(temp);
				else
				{
					if(!a1.contains(temp))
						a1.add(temp);
				}	
			}
			for(int i=0;i<a1.size();i++)
			{
				int cnt=0;
				for(int k=0;k<l2.size();k++)
				{
					
					String temp1=l2.get(k).get("source_addr").toString();
					if(temp1.contains(a1.get(i)))
					{
						cnt++;
					}
				}
				BasicDBObject insert1=new BasicDBObject();
				insert1.append("TimeStamp", time);
				insert1.append("IP", a1.get(i));
				
				insert1.append("Address Count",cnt);
				dc2.insert(insert1);
				System.out.println("Temp"+i);
			}
			
			}
		}
		
		System.out.println("\ngetCount Done!");
		
	}
	
	List<String> timeStamp1(String dname)
	{
		DB d1 = mop1.mc.getDB(dname);
		DBCollection col1 = d1.getCollection("total_stat");
		
		
		List<String> l1 = col1.distinct("TimeStamp");
		return l1;
	}
	
	public void returnSortedProcess(String dname,String pdname,int count)
	{
		DB db = mop1.mc.getDB(dname);
		DB pdb = mop1.mc.getDB(pdname);
		DBCollection colps = db.getCollection("ps_aux");
		DBCollection dc1=pdb.getCollection("processed_ps_aux");
		
		List <String> list=timeStamp1(dname);
		
		int i=0;
		while(i<list.size())
		{
			
			List <String>l1=colps.distinct("PID", new BasicDBObject("TimeStamp",list.get(i)));
			System.out.println(Arrays.toString(l1.toArray()));
			for(int j=0;j<l1.size();j++)
			{
				
				String pid=l1.get(j);
				
				for(int k=i;k<list.size();k++)
				{
					DBCursor db0=colps.find(new BasicDBObject("PID",pid).append("TimeStamp", list.get(k)));
					int cnt=0;
					if(db0.count()!=0) 
					{
						for(int l=k+1;l<list.size();l++)
						{
							DBCursor db1=colps.find(new BasicDBObject("PID",pid).append("TimeStamp", list.get(l)));
							
							if(db1.count()==0)
								break;
							else
								cnt++;
						}
					}
					if(cnt>=count)
					{
						
						BasicDBObject bdb=new BasicDBObject();
						bdb.append("PID", l1.get(j)).append("TimeStamp", list.get(k));
						DBCursor cur=colps.find(bdb);
						List <DBObject>l2=cur.toArray();
						DBObject dbo=l2.get(0);
						
						System.out.println(dbo);
						if(dbo!=null)
						{	
						BasicDBObject bdb1=new BasicDBObject("PID", dbo.get("PID").toString());
						System.out.println(dbo.get("TimeStamp").toString());
						bdb1.append("START", dbo.get("START ").toString());
						bdb1.append("USER", dbo.get("USER").toString()).append("COMMAND", dbo.get("COMMAND").toString()).append("Max Running Time", ""+30*cnt+"Mins");
						dc1.insert(bdb1);
						System.out.println(bdb1);
						}
					}
				}
			}
			i++;
		}
		
		System.out.println("\ngetSortedPS Done!");
	}
	
	public void processedFileStat(String dname,String pdname)
	{
		DB d2 = mop1.mc.getDB(dname);
		DB d3 = mop1.mc.getDB(pdname);
		DBCollection dc2 = d2.getCollection("file_stat");
		DBCollection dc3 = d3.getCollection("processed_file_stat");
		if(dc3 == null)
		{
			DBObject options = new BasicDBObject();
		    dc3 = d3.createCollection("processed_file_stat",options);
		}
		
		
		DBCursor c1 = dc2.find();
		List<DBObject> l1 = c1.toArray();
		
		for(int i=0; i<l1.size(); i++)
		{
			DBObject dbo=l1.get(i);
			dbo.removeField("_id");
			dc3.insert(l1.get(i));
		}
		
		System.out.println("\nprocessedFileStat Done!");
	}
	
	public void processedTotalStat(String dname,String pdname)
	{
		DB d2 = mop1.mc.getDB(dname);
		DB d3 = mop1.mc.getDB(pdname);
		DBCollection dc2 = d2.getCollection("total_stat");
		DBCollection dc3 = d3.getCollection("processed_total_stat");
		if(dc3 == null)
		{
			DBObject options = new BasicDBObject();
		    dc3 = d3.createCollection("processed_total_stat",options);
		}
		
		DBCursor c1 = dc2.find();
		List<DBObject> l1 = c1.toArray();
		
		for(int i=0; i<l1.size(); i++)
		{
			DBObject dbo=l1.get(i);
			dbo.removeField("_id");
			dc3.insert(dbo);
		}
		
		System.out.println("\nprocessedTotalStat Done!");
	}
	
	public void processedOpenPort(String dname,String pdname)
	{
		DB d2 = mop1.mc.getDB(dname);
		DB d3 = mop1.mc.getDB(pdname);
		DBCollection dc2 = d2.getCollection("open_port");
		DBCollection dc3 = d3.getCollection("processed_open_port");
	
		DBCursor c1 = dc2.find();
		List<DBObject> l1 = c1.toArray();
		
		for(int i=0; i<l1.size(); i++)
		{
			DBObject dbo=l1.get(i);
			dbo.removeField("_id");
			try
			{
			dc3.insert(dbo);
			}
			catch(Exception e) {i++;}
		}
		
		System.out.println("\nprocessedOpenPort Done!");
	}
}


