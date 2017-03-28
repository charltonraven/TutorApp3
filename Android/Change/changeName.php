<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
/* $who=$_POST["who"];
$userID=$_POST["user"];
$firstName=$_POST["firstName"];
$lastName=$_POST["lastName"]; */
$who="Tutor"; 
$userID="cwilliams2638"; 
$firstName="Williams";
$lastName="Charlton";
$query="";
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die('Unable to Connect');


if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();
	
}
if ($who=="Student" ||$who=="Tutor" ){
	$query= mysqli_query($conn,"UPDATE student SET firstName='$firstName',lastName='$lastName' WHERE studentID='$userID';" );
	echo "Complete";
}
if ($who=="Teacher" ){
	$query= mysqli_query($conn,"UPDATE student SET firstName='$firstName',lastName='$lastName' WHERE teacherID='$userID';" );
	echo "Complete";
}


?>