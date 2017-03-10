<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$Abbr=$_POST["Abbr"];
$ClassName=$_POST["ClassName"];
$firstName=$_POST["firstName"];
$lastName=$_POST["lastName"];
$who=$_POST["who"];
$result="";


/* $Abbr="CS";
$ClassName="190";
$firstName="Charlton";
$lastName="Williams"; */

$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die('Unable to Connect');


if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();	
}
if($who=="student"){/* Tutor address */
$query= mysqli_query($conn,"Select student.email from tutor INNER JOIN department ON tutor.departID=department.departID INNER JOIN student ON student.studentID=tutor.studentID where student.firstName='$firstName' AND student.lastName='$lastName' AND department.departAbbr='$Abbr' AND tutor.courseNumber=$ClassName");
}
if($who=="teacher"){/* teacher Address */
$query= mysqli_query($conn,"Select teacher.email from teacher INNER JOIN department ON tutor.departID=department.departID INNER JOIN student ON student.studentID=tutor.studentID where student.firstName='$firstName' AND student.lastName='$lastName' AND department.departAbbr='$Abbr' AND tutor.courseNumber=$ClassName");
}
if($who=="tutor"){/* student Address */
$query= mysqli_query($conn,"Select student.email from tutor INNER JOIN department ON tutor.departID=department.departID INNER JOIN student ON student.studentID=tutor.studentID where student.firstName='$firstName' AND student.lastName='$lastName' AND department.departAbbr='$Abbr' AND tutor.courseNumber=$ClassName");
}


if(!$query){
	echo 'Could not run query: '.mysql_error;
}

$row=mysqli_fetch_row($query);

print $row[0];


mysqli_close($conn)


?>