<?php

require_once(__DIR__.'\src\controller\InputValidator.php');
require_once(__DIR__.'\src\controller\HASController.php');
require_once(__DIR__.'\src\Model\Album.php');
require_once(__DIR__.'\src\Model\Manager.php');
require_once(__DIR__.'\src\Model\Playlist.php');
require_once(__DIR__.'\src\Model\Location.php');
require_once(__DIR__.'\src\Model\Song.php');
require_once(__DIR__.'\src\Model\Artist.php');
require_once(__DIR__.'\src\Model\ListOfSong.php');
require_once(__DIR__.'\src\Model\GroupLocation.php');
require_once(__DIR__.'\src\Persistence\PersistenceHAS.php');

session_start();
$_SESSION["errorAlbumgenre"] = ""; $_SESSION["errorAlbumname"] = "";$_SESSION["errorAlbumreleasedate"] = "";
$_SESSION["errorArtistName"] = "";
$_SESSION["errorPlaylistName"] = "";
$_SESSION["errorSongName"] = ""; $_SESSION["errorSongduration"] = "";

$pm = new PersistenceHAS();
$rm = $pm->loadDataFromStore();
$c = new HasController();

	$playlistindex = NULL;
	$songindex = NULL;
	

	
	if (isset($_POST['AddSongToLS1'])) {
		
		$indexS = $_POST['AddSongToLS1'];
		$indexS = substr($indexS,0,1);
		
		$song =$rm->getSong_index($indexS);

		
		if(isset($_POST['Playlistlibrary']) ){
			$PlayListindex = $_POST['Playlistlibrary'];
			$PlayListindex = substr($PlayListindex,0,1);
			$playlist = $rm->getPlaylist_index($PlayListindex);
			$c->addSongToListOfSong($playlist, $song);

		}
	
		 
		//$pm->writeDataToStore($rm);
		
	}
		
	
	
	?>
 <!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/music.php" /> </head>
</html>

