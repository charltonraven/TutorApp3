<?php
require "conn.php";

$who = $_POST["who"];
$id=$_POST["id"];
$first_name = $_POST["firstname"];
$last_name = $_POST["lastname"];
$email= $_POST["email"];
$major=$_POST["major"];
$courses="";
$registered=1;
$tutor=0;
$password="password";
$department=$major;

/* 
$who = "Teacher";
$id="TestIDBlah";
$first_name = "TEstFirst";
$last_name = "TestLast";
$email= "Tfrdk@g.fmarion.edu";
$major="Computer Science";
$department=$major;
$courses="";
$registered=1;
$tutor=0;
$password="password";
 */




if($who=="Student"){
	$mysql_qry= "INSERT INTO student VALUES('$id','$email','$first_name','$last_name','$major','$courses','$registered','$tutor','$password');";
	$result= mysqli_query($conn,$mysql_qry);
	echo "Complete";
}

if ($who=="Teacher"){
	$mysql_qry= "INSERT INTO teacher VALUES('$id','$email','$first_name','$last_name','$department','$registered','$password');";
	$result= mysqli_query($conn,$mysql_qry);
	echo "Complete";
	
}


?>