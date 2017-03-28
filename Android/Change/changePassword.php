<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$who=$_POST["who"];
$userID=$_POST["user"];
$what=$_POST["what"];
$password= $_POST["password"];
/* $who="tutor"; 
$userID="cwilliams2638"; */ 
$query="";
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die('Unable to Connect');


if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();
	
}
if ($what=="Student" ||$what=="Tutor" ){
	$query= mysqli_query($conn,"UPDATE student SET password='$password' WHERE studentID='$userID';" );
	echo "Complete";
}
if ($what=="Teacher" ){
	$query= mysqli_query($conn,"UPDATE student SET password='$password' WHERE teacherID='$userID';" );
	echo "Complete";
}


?>