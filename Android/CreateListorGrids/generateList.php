<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$who=$_POST["who"];
$userID=$_POST["user"];
/* $who="Student";
$userID="cwilliams2638"; */
$query="";
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die('Unable to Connect');


if(mysqli_connect_error($conn)){
	echo "Failed to Connect to Database ".mysqli_connect_error();
	
}
if ($who=="Student"){
	$query= mysqli_query($conn,"SELECT classname, name, date FROM student_chathistory where fromID='$userID'" );
	
}

if ($who=="Tutor"){
	$query= mysqli_query($conn,"SELECT classname, name, date FROM tutor_chathistory where studentID='$userID'" );
}
if ($who=="Teacher"){
	$query= mysqli_query($conn,"SELECT classname, name, date FROM teacher_chathistory where fromID='$userID'" );
}
if($query){
	while($row=mysqli_fetch_array($query)){
		$flag[]=$row;
	}
	print (json_encode($flag));
}
mysqli_close($conn);

?>