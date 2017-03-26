<?php
require "conn.php";

$who = $_POST["who"];
$toID=$_POST["toID"];
$email=$_POST["email"];
$tutorName = $_POST["tutorName"];
$classtutorName=$_POST["classtutorName"];
$studentName=$_POST["studentName"];
$fromID=$_POST["fromID"];
$date = date("Y-m-d");

//$conn=mysqli_connect($server_tutorName,$mysql_usertutorName,$mysql_password,$db_tutorName) or die('Unable to Connect');

/* $who = "Student";
$id="Cwilliams2638";
$email="cwilliams2638@g.fmarion.edu";
$tutorName ="Charlton Williams";
$classtutorName="CS190";
$date = date("Y-m-d");
$studentName="Dyled blah";
$toID="cwilliams2638";
$fromID="DLeon4334";  */


/* $mysql_qryST=array($mysql_qryS, $mysql_qryT);
$num=count($mysql_qryST); */

if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();
}

if($who=="Student2Tutor"){
	$mysql_qryS="INSERT INTO student_chathistory VALUES('$toID','$email','$tutorName','$classtutorName','$fromID','$date');";
	$result1=mysqli_query($conn,$mysql_qryS);
	
	$mysql_qryT= "INSERT INTO tutor_chathistory VALUES('$toID','$email','$studentName','$classtutorName','$fromID','$date');";
	$result2= mysqli_query($conn,$mysql_qryT);
	echo "Complete";
}

if($who=="Teacher2Tutor"){
	$mysql_qryS="INSERT INTO teacher_chathistory VALUES('$toID','$email','$studentName','$fromID','$date');"; #From Teacher
	$result1=mysqli_query($conn,$mysql_qryS);
	
	$mysql_qryT= "INSERT INTO tutor_chathistory VALUES('$toID','$email','$tutorName','Teacher','$fromID','$date');"; #From Tutor  StudentName is teacherName
	$result2= mysqli_query($conn,$mysql_qryT);
}

if($who=="Tutor2Teacher"){
	
	$mysql_qryT= "INSERT INTO tutor_chathistory VALUES('$toID','$email','$studentName','Teacher','$fromID','$date');"; #From Tutor  StudentName is teacherName
	$result2= mysqli_query($conn,$mysql_qryT);
	
	
	$mysql_qryS="INSERT INTO teacher_chathistory VALUES('$toID','$email','$tutorName','$fromID','$date');"; #From Teacher
	$result1=mysqli_query($conn,$mysql_qryS);
	
	
}



mysqli_close($conn);
?>