import sys,subprocess
import pymongo
import datetime
from pymongo.errors import ConnectionFailure # For catching exeptions
from platform import uname

def vm_info(username,mac):
    try:
        mcli = pymongo.MongoClient(mainIP, 27017) # localhost and port
        print "Connected to MongoDB successfully!"
    except ConnectionFailure, e:
        sys.stderr.write("Could not connect to MongoDB: %s" % e)
        
    db1 = mcli.get_database("VMinfo")
    #print db1
    #col1 = db1.get_collection("basic")
    #print col1
    
    m_new = mac[0:2]+":"+mac[2:4]+":"+mac[4:6]+":"+mac[6:8]+":"+mac[8:10]+":"+mac[10:]
    
    temp = db1.basic.update({"MAC":m_new,"uname":{"$exists":False}},{"$set":{"uname":username}})
    print str(temp)
    

def lsof_user():
    line = ""
    try:       
#lsof_user.txt-----------------------------------------------------------
        with open("/home/"+u_name+"/logs/lsof_user.txt","r") as f:
            lines = f.readlines()[1:]
            i = 0
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")
                            
                        COMMANDS = text[0]
                        PID = text[1]
                        USER = text[2]
                        FD = text[3]
                        TYPE = text[4]
                        DEVICE = text[5]
                        SIZE_OFF = text[6]
                        NODE = text[7]
                        NAME = text[8]
                          
                        record = {
                            "COMMANDS" : COMMANDS,
                            "PID" : PID,
                            "USER" : USER,
                            "FD" : FD,
                            "TYPE" : TYPE,
                            "DEVICE" : DEVICE,
                            "SIZE_OFF" : SIZE_OFF,
                            "NODE" : NODE,
                            "NAME" : NAME,
                            "TimeStamp" : TM
                        }
                        
                        lsof_user1=db_target.lsof_user
                        lsof_user1.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
        
        f.close()
        print "\nfile lsof_user.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[0]=i    
    
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def last():
    line = ""
    try:
#last.txt---------------------------------------------------------------    
        with open("/home/"+u_name+"/logs/last.txt","r") as f:
            lines = f.readlines()
            i = 0
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")        
                        
                        user=""
                        device=""
                        hostip=""
                        start_time=""
                    
                        user = text[0]
                        if(text[1]=='system'):    
                            device = text[1]+" "+text[2]
                            hostip = text[3]
                            start_time = text[4]
                        else:
                            device = text[1]
                            hostip = text[2]
                            start_time = text[3]
        
                        record = {
                            "User/runlevel":user,
                            "Device/Action":device,
                            "Host_IP":hostip,
                            "Start_time":start_time,
                            "TimeStamp" : TM
                                  }
        
                        last1=db_target.last.insert_one(record)
                        i=i+1; 
            else:
                i=0;
                print "\nNo records present!"
        
        f.close()
        print "\nfile last.txt parsed!"
        print "Total records added :"+str(i)
        #number.append(i)
        number[1]=i
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))
        
def netstat_au():
    line = ""
    try:
#netstat_au.txt----------------------------------------------------------
        with open("/home/"+u_name+"/logs/netstat_au.txt","r") as f:
            lines = f.readlines()[2:]
            i = 0
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")
        
                        Proto = text[0]
                        Recv_Q=text[1]
                        Send_Q=text[2]
                        Local_Address = text[3]
                        Foreign_Address= text[4]
                        State = text[5]
                          
                        record = {
                            "Proto":Proto,
                            "Recv-Q":Recv_Q,
                            "Send-Q":Send_Q,
                            "Local Address":Local_Address,
                            "Foreign Address":Foreign_Address,
                            "State":State,
                            "TimeStamp" : TM
                              }
                        
                        netstat_udp1=db_target.netstat_udp.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
        
        f.close()
        print "\nfile netstat_au.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[2]=i
    
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def netstat_at():
    line = ""
    try:
#netstat_tcp------------------------------------------------------------             
        with open("/home/"+u_name+"/logs/netstat_at.txt","r") as f:
            lines = f.readlines()[2:]
            i=0
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")
                        
                        Proto = text[0]
                        Recv_Q=text[1]
                        Send_Q=text[2]
                        Local_Address = text[3]
                        Foreign_Address= text[4]
                        State=text[5]
                          
                        record = {
                            "Proto":Proto,
                            "Recv-Q":Recv_Q,
                            "Send-Q":Send_Q,
                            "Local Address":Local_Address,
                            "Foreign Address":Foreign_Address,
                            "State":State,
                            "TimeStamp" : TM
                              }
                        
                        netstat_tcp1=db_target.netstat_tcp.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
        
        f.close()
        print "\nfile netstat_at.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[3]=i
    
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def ps_aux():
    line = ""
    try:
#ps_aux.txt---------------------------------------------------------
        with open("/home/"+u_name+"/logs/ps_aux.txt","r") as f:
            i=0
            lines = f.readlines()[1:]
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")
        
                        USER= text[0]
                        PID=text[1]
                        CPU=text[2]
                        MEM=text[3]
                        VSZ=text[4]
                        RSS=text[5]
                        TTY=text[6]
                        STAT=text[7]
                        START=text[8]
                        TIME=text[9]
                        COMMAND=text[10]
                        
                        record = {
                            "USER":USER,
                            "PID":PID,
                            "%CPU":CPU,
                            "%MEM":MEM,
                            "VSZ":VSZ,
                            "RSS":RSS,
                            "TTY":TTY,
                            "STAT ":STAT,
                            "START ":START,
                            "TIME":TIME,
                            "COMMAND":COMMAND,
                            "TimeStamp" : TM
                                  }
                        
                        ps_aux1=db_target.ps_aux.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
        
        f.close()
        print "\nfile ps_aux.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[4]=i
    
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def netstat_pntu():
    line = ""
    try:
#netstat_lntu.txt---------------------------------------------------
        with open("/home/"+u_name+"/logs/netstat_pntu.txt","r") as f:
            i=0
            lines = f.readlines()[2:]
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")
                        
                        Proto = text[0]
                        Recv_Q=text[1]
                        Send_Q=text[2]
                        Local_Address = text[3]
                        Foreign_Address= text[4]
                        State=text[5]
                        PID=text[6]
                        
                        record = {
                            "Proto":Proto,
                            "Recv-Q":Recv_Q,
                            "Send-Q":Send_Q,
                            "Local Address":Local_Address,
                            "Foreign Address":Foreign_Address,
                            "State":State,
                            "TimeStamp":TM,
                            "PID/Program name":PID
                                  }
                        
                        netstat_pntu1=db_target.netstat_pntu.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
        
        f.close()
        print "\nfile netstat_pntu.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[5]=i
        
    
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def lspci():
    line = ""
    try:
#lspci.txt----------------------------------------------------------     
        with open("/home/"+u_name+"/logs/lspci.txt","r") as f:
            i=0
            lines = f.readlines()
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split()
                        bus=text[0]
                        device=" ".join(text[1:])
                        
                        record = {
                            "bus:device:function":bus,
                            "device":device,
                            "TimeStamp" : TM
                                  }
                        
                        lspci1=db_target.lspci.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
                        
        f.close()
        print "\nfile lscpi.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[6]=i
        
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def partition_info():
    line = ""
    try:
#partition_info.txt-------------------------------------------------
        with open("/home/"+u_name+"/logs/partition_info.txt","r") as f:
            i=0
            lines = f.readlines()[3:]
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")
                        
                        major=text[0]
                        minor=text[1]
                        blocks=text[2]
                        name=text[3]
                          
                        record = {
                            "major ":major,
                            "minor":minor,
                            "blocks":blocks,
                            "name": name,
                            "TimeStamp" : TM
                              }
                        
                        partition_info1=db_target.partition_info.insert(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
                        
        f.close()
        print "\nfile partition_info.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[7]=i
    
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def open_port():
    line = ""
    try:
#lsof -i TCP.txt-------------------------------------------------
#COMMAND    PID   USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
        with open("/home/"+u_name+"/logs/open_port.txt","r") as f:
            i=0
            lines = f.readlines()[1:]
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split()
                        
                        COMMAND=text[0]
                        PID =text[1]
                        USER  =text[2]
                        FD =text[3]
                        TYPE =text[4]
                        DEVICE =text[5]
                        SIZE_OFF=text[6]
                        NODE=text[7]
                        #NAME=text[8:]
                        NAME=" ".join(text[8:])
                          
                        record = {
                            "COMMAND":COMMAND,
                            "PID": PID ,
                            "USER ":USER ,
                            "FD":   FD ,
                            "TYPE": TYPE,
                            "DEVICE":DEVICE,
                            "SIZE_OFF":SIZE_OFF,
                            "NODE ":NODE,
                            "NAME":NAME,
                            "TimeStamp" : TM
                              }
                        
                        open_port1=db_target.open_port.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
        
        f.close()
        print "\nfile open_port.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[8]=i
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()[0:]))

def file_stat():
    line = ""
    try:
#stat.txt-------------------------------------------------
        with open("/home/"+u_name+"/logs/stat.txt","r") as f:
            i=0
            lines = f.readlines()
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text=line.split("__")
                                            
                        record = {
                            "File": text[0],
                            "File-Type": text[1], 
                            "Size": text[2],          
                            "Blocks": text[3],          
                            "IO Block": text[4],   
                            "directoryDevice": text[5],    
                            "Inode": text[6],      
                            "Links": text[7],
                            "AccessPermissions": text[8],  
                            "Uid": text[9],   
                            "Gid": text[10],
                            "Access": text[11],
                            "Modify": text[12],
                            "Change": text[13],
                            "SecurityContext": text[14],
                            "Birth": text[15],
                            "TimeStamp" : TM
                                  }
                        
                        file_stat1=db_target.file_stat.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
            
        f.close()
        print "\nfile stat.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[9]=i
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()))
        

def tcpdump():
    line = ""
    try:
#tcpdump.txt-------------------------------------------------
        with open("/home/"+u_name+"/logs/tcpdump.txt","r") as f:
            i=0
            lines = f.readlines()
            if lines != None :
                for line in lines:
                    if(line!="\n"):
                        text = []
                        r = line.split(" ")
                        if(len(r)>2):
                            text.append(r[0].split(".")[0])
                            text.append(r[2])
                            text.append(r[4].split(":")[0])
                            text.append(r[5])
                            text.append(r[len(r)-1])
                                            
                        record = {
                            "packet_timestamp": text[0],
                            "source_addr": text[1], 
                            "destination_addr": text[2],          
                            "protocol": text[3],          
                            "packet_size": text[4],   
                            "TimeStamp":TM
                                  }
                        
                        tcpdump1=db_target.tcpdump.insert_one(record)
                        i=i+1
            else:
                i=0;
                print "\nNo records present!"
            
        f.close()
        print "\nfile tcpdump.txt parsed!"
        print "Total Records inserted: "+str(i)
        number[10]=i
    except :
        print( "Something wrong with this line: " + line + '\n' + str(sys.exc_info()))
        


def total_stat():
    try:
#Statistics--------------------------------------------------
        Record = {
            "lsof_user" : number[0],
            
            "last" : number[1],
            "netstat_au" : number[2],
            "netstat_at" : number[3],
            "ps_aux" : number[4],
            "netstat_pntu" : number[5],
            "lspci" : number[6],
            "partition_info" : number[7],
            "open_port" : number[8],
            "stat" : number[9],
            "tcpdump" : number[10],
            "TimeStamp" : TM
            }
        
        total_stat1=db_target.total_stat.insert_one(Record)
        print "\nStatistics collection created!"
        
    except :
        print(str(sys.exc_info()))
         
         
#MainFunction-------------------------------------------------------
global line
global number
global u_name
global TM
global db_target

mainIP = "192.168.0.16"

try:
    db_conn = pymongo.MongoClient(mainIP, 27017) # localhost and port
    print "Connected to MongoDB successfully!"
except ConnectionFailure, e:
    sys.stderr.write("Could not connect to MongoDB: %s" % e)
    
#get username from system
u_name=str(subprocess.check_output("whoami"))
u_name=u_name.split()[0] 

#get uuid from system(UNIVERSALLY UNIQUE IDENTIFIER)
macaddr = str(subprocess.check_output("uuidgen -t", shell=True))
macaddr = str((macaddr.split("-")[4]).split("\n")[0])

# Grab a database
DBname = u_name + "_" + macaddr

db_target =db_conn.get_database(DBname , None , None , None , None)

#system call
#os.system("sh script.sh")
TM = str(datetime.datetime.now())
print "\nLogs generated successfully..."

number = [0,0,0,0,0,0,0,0,0,0,0]

vm_info(u_name, macaddr)
lsof_user()
last()
netstat_au()
netstat_at()
ps_aux()
netstat_pntu()
lspci()
partition_info()
open_port()
file_stat()
tcpdump()
total_stat()
