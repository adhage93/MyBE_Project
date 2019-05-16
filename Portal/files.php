<html>
<head>
<title>processed_file_stat</title>

<style>
table {
  border: 3px solid #000000;
  width: 60%;
  text-align: center;
  border-collapse: collapse;
}
table td, table th {
  border: 1px solid #000000;
  padding: 5px 4px;
}
table tbody td {
  font-size: 13px;
}
table thead {
  background: #CFCFCF;
  background: -moz-linear-gradient(top, #dbdbdb 0%, #d3d3d3 66%, #CFCFCF 100%);
  background: -webkit-linear-gradient(top, #dbdbdb 0%, #d3d3d3 66%, #CFCFCF 100%);
  background: linear-gradient(to bottom, #dbdbdb 0%, #d3d3d3 66%, #CFCFCF 100%);
  border-bottom: 3px solid #000000;
}
table thead th {
  font-size: 25px;
  font-weight: bold;
  color: #000000;
  text-align: center;
}
table tfoot td {
  font-size: 40px;
}
hr {
	color: black;
	background-color: black;
}
</style>
</head>

<body bgcolor="skyblue">

<center><h2>Modified Files</h2></center>	
<hr align="center" noshade size="4px" width="100%"></hr>
<br>
	
	<?php

		
		$client=$_POST['client'];
		
		$vm=$_POST['vm'];
		$timestamp=$_POST['timestamp'];	
		echo $timestamp;
		#$timestamp=str_replace(" ", "_", $timestamp);
	
	
	
		$timestamp1=str_replace("_", "T", $timestamp);
		echo "<br>";
		$timestamp2=str_replace(":", "-", $timestamp1);
		$tmstamp=str_split($timestamp2, 16)[0];
		
		$temp=getdb($vm);
		echo $temp;
				
		
		$filename=$temp."_".$tmstamp;
		$path1="/home/adisd/tushar/".$filename."/home/root/BKP";
		$path2="/tushar/".$filename."/home/root/BKP";
		echo $path1;
		
		function getdb($vm2)
		{
			$conn = new MongoClient("mongodb://localhost");
			$db = $conn->VMinfo;
			$table = $db->basic; 
			
			$arr=$table->find();
			$mac="";
			foreach ($arr as $key ) 
			{
				$nm=$key['VmName'];
				#print $nm;
				if(strcmp($nm,$vm2)==0)
				{
					$mac=$key['MAC'];
				}	
			}
			
			$n_mac = str_replace(":", "", $mac);						
			return $n_mac;
		}	


		echo "<center><table><tr>
						<th>No.</th>
						<th>Link To The File</th>
						</tr>";
		$dir = $path1;
		if (is_dir($dir))
		{
  			if ($dh = opendir($dir))
  			{
  				$idx = 1;
    			while (($file = readdir($dh)) !== false)
    			{
    				
    				$linknm = $dir."/".$file;
    				$path2="file:///home/adisd/tushar/".$filename."/home/root/BKP/".$file;
    			#	echo $linknm;
      			echo "<tr>
      						<td>$idx</td>
      						<td align='left'><a href=files.php download=$file '>$file</a></td>
      					<tr>";
      			$idx = $idx + 1;
    			}
    			closedir($dh);
  		}
  		echo "</table></center>";
}

	?>

</body>
</html>