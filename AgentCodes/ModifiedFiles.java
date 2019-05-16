package AgentCodes;

import java.io.*;

class ModifiedFiles
{
	public int time=-30;
	
	public String getUserName()
	{
		String user = "";
		try
		{
			
			Process p=Runtime.getRuntime().exec("whoami");	
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str=br.readLine();
			user=str;
			while(str!=null)
			{
				str=br.readLine();
			}
			
		}
		catch(Exception e)
		{
			
		}
		return user;
	}
	
	public void getUpdatedFile()
	{
		try
		{
			String cmd = "find /"+" -mmin "+time+" -print";
			System.out.println(cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str=br.readLine();
			File file=new File("fileNames.txt");
			FileOutputStream fos=new FileOutputStream(file);
			String newline="\n";
			while(str!=null)
			{
				fos.write(str.getBytes());
				fos.write(newline.getBytes());
				str=br.readLine();
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void copyFiles()
	{
		String data1 = "";
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(new File("fileNames.txt")));
			data1=br.readLine();
			String data=data1;
			while(data1!=null)	
			{
				if(!data.contains("BKP.tar.gz"))
				{
					Process p=Runtime.getRuntime().exec("cp "+data+" /home/"+getUserName()+"/BKP/");
					data=data1;
					data1=br.readLine();
				}
				else
				{
					data=data1;
					data1=br.readLine();
				}
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void compressBKP()
	{
		try
		{
			/*
			 * tar -zcvf archive-name.tar.gz directory-name
				Where,
			    -z : Compress archive using gzip program
			    -c: Create archive
			    -v: Verbose i.e display progress while creating archive
			    -f: Archive File name
			    -x: to extract the file
			 */
			Process p=Runtime.getRuntime().exec("tar -zcf BKP.tar.gz /home/"+getUserName()+"/BKP");	
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str=br.readLine();
			while(str!=null)
			{
				System.out.println(str);
				str=br.readLine();
			}
			
		}
		catch(Exception e)
		{
			
		}
	}

	public void getJson()
	{
		try
		{
			BufferedReader sc=new BufferedReader(new FileReader(new File("fileNames.txt")));
			String str=sc.readLine();
			String data="";
			FileOutputStream fos=new FileOutputStream(new File("/home/"+getUserName()+"/logs/stat.txt"));
			while(str!=null)
			{
				String cmd = "stat --printf %n__%F__%s-bytes__%b(of:%B)__%o__(%Dh/%dd)__%i__%h__(%a)%A__(%u/%U)__(%g/%G)__%x__%y__%z__%w__ "+str;
				Process p=Runtime.getRuntime().exec(cmd);	
				BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String string=br.readLine();
				while(string!=null)
				{
					data=data+string;
					string=br.readLine();
				}
				data=data+"\n";
				str=sc.readLine();
				
			}
			fos.write(data.getBytes());
			fos.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	}	
}
