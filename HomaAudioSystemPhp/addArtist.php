<?php

require_once(__DIR__.'\src\controller\HASController.php'); 
session_start();
$c = new HasController();
$_SESSION["errorAlbumgenre"] = ""; $_SESSION["errorAlbumname"] = "";$_SESSION["errorAlbumreleasedate"] = "";
$_SESSION["errorArtistName"] = "";
$_SESSION["errorPlaylistName"] = "";
$_SESSION["errorSongName"] = ""; $_SESSION["errorSongduration"] = "";
try {
	
	$artistname = NULL;

	if (isset($_POST['artistName'])) {
		$artistname = $_POST['artistName'];
	}
	$c->addArtist($artistname); } catch (Exception $e) {
		$errors = explode("@", $e->getMessage()); 
		foreach ($errors as $error) {
		
			if (substr($error, 0, 1) == "1") {
       			$_SESSION["errorArtistName"] = substr($error, 1);
			}
		}
	}
?>

<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/music.php" /> </head>
</html>
