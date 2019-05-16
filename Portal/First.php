<!doctype html public "-//w3c//dtd html 3.2//en">
<html>

<head>
<title>Combobox</title>
<SCRIPT TYPE="text/javascript">

function reload(form)
{
	var val=form.client.options[form.client.options.selectedIndex].value; 
	self.location='temp.php?Client=' + val ;
}

function reload2(form)
{
	var val=form.client.options[form.client.options.selectedIndex].value; 
	var val2=form.vm.options[form.vm.options.selectedIndex].value; 
	self.location='temp.php?Client=' + val + '&Vm=' + val2 ;
}

</SCRIPT>

<style>
.combobox
{
  color: #fff;
  background-color: #aaa;
  letter-spacing: 2px;
  font-size: 12px;
  padding: 5px 10px;
  border-radius: 5px;
  width:80%;
}
input[type=submit]
{
	

  color: #fff;
  background-color: #aaa;
  text-transform: uppercase;
  letter-spacing: 2px;
  font-size: 12px;
  padding: 5px 10px;
  border-radius: 5px;
  width:60%;	

}
</style>
</head>

<body bgcolor="black">
<?php

	function getClients()
	{
		$conn = new MongoClient("mongodb://localhost");
		$coll = $conn->VMinfo; 
		$table= $coll->basic;
		$arr=$table->distinct("Owner");	
	
		return $arr;
	}
	
	function getVmsbyClient($a)	
	{
		$conn = new MongoClient("mongodb://localhost");
		$coll = $conn->VMinfo; //localhost");
		$sweetQuery = array('Owner' => $a);
		$table= $coll->basic;
		print $a;
		$arr=$table->distinct("VmName",$sweetQuery);
		return $arr;
	}

	function getVMdbs($vm)
	{
		$conn = new MongoClient("mongodb://localhost");
		$db = $conn->VMinfo; //localhost");
		$table = $db->basic; 
		#print "1".$vm;
		$sweetQuery = array('VmName' => $vm);
		#print "2";	
		$arr=$table->find();
		$mac="";
		foreach ($arr as $key ) 
		{
			$nm=$key['VmName'];
			#print $nm;
			if(strcmp($nm,$vm)==0)
			{
				$mac=$key['MAC'];
			}	
		}

		#print $mac;
		$names=array();
		$dbases = $conn->listDBs(); 
		
		$num = 0;
		#echo '';
		$name="";
		
		foreach ($dbases['databases'] as $dbs) 
		{
			#print_r($dbs);
		   $nm=$dbs['name'];
		 	if(strpos($nm,$mac)!==FALSE)
		   {
		   	if(strpos($nm,"processed") !== FALSE)
		    		$name=$nm;
		   } 
		}
		
		if($name === "")
		{	
			$names[0]="NO DATA AVAILABLE";
			return $names;
		}
		
		$db=$conn->$name;
		$total_stat=$db->processed_total_stat;
		$timestamp=$total_stat->distinct("TimeStamp");
		#echo "";
		return $timestamp;
}

$quer2=getClients();

$cat=$_GET['Client']; // This line is added to take care if your global variable is off

if(isset($cat) and strlen($cat) > 0)
{
	$quer=getVmsbyClient($cat);
#	echo "<br>$quer";
}
else
{
	$quer="VM1-27";
} 

$dbs=$_GET['Vm'];

if(isset($dbs) and strlen($dbs) > 0)
{
	$db=getVMdbs($dbs);
	#print_r($db);
	if ($db == NULL)
		$db[0]="NO DATA ALLOCATED";
	#echo "<br>$quer";
}
else
{
	$db=array();
	#echo "<br>$quer";
}

echo "<center><br>";

echo "<select name='client' class='combobox' onchange=\"reload(this.form)\"><option value=''>Select Client</option>";
foreach ($quer2 as $noticia2) 
{
	echo "<br>$noticia2";
	if($noticia2==@$cat)
		echo "<option selected value='$noticia2'>$noticia2</option>";
	else
		echo "<option >$noticia2</option>";
}
echo "</select>";
echo"<br><br>";

echo "<select name='vm' class='combobox' onchange=\"reload2(this.form)\"><option value=''>Select VM</option>";
foreach ($quer as $noticia) 
{
	if($noticia==@$dbs)
		echo "<option selected value='$noticia'>$noticia</option>";
	else
		echo "<option >$noticia</option>";
	
}
echo "</select>";
echo "<br><br>";

echo "<select name='timestamp' class='combobox'><option value=''>Select Time</option>";
foreach ($db as $timestamps) 
{
	
	echo "<option selected value='$timestamps'>$timestamps</option>";
	
	
}
echo "</select>";

echo "<br><br>";

echo "<input type='submit'  value=\"Submit\" onclick='main.php' target='frame2'/></form>";
echo "</center>";

?>
<br>
<center><a href=.php>Reset and Try again</a></center> 
</body>
</html>
