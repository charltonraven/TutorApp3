<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die("Error".mysqli_error($conn));

$query= mysqli_query($conn,"Select lastName, firstName FROM teacher ORDER BY lastName;");
	while($row=mysqli_fetch_array($query)){
		$flag[]=$row;
	}
	print(json_encode($flag));


mysqli_close($conn)


?>