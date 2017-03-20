<?php


#$db_name = "tutor3test";
$firstName=$_POST["firstName"];
$lastName=$_POST["lastName"];
$who=$_POST["who"];
$result="";
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die("Error".mysqli_error($conn));



/* $Abbr="CS";
$ClassName="190";
$firstName="Charlton";
$lastName="Williams"; */

//$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die('Unable to Connect');


if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();	
}
if($who=="student"){/* Tutor address */
$query= mysqli_query($conn,"Select * FROM student where firstName='$firstName' AND lastName='$lastName' and tutor=1");
	while($row=mysqli_fetch_array($query)){
		$flag[]=$row;
	}
	print(json_encode($flag));
}

mysqli_close($conn)


?>