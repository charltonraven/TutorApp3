<?php
require "conn.php";

//$who=$_POST["who"];
$user_name = $_POST["username"];
$user_pass = $_POST["password"];

/* $user_name = "DLeon4743@gmail.com";
$user_pass = "password"; */

$mysql_qryStudent= "select * from student where email = '$user_name' and password = '$user_pass';";
$mysql_qryTutor= "select * from student where email = '$user_name' and password = '$user_pass' and tutor=1;";
$mysql_qryTeacher= "select * from teacher where email = '$user_name' and password = '$user_pass';";

$result= mysqli_query($conn,$mysql_qryStudent);
if(mysqli_num_rows($result)==1){
	$result= mysqli_query($conn,$mysql_qryTutor);
	if(mysqli_num_rows($result)==1){
	echo "Tutor $user_name";
	
	}else{
		echo "Student $user_name";
	}
	
	
}
else{
	$result=mysqli_query($conn,$mysql_qryTeacher);
	if(mysqli_num_rows($result)==1){
	echo "Teacher $user_name";
	
	}else{
		echo "Login Not Successful";
		
		}
	
	}


?>