<?php
	/* Create simple Website with PHP - http://coursesweb.net/php-mysql/ */
	// create an array with data for title, and meta, for each page
	$pgdata = array();
	$pgdata['index'] = array(
 	'title'=>'Chain Of Custody',
 	'description'=>'Here add the description for Home page',
 	'keywords'=>'meta keywords, for, home page'
	);

	// set the page name
	$pgname = isset($_GET['pg']) ? trim(strip_tags($_GET['pg'])) : 'index';

	// get title, and meta data for current /accessed page
	$title = $pgdata[$pgname]['title'];
	$description = $pgdata[$pgname]['description'];
	$keywords = $pgdata[$pgname]['keywords'];
	// set header for utf-8 encode
	header('Content-type: text/html; charset=utf-8');
?>

<!doctype html>
<html>

<head>
 <meta charset="utf-8" />
 <title><?php echo $title; ?></title>
 <meta name="description" content="<?php echo $description; ?>" />
 <meta name="keywords" content="<?php echo $keywords; ?>" />
 <!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
 
<style>
table {
		
	border: 2px solid #A40808;
  	background-color: #EEE7DB;
  	width: 100%;
  	text-align: center;
 	border-collapse: collapse;
}
table td, th {
  border: 1px solid #AAAAAA;
  padding: 3px 2px;
}
table tbody td {
  font-size: 13px;
}
table tr:nth-child(even) {
  background: #F5C8BF;
}
table thead {
  background: #A40808;
}
table thead th {
  font-size: 19px;
  font-weight: bold;
  color: #FFFFFF;
  text-align: center;
  border-left: 2px solid #A40808;
}
table thead th:first-child {
  border-left: none;
}
 <!--
 body {
 margin:0;
 text-align:center;
 padding:0 1em;
 }
 header, footer, section, aside, nav, article { display: block; }
 #posts{
 position:relative;
 width:99%;
 margin:0.5em auto;
 background:#fdfefe;
 }
 #menu {
 float:left;
 width:15em;
 margin:0 auto;
 background:#f8f9fe;
 border:1px solid blue;
 text-align:left;
 }
 #menu li a:hover {
 text-decoration:none;
 color:#01da02;
 }
 #article {
 margin:0 1em 0 16em;
 background:#efeffe;
 border:1px solid #01da02;
 padding:0.2em 0.4em;
 }
 #footer {
 clear:both;
 position:relative;
 background:#edfeed;
 border:1px solid #dada01;
 width:99%;
 margin:2em auto 0.5em auto;
 }

 -->
 
</style>
</head>

<body>
<header id="header">
 <h1><?php echo $title; ?></h1>
</header>

<section id="posts">
	<nav id="menu">
	<ul>
	<li><a href="../Login Expert/index.html" title="Login To Portal">Login To Portal</a></li>
	<li><a href="logout.php" title="Logout">Logout</a></li>
 
	</ul>
	</nav>
	<article id="article"><?php echo file_get_contents('pages/'. $pgname. '.htm'); ?></article>
</section>

<form action="checkbox-form.php" method="post">
	Username
   <input type="checkbox" name="box" value="Yes" />
   <input type="submit" name="formSubmit" value="Submit" />
</form>

<?php
	$servername = "localhost";
	$username = "root";
	$password = "4946";
	$dbname = "cof";

	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);	
	// Check connection
	if ($conn->connect_error) 
	{
   	die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "SELECT username, time FROM coc";
	$result = $conn->query($sql);

	if ($result->num_rows > 0) 
	{
   	echo "<table><tr><th>Username</th><th>Time</th></tr>";
   	// output data of each row
    	while($row = $result->fetch_assoc()) 
    	{
      	echo "<tr><td>" . $row["username"]. "</td><td>" . $row["time"]. "</td></tr>";
    	}
    	echo "</table>";
	}
	else 
	{
    	echo "0 results";
	}
	$conn->close();
?> 

<footer id="footer">
 	<p>Copyright @ Cloud Forensics 2018</a></p>
</footer>

</body>
</html>