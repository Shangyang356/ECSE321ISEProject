<?php

require_once(__DIR__.'\src\controller\HASController.php'); 
session_start();
$c = new HasController();
$_SESSION["errorLocationName"] = "";
$_SESSION["errorGroupLocationName"] = "";
try {
	$locationname = NULL;
	if (isset($_POST['Location_name'])) {
		$locationname = $_POST['Location_name'];
	}
	$c->setLocation($locationname); } catch (Exception $e) {
		$errors = explode("@", $e->getMessage()); 
		foreach ($errors as $error) {
		
			if (substr($error, 0, 1) == "1") {
       			$_SESSION["errorLocationName"] = substr($error, 1);
			}
		}
	}
?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/locations.php" /> </head>
</html>