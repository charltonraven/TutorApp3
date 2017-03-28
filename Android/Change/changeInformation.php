<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";

$who=$_POST["who"];
$what=$_POST["what"];
$userID=$_POST["userID"];


/* $who="Teacher";
$what="lastName";
$lastName="boom";
$userID="FinalTest"; */

$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die('Unable to Connect');


if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();
	
}
if ($who=="Student" || $who=="Tutor" ){
	
	if($what=="firstName"){
		$firstName=$_POST["changeTo"];
		$query= mysqli_query($conn,"UPDATE student SET firstName='$firstName' WHERE studentID='$userID';" );
		echo "Complete";
	}
	if($what=="lastName"){
		$lastName=$_POST["changeTo"]; 
		$query= mysqli_query($conn,"UPDATE student SET lastName='$lastName' WHERE studentID='$userID';" );
		echo "Complete";
	}
	if($what=="password"){
		$password=$_POST["changeTo"];
		$query= mysqli_query($conn,"UPDATE student SET password='$password' WHERE studentID='$userID';" );
		echo "Complete";
	}
	if($what=="major"){
		$major=$_POST["changeTo"];
		$query= mysqli_query($conn,"UPDATE student SET major='$major' WHERE studentID='$userID';" );
		echo "Complete";
	}
}

if ($who=="Teacher" ){
	if($what=="firstName"){
		$firstName=$_POST["changeTo"];
		$query= mysqli_query($conn,"UPDATE teacher SET firstName='$firstName'WHERE teacherID='$userID';" );
		echo "Complete";
	}
	if($what=="lastName"){
		$lastName=$_POST["changeTo"]; 
		$query= mysqli_query($conn,"UPDATE teacher SET lastName='$lastName' WHERE teacherID='$userID';" );
		echo "Complete";
	}
	if($what=="password"){
		$password=$_POST["changeTo"];
		$query= mysqli_query($conn,"UPDATE teacher SET password='$password' WHERE teacherID='$userID';" );
		echo "Complete";
	}
	if($what=="major"){
		$department=$_POST["changeTo"];
		$query= mysqli_query($conn,"UPDATE teacher SET department='$major' WHERE teacherID='$userID';" );
		echo "Complete";
		
	}
}


?>