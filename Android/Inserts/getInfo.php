<?php
require "conn.php";

$who=strtolower($_POST["who"]);
$firstName = $_POST["firstName"];
$lastName = $_POST["lastName"];

/* $who=strtolower("STUDENT");
$firstName = "Charlton";
$lastName = "Williams"; */




if($who=="student" || $who=="tutor"){
	$mysql_qryStudentHistory= "select * from student where firstName = '$firstName' and lastName = '$lastName' and tutor=1;";
	$result=mysqli_query($conn,$mysql_qryStudentHistory);
	while($row=mysqli_fetch_array($result)){
		$flag[]=$row;
	}
	print(json_encode($flag));
	
}

if($who=="teacher"){
	$mysql_qryTeacherHistory= "select * from teacher where firstName = '$firstName' and lastName = '$lastName';";
	$result=mysqli_query($conn,$mysql_qryTeacherHistory);
	while($row=mysqli_fetch_array($result)){
		$flag[]=$row;
	}
	print(json_encode($flag));

}

mysqli_close($conn)


?>