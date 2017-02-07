<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$Department=$_POST["department"];//'Computer Science';
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die('Unable to Connect');


if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();
	
}

$query= mysqli_query($conn,"SELECT CONCAT(department.departAbbr,tutor.courseNumber) AS ClassTutor, student.firstName, student.lastName FROM tutor INNER JOIN department ON tutor.departID=department.departID INNER JOIN student ON student.studentID=tutor.studentID where department.departName='$Department'" );

if($query){
	while($row=mysqli_fetch_array($query)){
		$flag[]=$row;
	}
	print (json_encode($flag));
}
mysqli_close($conn);

?>