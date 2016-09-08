<?php

require_once(__DIR__.'\src\controller\HASController.php'); 
session_start();
$c = new HasController();
$_SESSION["errorAlbumgenre"] = ""; $_SESSION["errorAlbumname"] = "";$_SESSION["errorAlbumreleasedate"] = "";
$_SESSION["errorArtistName"] = "";
$_SESSION["errorPlaylistName"] = "";
$_SESSION["errorSongName"] = ""; $_SESSION["errorSongduration"] = "";
try {
	$Albumgenre = NULL;
	if (isset($_POST['AlbumGenre'])) {
		$Albumgenre = $_POST['AlbumGenre'];
			
	}
	$Albumreleasedate = NULL;
	if (isset($_POST['AlbumDate'])) {
		$Albumreleasedate = $_POST['AlbumDate'];
		
	}
	$Albumname = NULL;
	if (isset($_POST['AlbumName'])) {
		$Albumname = $_POST['AlbumName'];
		
	}
	
	
	$c->addAlbum($Albumname, $Albumgenre,$Albumreleasedate); 
	
	} catch (Exception $e) {
		$errors = explode("@", $e->getMessage()); 
		foreach ($errors as $error) {
		
			if (substr($error, 0, 1) == "1") {
       			$_SESSION["errorAlbumname"] = substr($error, 1);
			}
			if (substr($error, 0, 1) == "2") {
       			$_SESSION["errorAlbumgenre"] = substr($error, 1);
			}
			if (substr($error, 0, 1) == "3") {
				$_SESSION["errorAlbumreleasedate"] = substr($error, 1);
			}
		}
	}
?>

<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/music.php" /> </head>
</html>
 