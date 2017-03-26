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


/* $who = "Student";
$id="TestIDBlah";
$first_name = "TEstFirst";
$last_name = "TestLast";
$email= "studendtTest234@g.fmarion.edu";
$major="Computer Science";
$department=$major;
$courses="";
$registered=1;
$tutor=0;
$password="password"; */

 
$resultS= mysqli_query($conn,"Select email from Student where email='$email'");
$resultT= mysqli_query($conn,"Select email from Student where email='$email'");
if(mysqli_num_rows($resultS)==1 ||mysqli_num_rows($resultT)==1){
	echo "Email Already Exist";
	
} else{
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
		
		
	}
	 
	
 
 


mysqli_close($conn);


?>