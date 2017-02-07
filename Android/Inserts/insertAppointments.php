<?php
require "conn.php";

/* 
$firstName = $_POST["username"];
$lastName = */

$emailAddress=$_POST["username"];
$subject= $_POST["subject"];
$month= $_POST["month"];
$day= $_POST["day"];
$year= $_POST["year"];
$hour= $_POST["hour"];
$minute= $_POST["minute"];
$AmPm= "PM";

/* $username="Cwilliams2638@g.fmarion.edu";
$subject= "Need Help on Classes";
$month= 11;
$day= 15;
$year= 2016;
$hour= 9;
$minute =30;
$AmPm= "Am"; */


$mysql_qry= "INSERT INTO appointments VALUES(appID,'$username','$subject','$month','$day','$year','$hour','$minute','$AmPm');";
$result= mysqli_query($conn,$mysql_qry);
echo "Complete";


?>