<html>

<frameset cols="25%,*"> 
	<frameset rows="10%,30%,*">
	   <frame src="title.html" name="frame0" noresize frameborder="1" scrolling="no" marginheight="20px">
		<frame src="temp.php" name="frame1" noresize frameborder="1" scrolling="no"> 
		<frame src="main.php" name="frame2" noresize frameborder="1" scrolling="no">
	</frameset> 
	<frame src="table.html" name="frame3" scrolling="auto" noresize> 
</frameset>

<?php
$con=mysqli_connect("localhost","root","4946","login");
    		$rs=mysqli_query($con,"select * from login where username='$username' and password='$password'") or die(mysqli_connect_error());
			if(mysqli_num_rows($rs)==1)
			{
        		$_SESSION[login]="true";
        		//echo "sucess";
				// Create connection
				$conn = new mysqli("localhost","root", "4946", "cof");
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}


				date_default_timezone_set('Asia/Kolkata');
				//echo date('d-m-Y H:i');

				$date = date('Y/m/d H:i:s');
				$sql = "INSERT INTO coc (username, time)
				VALUES ('$username', '$date')";

				if ($conn->query($sql) === TRUE) {
				   // echo "New record created successfully";
				} else {
				   // echo "Error: " . $sql . "<br>" . $conn->error;
				}

				$conn->close();
        		
        		#include("frame.php");
        		echo "<a href="frame.php">PLease Proceed to Portal</a>";

			}
    		else
    		{
 		   	echo "Username and Password Combination is not matching !! Try Again !!";
	
    		}
		} 
		else if(!isset($_SESSION[login]))
		{	
	?>		
		<br><div class="alert alert-danger" role="alert"><h4><center>Your are not logged in</center>
	<?php
		
		exit;
		//echo "<a href=fhp4.php>start</a>";
		}
	?>
?>
</html>
