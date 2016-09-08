<?php

require_once(__DIR__.'\src\controller\HASController.php'); 
session_start();
$c = new HasController();

$_SESSION["errorPlaylistName"] = "";

try {
	$playlistname = NULL;
	if (isset($_POST['playlistName'])) {
		$playlistname = $_POST['playlistName'];
	}
	$c->addPlaylist($playlistname); 
} catch (Exception $e) {
		$errors = explode("@", $e->getMessage()); 
		foreach ($errors as $error) {
		
			if (substr($error, 0, 1) == "1") {
       			$_SESSION["errorPlaylistName"] = substr($error, 1);
			}
		}
	}
?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/music.php" /> </head>
</html>
