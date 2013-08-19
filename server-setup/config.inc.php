<?php


//DB parameters

$dbHost = "provide your hostname here";
$dbUser = "provide your database username here";
$dbPassword = "provide your database password here";
$dbName = "provide your database name here";

//connect to the db
function connect()
{
	$db = mysql_connect($GLOBALS["dbHost"], $GLOBALS["dbUser"], $GLOBALS["dbPassword"]);
	if($db == false)
		die("Error while connecting to the DB");
	mysql_select_db($GLOBALS["dbName"], $db)
		or die("Error while connecting to the DB");
	return $db;
}

?>