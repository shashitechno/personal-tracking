<?php

$dbhost = 'localhost'; $dbname = 'test'; $dbuser = 'root'; $dbpasswd ='';

selectDB(getDBConn());

function executeQuery($strQuery)
{	
	if($res_id=mysql_query($strQuery,getDBConn()))
	{	
		return $res_id;
	}
}
function getSystemDate(){
	return date("Y-m-d H:i:s ");
}

function getFieldName($res_id,$index){
	return mysql_field_name($res_id,$index);
}

function getNumRows($strQuery){	
	return mysql_num_rows($res_id=(executeQuery($strQuery)));
}
function getNumRowsFromRes($res){	
	if(!$res)
	{
		return 0;
	}else
	{
		return mysql_num_rows($res);
	}
}

function getRecords($res_id){
	if($res_id)
	{
		return mysql_fetch_row($res_id);
	}
	else
	{
		return '';
	}
}

function getRecordsArray($resouceId)
{
	if($resouceId)
	{
		return mysql_fetch_array($resouceId);
	}
	else return '';
}

function getAffectedRows()
{
	return mysql_affected_rows();
}

function getDBConn(){
	//check database connection
	global $dbhost, $dbuser, $dbpasswd;
	global $dbhost1, $dbuser1, $dbpasswd1;
	$link=mysql_connect($dbhost, $dbuser, $dbpasswd) ;
	//or die("???".mysql_error());
	
	return $link;
	#...............................................................................
}

function selectDB($link){
	global $dbname;
	mysql_select_db($dbname,$link) ;
	//or die("Unable to select database!".mysql_error());	
}
?>