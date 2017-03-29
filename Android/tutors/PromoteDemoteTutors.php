<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die("Error".mysqli_error($conn));

$promote=$_POST["promote"];
$firstName=$_POST["firstName"];
$lastName=$_POST["lastName"];

/* $promote="true";
$firstName="Student";
$lastName="Test"; */

if($promote=="true"){
	$query= mysqli_query($conn,"UPDATE student SET tutor='1' WHERE firstName='$firstName' AND lastName='$lastName';" );
	echo "Complete";
}

if($promote=="false"){
	$query= mysqli_query($conn,"UPDATE student SET tutor='0' WHERE firstName='$firstName' AND lastName='$lastName';" );
	echo "Complete";
	
}

mysqli_close($conn)


?>