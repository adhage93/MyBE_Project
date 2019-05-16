#!/bin/sh -e

u_name=$(whoami)

if [ -d "/home/$u_name/BKP" ] 
then
    echo "Directory /home/$u_name/BKP exists.";
else
    mkdir /home/$u_name/BKP;
    echo "Directory /home/$u_name/BKP created!";
fi

if [ -d "/home/$u_name/logs" ] 
then
    echo "Directory /home/$u_name/logs exists.";
else
    mkdir /home/$u_name/logs;
    echo "Directory /home/$u_name/logs created!";
fi

#commands for logging start here

lsof -u $u_name | awk '{print $1 "__" $2 "__" $3 "__" $4 "__" $5 "__" $6 "__" $7 "__" $8 "__" $9"__"}' > /home/$u_name/logs/lsof_user.txt;

last -i --time-format iso | awk '{print $1"__"$2"__"$3"__"$4"__"$5"__"$6"__"}' > /home/$u_name/logs/last.txt;

netstat -au | awk '{print $1"__"$2"__"$3"__"$4"__"$5"__"$6"__"}' > /home/$u_name/logs/netstat_au.txt;

netstat -at | awk '{print $1"__"$2"__"$3"__"$4"__"$5"__"$6"__"}' > /home/$u_name/logs/netstat_at.txt;

netstat -pntu | awk '{print $1"__"$2"__"$3"__"$4"__"$5"__"$6"__"$7"__"}' > /home/$u_name/logs/netstat_pntu.txt;

ps aux | awk '{print $1"__"$2"__"$3"__"$4"__"$5"__"$6"__"$7"__"$8"__"$9"__"$10"__"$11"__"}' > /home/$u_name/logs/ps_aux.txt; 

cat /proc/partitions | awk '{print $1"__"$2"__"$3"__"$4"__"}' >  /home/$u_name/logs/partition_info.txt;

lsof -i  > /home/$u_name/logs/open_port.txt;

lspci > /home/$u_name/logs/lspci.txt;

sudo timeout 10 tcpdump -p -n -q >  /home/$u_name/logs/tcpdump.txt;

exit 0
