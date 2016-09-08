<?php

require_once(__DIR__.'\src\controller\HASController.php'); 
session_start();
$c = new HasController();
$_SESSION["errorAlbumgenre"] = ""; $_SESSION["errorAlbumname"] = "";$_SESSION["errorAlbumreleasedate"] = "";
$_SESSION["errorArtistName"] = "";
$_SESSION["errorPlaylistName"] = "";
$_SESSION["errorSongName"] = ""; $_SESSION["errorSongduration"] = "";
try {
	$songname = NULL;
	if (isset($_POST['Songname'])) {
		$songname = $_POST['Songname'];
	}
	$Songduration = NULL;
	if (isset($_POST['Songduration'])) {
		$songduration = $_POST['Songduration'];
	}
	$c->addSong($songname, $songduration); } catch (Exception $e) {
		$errors = explode("@", $e->getMessage()); 
		foreach ($errors as $error) {
		
			if (substr($error, 0, 1) == "1") {
       			$_SESSION["errorSongName"] = substr($error, 1);
			}
			if (substr($error, 0, 1) == "2" || substr($error, 0, 1) == "3") {
       			$_SESSION["errorSongduration"] = substr($error, 1);
			}
		}
	}
?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/music.php" /> </head>
</html>