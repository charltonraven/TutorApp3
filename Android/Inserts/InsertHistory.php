<?php
require "conn.php";

$who = $_POST["who"];
$toID=$_POST["toID"];
$email=$_POST["email"];
$tutorName = $_POST["tutorName"];
$studentName=$_POST["studentName"];
$classtutorName=$_POST["classtutorName"];
$fromID=$_POST["fromID"];
$date = date("Y-m-d");

$conn=mysqli_connect($server_tutorName,$mysql_usertutorName,$mysql_password,$db_tutorName) or die('Unable to Connect');

/* $who = "student";
$id="Cwilliams2638";
$email="cwilliams2638@g.fmarion.edu";
$tutorName ="Charlton Williams";
$classtutorName="CS190";
$date = date("Y-m-d"); */
$mysql_qryS="INSERT INTO student_chathistory VALUES('$toID','$email','$tutorName','$classtutorName','$fromID','$date');";
$mysql_qryT= "INSERT INTO tutor_chathistory VALUES('$toID','$email','$studentName','$classtutorName','$fromID','$date');";
$mysql_qryST=array($mysql_qryS, $mysql_qryT);
$num=count($mysql_qryST);

if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();
}

if($who=="Student"){
	for($i=0;$i<$num;$i++){
	mysqli_query($conn,$mysql_qryST[$i]);
	}
	echo "saved";
}

if($who=="Tutor"){
	$result= mysqli_query($conn,$mysql_qry);
	echo "Complete";
}


if($who=="Teacher"){
	$mysql_qry= "INSERT INTO teacher_chathistory VALUES('$toID','$email','$tutorName','$classtutorName','$fromID','$date');";
	$result= mysqli_query($conn,$mysql_qry);
	echo "Complete";
}

mysqli_close($conn);
?>