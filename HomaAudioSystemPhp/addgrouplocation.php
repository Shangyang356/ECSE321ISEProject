<?php

require_once(__DIR__.'\src\controller\HASController.php'); 
session_start();
$c = new HasController();
$_SESSION["errorLocationName"] = "";
$_SESSION["errorGroupLocationName"] = "";
try {
	$grouplocationname = NULL;
	if (isset($_POST['Grouplocation_name'])) {
		$grouplocationname = $_POST['Grouplocation_name'];
	}
	$c->createGroupLocation($grouplocationname); } catch (Exception $e) {
		$errors = explode("@", $e->getMessage()); 
		foreach ($errors as $error) {
		
			if (substr($error, 0, 1) == "1") {
       			$_SESSION["errorGroupLocationName"] = substr($error, 1);
			}
		}
	}
?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/locations.php" /> </head>
</html>