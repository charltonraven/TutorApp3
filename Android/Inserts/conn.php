<?php
$db_name = "tutor3test";
$mysql_username = "root";
$mysql_password = "";
$server_name = "localhost";
$conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name) or die("Error".mysqli_error($conn));

 if($conn){
	echo "";
	
}
else{
	echo "Error ".mysqli_error($conn);
} 

?>